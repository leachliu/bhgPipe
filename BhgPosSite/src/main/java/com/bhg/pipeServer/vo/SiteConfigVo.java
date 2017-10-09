package com.bhg.pipeServer.vo;

import java.util.HashMap;
import java.util.Map;

/**
 * 客户端配置信息
 * 
 * @author llq
 *
 */
public class SiteConfigVo {

	private static final HashMap<String, String> defaultProperties = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;
		{
			put("metadata.broker.list",
					"192.168.187.149:9092,192.168.187.149:9093,192.168.187.149:9094,192.168.187.149:9095");
			put("request.required.acks", "0");
			put("producer.type", "async");
			put("serializer.class", "kafka.serializer.StringEncoder");
			put("partitioner.class", "JavaKafkaProducerPartitioner");
			put("message.send.max.retries", "3");
			put("batch.num.messages", "200");
			put("send.buffer.bytes", "102400");

		}
	};

	Map<String, String> producerProperties;

	public SiteConfigVo() {
		this.producerProperties = defaultProperties;
	}

	public Map<String, String> getProducerProperties() {
		return producerProperties;
	}

	public void setProducerProperties(Map<String, String> producerProperties) {
		this.producerProperties = producerProperties;
	}
}
