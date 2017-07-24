package com.bhg.posServer.storm.bolt;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bhg.posServer.redis.RedisUtil;
import com.bhg.posServer.storm.StormConstant;
import com.bhg.posServer.utils.ValueCounter;

import backtype.storm.Config;
import backtype.storm.Constants;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;

public abstract class AbstractVisitStatisticBolt implements IRichBolt {

	private static final long serialVersionUID = 1L;
	
	public static Logger log = LoggerFactory.getLogger(AbstractVisitStatisticBolt.class);
	
	private static final int WRITE_TO_REDIS_LOG_COUNT = 1000;
	
	private OutputCollector collector;

    private ValueCounter<String> pvCounter = new ValueCounter<String>();
	
	private ValueCounter<String> clickCounter = new ValueCounter<String>();
	
	private AtomicInteger count = new AtomicInteger(0);

    private AtomicInteger pvTotal = new AtomicInteger(0);
	
	private AtomicInteger clickTotal = new AtomicInteger(0);
	
	private AtomicInteger defaultTotal = new AtomicInteger(0);

	@Override
	@SuppressWarnings("rawtypes")
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
	}

	@Override
	public void execute(Tuple input) {
		if(keyFieldExist(input)) {
			try {
				String field = redisPVClickField(input);
				String uvKey = redisUVKey(input);
				switch (input.getIntegerByField(StormConstant.BOLT_FIELD_TYPE)) {
					case StormConstant.BOLT_TYPE_VIEW:
						pvCounter.put(field);
						pvTotal.incrementAndGet();
						if(input.contains(StormConstant.BOLT_FIELD_USER_ID) && StringUtils.isNotBlank(uvKey)) {
							RedisUtil.getInstance().sAdd(uvKey, input.getStringByField(StormConstant.BOLT_FIELD_USER_ID));
						}
						break;
					case StormConstant.BOLT_TYPE_CLICK:
						clickTotal.incrementAndGet();
						clickCounter.put(field);
						break;
					default:
						defaultTotal.incrementAndGet();
						break;
				}
				collector.ack(input);
			} catch (Throwable e) {
				log.error(e.getMessage(), e);
				collector.fail(input);
			}
			if(count.incrementAndGet() >= WRITE_TO_REDIS_LOG_COUNT) {
				writeToRedis();
				count.set(0);
			}
		}
		if(input.getSourceStreamId().equals(Constants.SYSTEM_TICK_STREAM_ID)) {
			writeToRedis();
			count.set(0);
		}
	}

	@Override
	public void cleanup() {
		writeToRedis();
		count.set(0);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
	}
	
	@Override
	public Map<String, Object> getComponentConfiguration() {
		Map<String, Object> conf = new HashMap<String, Object>();
		conf.put(Config.TOPOLOGY_TICK_TUPLE_FREQ_SECS, topologyTickTupleFreqSeconds());
		return conf;
	}

	protected abstract boolean keyFieldExist(Tuple input);
	
	protected abstract String redisPVClickField(Tuple input);
	
	protected abstract String redisUVKey(Tuple input);
	
	protected abstract String redisTime(Tuple input);
	
	protected abstract String redisPVKey();
	
	protected abstract String redisClickKey();
	
	protected abstract int topologyTickTupleFreqSeconds();
	
	private void writeToRedis() {
		writeToRedis(redisPVKey(), pvCounter);
		writeToRedis(redisClickKey(), clickCounter);
	}
	
	private void writeToRedis(String key, ValueCounter<String> counter) {
		try {
			for (String field : counter.keySet()) {
				RedisUtil.getInstance().hIncrby(key, field, counter.get(field));
			}
			counter.clear();
		} catch (Throwable e) {
			log.error(key + " write to redis error!", e);
		}
	}
	
    public ValueCounter<String> getPvCounter() {
        return pvCounter;
    }

	public ValueCounter<String> getClickCounter() {
		return clickCounter;
	}
}
