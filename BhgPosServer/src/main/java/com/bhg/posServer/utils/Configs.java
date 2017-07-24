package com.bhg.posServer.utils;

import java.util.ResourceBundle;

public class Configs {
	
	private static final ResourceBundle bundle = ResourceBundle.getBundle("storm");

	public static final String KAFKA_ZOOKEEPER_HOSTS = bundle.getString("kafka_zookeeper_hosts");

	public static final String KAFKA_METADATA_BROKER_LIST = bundle.getString("kafka_metadata_broker_list");

	public static final String SERIALIZER_CLASS = bundle.getString("serializer_class");

	public static final String NIMBUS_HOST = bundle.getString("nimbus_host");
	
	public static final String REQUEST_REQUIRED_ACKS = bundle.getString("request_required_acks");
	
	public static final String LOGTOPIC = bundle.getString("logTopic");
	
}
