package com.bhg.posServer.storm.bolt;

import java.util.HashMap;
import java.util.Map;

import com.bhg.posServer.service.RedisToDbService;
import com.bhg.posServer.utils.DateUtil;
import com.bhg.posServer.utils.SpringApplicationContext;

import backtype.storm.Config;
import backtype.storm.Constants;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;

public class DbBolt implements IRichBolt {

	private static final long serialVersionUID = 1L;
	
	private OutputCollector collector = null;
	
	private RedisToDbService redisToDbService;

	@SuppressWarnings("rawtypes")
	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
		redisToDbService = SpringApplicationContext.getBean(RedisToDbService.class);
	}

	@Override
	public void execute(Tuple input) {
		if (input.getSourceStreamId().equals(Constants.SYSTEM_TICK_STREAM_ID)){
			int minute = DateUtil.getMinuteFromDate();
			redisToDbService.addAdvertisementPvAndClickToFrameDb();
			if(minute == 30) {
				redisToDbService.addAdvertisementUvToDb();
				redisToDbService.addAreaStaticsToDb();
				redisToDbService.addCinemaMovieStaticsToDb();
				redisToDbService.addMaterialAreaStaticsToDb();
				redisToDbService.addMaterialStaticsToDb();
			}
		}
		this.collector.ack(input);
	}
	@Override
	public Map<String, Object> getComponentConfiguration() {
		Map<String, Object> conf = new HashMap<String, Object>();
		conf.put(Config.TOPOLOGY_TICK_TUPLE_FREQ_SECS, 60);
		return conf;
	}

	@Override
	public void cleanup() {
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		
	}
}
