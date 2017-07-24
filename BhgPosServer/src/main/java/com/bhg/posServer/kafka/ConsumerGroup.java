package com.bhg.posServer.kafka;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.bhg.posServer.utils.Configs;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

public class ConsumerGroup {

	private ConsumerConnector consumer;

	private ExecutorService executor;
	
	public static void main(String[] args) {
		new ConsumerGroup("10.10.3.54:2181,10.10.3.55:2181,10.10.3.56:2181", "group_1").exec(2);
	}

	public ConsumerGroup() {
	}

	public ConsumerGroup(String zookeeperHost, String groupId) {
		consumer = Consumer.createJavaConsumerConnector(createConsumerConfig(zookeeperHost, groupId));
	}

	private ConsumerConfig createConsumerConfig(String zookeeperHost, String groupId) {
		Properties props = new Properties();
		props.put("zookeeper.connect", zookeeperHost);
		props.put("group.id", groupId);
		props.put("zookeeper.session.timeout.ms", "400");
		props.put("zookeeper.sync.time.ms", "200");
		props.put("auto.commit.interval.ms", "1000");
		return new ConsumerConfig(props);
	}

	public void exec(int threads) {
		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put(Configs.LOGTOPIC, new Integer(threads));
		
		Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
		List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(Configs.LOGTOPIC);
		executor = Executors.newFixedThreadPool(threads);
		for (final KafkaStream<byte[], byte[]> stream : streams) {
			executor.submit(new ConsumerGroupReceiver(stream) {
				@Override
				public void receive(String message) {
					
				}
			});
		}
	}

	public void shutdown() {
		if (consumer != null)
			consumer.shutdown();
		if (executor != null)
			executor.shutdown();
		try {
			if (!executor.awaitTermination(5000, TimeUnit.MILLISECONDS)) {
				System.out.println("Timed out waiting for consumer threads to shut down, exiting uncleanly");
			}
		} catch (InterruptedException e) {
			System.out.println("Interrupted during shutdown, exiting uncleanly");
		}
	}
}
