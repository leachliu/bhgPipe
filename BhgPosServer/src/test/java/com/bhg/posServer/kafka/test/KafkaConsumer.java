package com.bhg.posServer.kafka.test;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;
import kafka.serializer.StringDecoder;
import kafka.utils.VerifiableProperties;

public class KafkaConsumer {

	private ConsumerConnector connector = null;

	public KafkaConsumer() {
		connector = Consumer.createJavaConsumerConnector(createConsumerConfig());
	}

	private ConsumerConfig createConsumerConfig() {
		Properties props = new Properties();
		props.put("auto.offset.reset", "smallest");
		props.put("zookeeper.connect", "10.10.3.54:2181,10.10.3.55:2181,10.10.3.56:2181");
		props.put("group.id", "group1");
		props.put("zookeeper.session.timeout.ms", "4000");
		props.put("zookeeper.sync.time.ms", "20000");
		props.put("auto.commit.interval.ms", "10000");
		//props.put("serializer.class", "kafka.serializer.StringEncoder");
		return new ConsumerConfig(props);
	}

	public void run() {
		System.out.println("start");

		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put("simpleconsunmertest", 1);
		Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = connector.createMessageStreams(topicCountMap);

		for (String key : consumerMap.keySet()) {
			System.out.println("key = " + key + " size=" + consumerMap.get(key).get(0).size());
		}

		System.out.println("consumerMap = " + consumerMap.size());

		KafkaStream<byte[], byte[]> stream = consumerMap.get("simpleconsunmertest").get(0);
		System.out.println("stream=" + stream.size());

		for (MessageAndMetadata<byte[], byte[]> messageAndMetadata : stream) {
			System.out.println("Received message: (" + ByteBuffer.wrap(messageAndMetadata.key()).getInt() + ", "
					+ new String(messageAndMetadata.message()) + ")");
		}
		System.out.println("end");
	}

	void consume() {
		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put("simpleconsunmertest", new Integer(1));

		StringDecoder keyDecoder = new StringDecoder(new VerifiableProperties());
		StringDecoder valueDecoder = new StringDecoder(new VerifiableProperties());

		Map<String, List<KafkaStream<String, String>>> consumerMap = connector.createMessageStreams(topicCountMap,
				keyDecoder, valueDecoder);
		KafkaStream<String, String> stream = consumerMap.get("simpleconsunmertest").get(0);
		ConsumerIterator<String, String> it = stream.iterator();
		int i = 0;
		while (it.hasNext()){
			System.out.println(i+"--"+it.next().message());
			i++;	
		}
			
	}

	public static void main(String[] args) {
		new KafkaConsumer().consume();
	}
}
