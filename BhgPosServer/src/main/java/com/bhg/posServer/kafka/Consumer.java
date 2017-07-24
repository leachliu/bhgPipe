package com.bhg.posServer.kafka;

import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bhg.posServer.utils.BoltUtil;
import com.bhg.posServer.utils.DateUtil;

import kafka.api.FetchRequest;
import kafka.api.FetchRequestBuilder;
import kafka.api.PartitionOffsetRequestInfo;
import kafka.common.TopicAndPartition;
import kafka.javaapi.FetchResponse;
import kafka.javaapi.OffsetResponse;
import kafka.javaapi.consumer.SimpleConsumer;
import kafka.message.MessageAndOffset;

public abstract class Consumer {
	
	public static final Logger logger = LoggerFactory.getLogger(Consumer.class);
	
	private List<Broker> brokers = new ArrayList<Broker>();
	
	private String topic;
	
	private ExecutorService executor;
	
	private AtomicInteger messageCount = new AtomicInteger(0);
	
	/**
	 * 
	 * @param zkHosts	zkHost:zkPort,zkHost:zkPort
	 * @param topic		kafka topic
	 * @throws SocketTimeoutException 
	 */
	public Consumer(String zkHosts, String topic) throws SocketTimeoutException {
		DynamicBrokersReader brokersReader = new DynamicBrokersReader(zkHosts, topic);
		brokers = brokersReader.getBrokers();
		brokersReader.close();
		this.topic = topic;
	}
	
	public void start(int nThreads) throws UnsupportedEncodingException {
		executor = Executors.newFixedThreadPool(nThreads < 1 ? 1 : nThreads);
		for (Broker broker : brokers) {
			read(broker);
		}
		executor.shutdown();
		logger.info("Read all message from kafka finished. message total = {}", messageCount.get());
		finished();
	}
	
	/**
	 * 单条信息的处理方法
	 * @param message		Kafka中的一条记录
	 * @param offset		该记录的偏移量
	 * @param lastOffset	本次读取最大偏移量
	 */
	public abstract void handle(String message, long offset, long lastOffset);
	
	/**
	 * 读取完本次所有记录之后调用
	 */
	public abstract void finished();
	
	private void read(final Broker broker) throws UnsupportedEncodingException {
		long startTimeMillis = System.currentTimeMillis();
		String clientName = "Client_" + topic + "_" + broker.getPartition();
		SimpleConsumer consumer = new SimpleConsumer(broker.getHost(), broker.getPort(), 100000, 64 * 1024, clientName);
		long currentOffset = getLastOffset(consumer, topic, broker.getPartition(), kafka.api.OffsetRequest.EarliestTime(), clientName);
		final long lastOffset = getLastOffset(consumer, topic, broker.getPartition(), kafka.api.OffsetRequest.LatestTime(), clientName);
		final long total = lastOffset - currentOffset;
		logger.info("Read message from {}, startOffset = {}, last offset = {}, total = {}", broker, currentOffset, lastOffset, total);
		while (currentOffset < lastOffset) {
			FetchRequest req = new FetchRequestBuilder().clientId(clientName).addFetch(topic, broker.getPartition(), currentOffset, 10000).build();
			FetchResponse fetchResponse = consumer.fetch(req);
			for (final MessageAndOffset messageAndOffset : fetchResponse.messageSet(topic, broker.getPartition())) {
				currentOffset = messageAndOffset.offset();
				executor.submit(new Callable<Boolean>() {

					@Override
					public Boolean call() throws Exception {
						try {
							ByteBuffer payload = messageAndOffset.message().payload();
							byte[] bytes = new byte[payload.limit()];
							payload.get(bytes);
							String message = new String(bytes, "UTF-8");
							long offset = messageAndOffset.offset();
							handle(message, offset, lastOffset);
							if(messageCount.getAndIncrement() % 500000 == 0) {
								logger.info("Read message from kafka{} offset = {}, lastOffset = {}, total = {}, remain = {}, accessTime = {}", 
										broker, offset, lastOffset, total, lastOffset - offset, 
										DateUtil.toString(DateUtil.getNginxDate(BoltUtil.getNginxAccessTime(message))));
							}
							return true;
						} catch (Exception e) {
							logger.error(e.getMessage(), e);
							return false;
						}
					}
				});
			}
			currentOffset++;
		}
		logger.info("Read message from kafka{} finished. message count = {}, consumed time = {} minutes", broker, messageCount.get(), (System.currentTimeMillis() - startTimeMillis)/1000/60);
	}
	
	private long getLastOffset(SimpleConsumer consumer, String topic, int partition, long whichTime, String clientName) {
		TopicAndPartition topicAndPartition = new TopicAndPartition(topic, partition);
		Map<TopicAndPartition, PartitionOffsetRequestInfo> requestInfo = new HashMap<TopicAndPartition, PartitionOffsetRequestInfo>();
		requestInfo.put(topicAndPartition, new PartitionOffsetRequestInfo(whichTime, 1));
		kafka.javaapi.OffsetRequest request = new kafka.javaapi.OffsetRequest(requestInfo, kafka.api.OffsetRequest.CurrentVersion(), clientName);
		OffsetResponse response = consumer.getOffsetsBefore(request);
		if (response.hasError()) {
			logger.info("Error fetching data Offset Data the Broker. Reason: {}", response.errorCode(topic, partition));
			return 0;
		}
		return response.offsets(topic, partition)[0];
	}
}
