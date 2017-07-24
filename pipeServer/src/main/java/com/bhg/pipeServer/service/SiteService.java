package com.bhg.pipeServer.service;

import java.lang.reflect.Type;

import org.springframework.stereotype.Service;

import com.bhg.pipeServer.vo.SiteVo;

@Service
public class SiteService extends ServiceBase<SiteVo> {

	@Override
	String serviceKey() {
		return "sites";
	}

	@Override
	Type getEntityType() {
		return SiteVo.class;
	}

}
