package com.bhg.posServer.storm.test;

import java.util.Map;
import java.util.Random;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

public class NumberProduceSpout extends BaseRichSpout {

	private static final long serialVersionUID = 1L;

	private static String[] numberPair = { "1,9", "2,8", "3,7", "4,6", "5,5" };

	private SpoutOutputCollector collector;
	
	private boolean isFinished = false;
	
	private int index=0;
	
	private Random random = new Random();

	@Override
	@SuppressWarnings("rawtypes")
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		this.collector = collector;
		
	}

	@Override
	public void nextTuple() {
		if(index>=100000){
			isFinished = true;
		}
		if(!isFinished)
		{
			String value = numberPair[random.nextInt(5)];
			System.out.println(index+" produce numpair is "+value);
			collector.emit(new Values(value));
			index++;
		}
		
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("numberPair"));
	}
	

}
