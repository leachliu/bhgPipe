package com.bhg.posServer.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "advertisement_user_age_range_statistics")
public class AdvertisementUserAgeVisitStatistic extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "statistics_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date time;
	
	@Id
	@Column(name = "advertisement_uuid")
	private long advertisementUuid;
	
	@Id
	@Column(name = "age")
	private String ageRange;
	
	@Column(name = "clicks")
	private long clicks;
	
	@Column(name = "user_total")
	private long totalVisit;
	
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	
	@Column(name = "update_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;


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

	public long getClicks() {
		return clicks;
	}

	public void setClicks(long clicks) {
		this.clicks = clicks;
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
	

	public String getAgeRange() {
		return ageRange;
	}

	public void setAgeRange(String ageRange) {
		this.ageRange = ageRange;
	}

	public long getTotalVisit() {
		return totalVisit;
	}

	public void setTotalVisit(long totalVisit) {
		this.totalVisit = totalVisit;
	}
	
}
