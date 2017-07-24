package com.bhg.posServer.storm.bolt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bhg.posServer.redis.AdvertisementRedisUtil;
import com.bhg.posServer.storm.StormConstant;
import com.bhg.posServer.utils.Constants;
import com.bhg.posServer.utils.DateUtil;
import com.bhg.posServer.utils.ValueCounter;

import backtype.storm.Config;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;

public class AdvertisementRedisBolt implements IRichBolt {
	
	public static Logger log = LoggerFactory.getLogger(AdvertisementRedisBolt.class);

	private static final long serialVersionUID = -3254232399961903448L;

	private OutputCollector collector;

    private ValueCounter<String> pvCounter = new ValueCounter<String>();
	
	private ValueCounter<String> clickCounter = new ValueCounter<String>();

	private AtomicInteger count = new AtomicInteger(0);
	
	private static final int WRITE_TO_REDIS_LOG_COUNT = 1000;
	
	@SuppressWarnings("rawtypes")
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this.collector = collector;
	}
	
	protected boolean keyFieldExist(Tuple input) {
		return input.contains(StormConstant.BOLT_FIELD_ADVERTISEMENT_UUID);
	}
	
	protected String redisTime(Tuple input) {
		Date nginxAccessTime = DateUtil.getNginxDate(input.getStringByField(StormConstant.BOLT_FIELD_NGINX_ACCESS_TIME));
		Date statisticTimePoint = DateUtil.getTimePointWithMinute(nginxAccessTime, StormConstant.COMMIT_MINUTE_INTERVAL);
		return DateUtil.dateToRedisString(statisticTimePoint);
	}
	
	protected String redisPVClickField(Tuple input) {
		return redisTime(input) + "#" + input.getLongByField(StormConstant.BOLT_FIELD_ADVERTISEMENT_UUID);
	}

	protected String redisPVKey() {
		return Constants.REDIS_KEY_ADVERTISEMENT_PV;
	}
	
	protected String redisClickKey() {
		return Constants.REDIS_KEY_ADVERTISEMENT_CLICK;
	}

	@Override
	public void execute(Tuple input) {
		if(keyFieldExist(input)) {
			try {
				String field = redisPVClickField(input);
				switch (input.getIntegerByField(StormConstant.BOLT_FIELD_TYPE)) {
					case StormConstant.BOLT_TYPE_VIEW:
						pvCounter.put(field);
						break;
					case StormConstant.BOLT_TYPE_CLICK:
						clickCounter.put(field);
						break;
					default:
						break;
				}
				collector.ack(input);
			} catch (Throwable e) {
				log.error(e.getMessage(), e);
				collector.fail(input);
			}
			if(count.incrementAndGet() >= WRITE_TO_REDIS_LOG_COUNT) {
				writeToOtherRedis();
				count.set(0);
			}
		}
		if(input.getSourceStreamId().equals(backtype.storm.Constants.SYSTEM_TICK_STREAM_ID)) {
			writeToOtherRedis();
			count.set(0);
		}
	}
	
    private void writeToOtherRedis(){
    	try {
			ValueCounter<String> pvCounter = getPvCounter();
			for (String field : pvCounter.keySet()) {
				String hourFiled = field.split("#")[0];
				hourFiled = hourFiled.substring(0, hourFiled.length() - 4) + "0000";
				AdvertisementRedisUtil.getInstance().hIncrby(redisPVKey(), hourFiled+"#"+field.split("#")[1], pvCounter.get(field));
			}
			pvCounter.clear();
			ValueCounter<String> clickCounter = getClickCounter();
			for (String field : clickCounter.keySet()) {
				String hourFiled = field.split("#")[0];
				hourFiled = hourFiled.substring(0, hourFiled.length() - 4) + "0000";
				AdvertisementRedisUtil.getInstance().hIncrby(redisClickKey(), hourFiled+"#"+field.split("#")[1], clickCounter.get(field));
			}
			clickCounter.clear();
		} catch (Throwable e) {
			log.error(redisPVKey() + " write to redis error!", e);
		}
    }

	@Override
	public void cleanup() {
		writeToOtherRedis();
	}

    public ValueCounter<String> getPvCounter() {
        return pvCounter;
    }

	public ValueCounter<String> getClickCounter() {
		return clickCounter;
	}
	
	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		Map<String, Object> conf = new HashMap<String, Object>();
		conf.put(Config.TOPOLOGY_TICK_TUPLE_FREQ_SECS, StormConstant.LIMIT_WRITE_AD_REDIS_SECONDS_INTERVAL);
		return conf;
	}

}
