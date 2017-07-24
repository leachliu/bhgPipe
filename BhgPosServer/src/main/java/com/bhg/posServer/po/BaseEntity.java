package com.bhg.posServer.po;

import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@MappedSuperclass
public abstract class BaseEntity {

	@PrePersist
	protected void onCreate() {
		Date currentTime = new Date();
		setCreateTime(currentTime);
		setUpdateTime(currentTime);
	}

	@PreUpdate
	protected void onUpdate() {
		Date currentTime = new Date();
		if(getCreateTime() == null) {
			setCreateTime(currentTime);
		}
		setUpdateTime(new Date());
	}

	public abstract Date getCreateTime();
	
	public abstract void setCreateTime(Date createTime);

	public abstract void setUpdateTime(Date updateTime);
}
