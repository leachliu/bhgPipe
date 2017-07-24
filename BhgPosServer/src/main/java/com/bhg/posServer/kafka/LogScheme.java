package com.bhg.posServer.kafka;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.bhg.posServer.storm.StormConstant;

import backtype.storm.spout.Scheme;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

public class LogScheme implements Scheme {

	private static final long serialVersionUID = 1L;

	@Override
	public List<Object> deserialize(byte[] ser) {
		try {
			return new Values(new String(ser, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Fields getOutputFields() {
		return new Fields(StormConstant.SPOUT_FIELD_LINE);
	}

}
