package com.bhg.pipeServer.vo;

import com.bhg.pipeServer.service.IPipeEntity;

public class SiteVo implements IPipeEntity {
	String id;

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return this.id;
	}

}
