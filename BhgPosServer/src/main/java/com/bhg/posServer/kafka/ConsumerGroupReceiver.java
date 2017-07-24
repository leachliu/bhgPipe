package com.bhg.posServer.kafka;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

public abstract class ConsumerGroupReceiver implements Runnable {

	private KafkaStream<byte[], byte[]> kafkaStream;

	public ConsumerGroupReceiver(KafkaStream<byte[], byte[]> stream) {
		kafkaStream = stream;
	}

	@Override
	public void run() {
		ConsumerIterator<byte[], byte[]> it = kafkaStream.iterator();
		while (it.hasNext()) {
			receive(new String(it.next().message()));
		}
	}

	public abstract void receive(String message);

	public KafkaStream<byte[], byte[]> getKafkaStream() {
		return kafkaStream;
	}

	public void setKafkaStream(KafkaStream<byte[], byte[]> kafkaStream) {
		this.kafkaStream = kafkaStream;
	}
}