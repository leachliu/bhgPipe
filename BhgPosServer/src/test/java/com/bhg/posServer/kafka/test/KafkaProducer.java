package com.bhg.posServer.kafka.test;

import java.util.Properties;
import java.util.Random;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class KafkaProducer {
	private Producer<String, String> producer;

	private String topicName = "numPair";
	
	private static String[] numberPair = { "1,9", "2,8", "3,7", "4,6", "5,5" };
	
	private Random random = new Random();
	
	
	public KafkaProducer() {
		producer = new Producer<String, String>(createProducerConfig());
	}

	private ProducerConfig createProducerConfig() {
		Properties props = new Properties();
		props.put("metadata.broker.list", "10.10.3.54:9092,10.10.3.55:9093,10.10.3.56:9094");
		props.put("serializer.class", "kafka.serializer.StringEncoder");
	    props.put("key.serializer.class", "kafka.serializer.StringEncoder");
		props.put("request.required.acks", "1");
		return new ProducerConfig(props);
	}
	private void  run() {
		try {
			for(int i =0 ;i < 10000;i++){
				producer.send(new KeyedMessage<String, String>(topicName,numberPair[random.nextInt(4)] ));
				System.out.println("send " + i + " messages to kafka..");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new KafkaProducer().run();
	}
}
