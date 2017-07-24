package com.bhg.posServer.storm.test;

import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;

public class ResultBolt implements IRichBolt {

	private static final long serialVersionUID = 1L;

	private static int result = 0;

	OutputCollector collector = null;
	
	@Override
	@SuppressWarnings("rawtypes")
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
		
	}

	@Override
	public void execute(Tuple input) {

		try {
			String sum = input.getStringByField("sum");
			result += Integer.parseInt(sum);
			System.out.println("result is  " + result);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Override
	public void cleanup() {
		
		
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}
}
