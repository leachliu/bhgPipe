package com.bhg.posServer.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "advertisement_unique_visitor_statistics")
public class AdvertisementUniqueVisitorStatistic extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "statistics_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date time;
	
	@Column(name = "advertisement_uuid")
	private long advertisementUuid;

	@Column(name = "unique_visitor")
	private long userView;
	
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	
	@Column(name = "update_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;

	public AdvertisementUniqueVisitorStatistic() {
		
	}
	
	public AdvertisementUniqueVisitorStatistic(Date time, long advertisementUuid) {
		this.time = time;
		this.advertisementUuid = advertisementUuid;
	}

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
	public long getAdvertisementUuid() {
		return advertisementUuid;
	}

	public void setAdvertisementUuid(long advertisementUuid) {
		this.advertisementUuid = advertisementUuid;
	}


	public long getUserView() {
		return userView;
	}

	public void setUserView(long userView) {
		this.userView = userView;
	}


	public Date getUpdateTime() {
		return updateTime;
	}

	@Override
	public Date getCreateTime() {
		return createTime;
	}

	@Override
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
