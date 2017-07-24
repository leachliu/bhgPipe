package com.bhg.posServer.storm;

import java.util.HashMap;
import java.util.Map;

import com.bhg.posServer.kafka.LogScheme;
import com.bhg.posServer.storm.bolt.AdvertisementCinemaMovieStatisticBolt;
import com.bhg.posServer.storm.bolt.AdvertisementCityStatisticBolt;
import com.bhg.posServer.storm.bolt.AdvertisementRedisBolt;
import com.bhg.posServer.storm.bolt.AdvertisementStatisticFrameBolt;
import com.bhg.posServer.storm.bolt.DbBolt;
import com.bhg.posServer.storm.bolt.MaterialCityStatisticBolt;
import com.bhg.posServer.storm.bolt.MaterialStatisticBolt;
import com.bhg.posServer.storm.bolt.NginxLogParserBolt;
import com.bhg.posServer.utils.Configs;

import backtype.storm.Config;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import storm.kafka.KafkaSpout;
import storm.kafka.SpoutConfig;
import storm.kafka.ZkHosts;

public class LogTopology {

	public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException {		
		System.out.println("LogTopology begin to process");

        String logTopic = Configs.LOGTOPIC;

		TopologyBuilder builder = new TopologyBuilder();
		Config conf = new Config();
		Map<String, String> map = new HashMap<String, String>();
		map.put("metadata.broker.list", Configs.KAFKA_METADATA_BROKER_LIST);
		map.put("serializer.class", Configs.SERIALIZER_CLASS);
		conf.put("kafka.broker.properties", map);
	
		SpoutConfig logSpoutConfig = new SpoutConfig(new ZkHosts(Configs.KAFKA_ZOOKEEPER_HOSTS), logTopic, "/logRoot", "logSpoutId");
        logSpoutConfig.scheme = new SchemeAsMultiScheme(new LogScheme());
        logSpoutConfig.forceFromStart = false;

		
		builder.setSpout(StormConstant.SPOUT_LOG_FROM_KAFKA, 						new KafkaSpout(logSpoutConfig),2);
		builder.setBolt(StormConstant.BOLT_NGINX_LOG_PARSER, 						new NginxLogParserBolt(), 2).shuffleGrouping(					StormConstant.SPOUT_LOG_FROM_KAFKA);
		builder.setBolt(StormConstant.BOLT_ADVERTISEMENT_REDIS, 					new AdvertisementRedisBolt(), 2).fieldsGrouping(				StormConstant.BOLT_NGINX_LOG_PARSER, new Fields(StormConstant.BOLT_FIELD_ADVERTISEMENT_UUID));
		builder.setBolt(StormConstant.BOLT_ADVERTISEMENT_FRAME_STATISTIC, 			new AdvertisementStatisticFrameBolt(), 2).fieldsGrouping(		StormConstant.BOLT_NGINX_LOG_PARSER, new Fields(StormConstant.BOLT_FIELD_ADVERTISEMENT_UUID));
		builder.setBolt(StormConstant.BOLT_ADVERTISEMENT_CITY_STATISTIC, 			new AdvertisementCityStatisticBolt(), 2).fieldsGrouping(		StormConstant.BOLT_NGINX_LOG_PARSER, new Fields(StormConstant.BOLT_FIELD_ADVERTISEMENT_UUID, 	StormConstant.BOLT_FIELD_CITY));
		builder.setBolt(StormConstant.BOLT_ADVERTISEMENT_CINEMA_MOVIE_STATISTIC, 	new AdvertisementCinemaMovieStatisticBolt(), 2).fieldsGrouping(	StormConstant.BOLT_NGINX_LOG_PARSER, new Fields(StormConstant.BOLT_FIELD_ADVERTISEMENT_UUID, 	StormConstant.BOLT_FIELD_CINEMA_ID, StormConstant.BOLT_FIELD_MOVIE_ID));
		builder.setBolt(StormConstant.BOLT_MATERIAL_STATISTIC, 						new MaterialStatisticBolt(), 2).fieldsGrouping(					StormConstant.BOLT_NGINX_LOG_PARSER, new Fields(StormConstant.BOLT_FIELD_MATERIAL_ID));
		builder.setBolt(StormConstant.BOLT_MATERIAL_CITY_STATISTIC, 				new MaterialCityStatisticBolt(), 2).fieldsGrouping(				StormConstant.BOLT_NGINX_LOG_PARSER, new Fields(StormConstant.BOLT_FIELD_MATERIAL_ID, 			StormConstant.BOLT_FIELD_CITY));
		builder.setBolt(StormConstant.BOLT_DB, new DbBolt());
		
		conf.put(Config.NIMBUS_HOST, Configs.NIMBUS_HOST);
		StormSubmitter.submitTopology("logTopology", conf, builder.createTopology());
	}

}
