package com.bhg.posServer.storm.test;

import java.util.concurrent.TimeUnit;

import com.bhg.posServer.kafka.LogScheme;

import storm.kafka.BrokerHosts;
import storm.kafka.KafkaSpout;
import storm.kafka.SpoutConfig;
import storm.kafka.ZkHosts;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.TopologyBuilder;

public class NumTopology {
	public static void main(String[] args) {
		
		TopologyBuilder topologyBuilder = new TopologyBuilder();
		
		Config config = new Config();
		
		String kafkaZookeeperHosts = "10.10.3.54:2181,10.10.3.55:2181,10.10.3.56:2181";
		
		BrokerHosts brokerHosts = new ZkHosts(kafkaZookeeperHosts);
		
		
		SpoutConfig nginxSpoutConfig = new SpoutConfig(brokerHosts, "numPair", "/nginxLogRoot", "numPairTest");
		nginxSpoutConfig.scheme = new SchemeAsMultiScheme(new LogScheme());
		topologyBuilder.setSpout("numPairSpout", new KafkaSpout(nginxSpoutConfig));
		
		
		
//		topologyBuilder.setSpout("numberProduceSpout", new NumberProduceSpout(), 1);
		topologyBuilder.setBolt("numAddBolt", new NumAddBolt(),2).shuffleGrouping("numPairSpout");
		topologyBuilder.setBolt("resultBolt", new ResultBolt(),1).shuffleGrouping("numAddBolt");
		LocalCluster cluster = new LocalCluster();
		
        cluster.submitTopology("test", config, topologyBuilder.createTopology());
        try {
			TimeUnit.SECONDS.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		cluster.killTopology("test");
		cluster.shutdown();
		
	}

}
