package com.bhg.posServer.kafka;

public class Broker {
	
	private String host;
	
	private int port;
	
	private int partition;

	public Broker(String host, int port, int partition) {
		this.host = host;
		this.port = port;
		this.partition = partition;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getPartition() {
		return partition;
	}

	public void setPartition(int partition) {
		this.partition = partition;
	}

	@Override
	public String toString() {
		return "Broker [host=" + host + ", port=" + port + ", partition=" + partition + "]";
	}
	
}
