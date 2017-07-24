package com.bhg.posServer.storm.bolt;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bhg.posServer.storm.StormConstant;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class ClickBolt implements IRichBolt {

	private static final long serialVersionUID = 1L;

    private static Logger log = LoggerFactory.getLogger(ClickBolt.class);

    private OutputCollector collector = null;

    @Override
    @SuppressWarnings("rawtypes")
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
    	 declarer.declare(new Fields(StormConstant.BOLT_FIELD_ADVERTISEMENT_UUID, StormConstant.BOLT_FIELD_USER_ID, 
         		StormConstant.BOLT_FIELD_MATERIAL_ID, StormConstant.BOLT_FIELD_CITY, StormConstant.BOLT_FIELD_CINEMA_ID, 
         		StormConstant.BOLT_FIELD_MOVIE_ID, StormConstant.BOLT_FIELD_NGINX_ACCESS_TIME, StormConstant.BOLT_FIELD_TYPE, 
         		StormConstant.BOLT_FIELD_USER_AGENT));
    }
    
    @Override
    public void execute(Tuple input) {
        if (input.contains(StormConstant.BOLT_FIELD_ADVERTISEMENT_UUID)) {
            try {
            	collector.emit(new Values(input.getLongByField(StormConstant.BOLT_FIELD_ADVERTISEMENT_UUID), 
            			input.getStringByField(StormConstant.BOLT_FIELD_USER_ID), input.getStringByField(StormConstant.BOLT_FIELD_MATERIAL_ID),
            			input.getIntegerByField(StormConstant.BOLT_FIELD_CITY), input.getStringByField(StormConstant.BOLT_FIELD_CINEMA_ID),
            			input.getStringByField(StormConstant.BOLT_FIELD_MOVIE_ID), input.getStringByField(StormConstant.BOLT_FIELD_NGINX_ACCESS_TIME),
            			input.getIntegerByField(StormConstant.BOLT_FIELD_TYPE), input.getStringByField(StormConstant.BOLT_FIELD_USER_AGENT)));
            	collector.ack(input);
            } catch (Throwable e) {
                log.error(e.getMessage(), e);
                collector.fail(input);
            }
        }
    }

    @Override
    public void cleanup() {
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }


}
