package com.bhg.posServer.kafka;

import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DynamicBrokersReader {

	public static final Logger logger = LoggerFactory.getLogger(DynamicBrokersReader.class);

	private CuratorFramework curator;

	int sessionTimeoutMs = 10000;

	int connectionTimeoutMs = 10000;

	int retry = 3;

	int sleepMsBetweenRetries = 1000;

	private String zkBrokersPath = "/brokers";

	private String topic;

	public DynamicBrokersReader(String zkHosts, String topic) {
		this.topic = topic;
		try {
			curator = CuratorFrameworkFactory.newClient(zkHosts, sessionTimeoutMs, connectionTimeoutMs, new RetryNTimes(3, 100));
			curator.start();
		} catch (Exception e) {
			logger.error("Couldn't connect to zookeeper", e);
		}
	}

	public List<Broker> getBrokers() throws SocketTimeoutException {
		List<Broker> brokers = new ArrayList<Broker>();
		try {
			int numPartitionsForTopic = getNumPartitions();
			String brokerInfoPath = brokerPath();
			for (int partition = 0; partition < numPartitionsForTopic; partition++) {
				int leader = getLeaderFor(partition);
				String path = brokerInfoPath + "/" + leader;
				try {
					byte[] brokerData = curator.getData().forPath(path);
					brokers.add(getBrokerHost(brokerData, partition));
				} catch (org.apache.zookeeper.KeeperException.NoNodeException e) {
					logger.error("Node {} does not exist ", path);
				}
			}
		} catch (SocketTimeoutException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		logger.info("Read partition info from zookeeper: {}", brokers);
		return brokers;
	}

	public void close() {
		curator.close();
	}

	@SuppressWarnings("unchecked")
	private Broker getBrokerHost(byte[] brokerData, int partition) {
		try {
            Map<Object, Object> value = (Map<Object, Object>) JSONValue.parse(new String(brokerData, "UTF-8"));
            String host = (String) value.get("host");
            Integer port = ((Long) value.get("port")).intValue();
            return new Broker(host, port, partition);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
	}

	@SuppressWarnings("unchecked")
	private int getLeaderFor(int partition) {
		try {
            String topicBrokersPath = partitionPath();
            byte[] hostPortData = curator.getData().forPath(topicBrokersPath + "/" + partition + "/state");
            Map<Object, Object> value = (Map<Object, Object>) JSONValue.parse(new String(hostPortData, "UTF-8"));
            Integer leader = ((Number) value.get("leader")).intValue();
            if (leader == -1) {
                throw new RuntimeException("No leader found for partition " + partition);
            }
            return leader;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	}

	private String partitionPath() {
		return zkBrokersPath + "/topics/" + topic + "/partitions";
	}

	private String brokerPath() {
		return zkBrokersPath + "/ids";
	}

	private int getNumPartitions() {
		try {
            String topicBrokersPath = partitionPath();
            List<String> children = curator.getChildren().forPath(topicBrokersPath);
            return children.size();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	}
    
}
