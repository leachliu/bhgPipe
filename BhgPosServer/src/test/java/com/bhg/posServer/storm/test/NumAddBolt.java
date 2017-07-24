package com.bhg.posServer.storm.test;

import java.util.Map;

import com.bhg.posServer.storm.StormConstant;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.IBasicBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class NumAddBolt implements IBasicBolt {
	
	private static final long serialVersionUID = 1L;
	
	private static int totalAddBoltNum = 0;
	
	private int index;

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("sum"));
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		return null;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public void prepare(Map stormConf, TopologyContext context) {
		totalAddBoltNum++;
		index = totalAddBoltNum;
	}

	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		String numberPair = input.getStringByField(StormConstant.SPOUT_FIELD_LINE);
		int num1 = Integer.parseInt(numberPair.split(",")[0]);
		int num2 = Integer.parseInt(numberPair.split(",")[1]);
		int sum = num1+num2;
		System.out.println("this is "+index+" it is processing "+numberPair);
		collector.emit(new Values(sum+""));
	}

	@Override
	public void cleanup() {
		
	}


}
