package com.bhg.pipeServer.service;

import java.lang.reflect.Type;

import org.springframework.stereotype.Service;

import com.bhg.pipeServer.vo.SchemaVo;

@Service
public class SchemaService extends ServiceBase<SchemaVo> {

	@Override
	String serviceKey() {
		return "schemas";
	}

	@Override
	Type getEntityType() {
		return SchemaVo.class;
	}
	
	
}
