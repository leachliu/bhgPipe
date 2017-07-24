package com.bhg.posServer.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.bhg.posServer.po.AdvertisementMobileSystemVisitStatistic;

public class AdvertisementMobileSystemStatisticVo implements Serializable {

    private static final long serialVersionUID = 1L;
	
	private long advertisementUuid;
	
	private Date statisticsTime;
	
	private String systemType;
	
	private Set<String> userIds =  new HashSet<String>();
	
	private int clicks;

	public long getAdvertisementUuid() {
		return advertisementUuid;
	}

	public void setAdvertisementUuid(long advertisementUuid) {
		this.advertisementUuid = advertisementUuid;
	}

	public Date getStatisticsTime() {
		return statisticsTime;
	}

	public void setStatisticsTime(Date statisticsTime) {
		this.statisticsTime = statisticsTime;
	}

	public int getClicks() {
		return clicks;
	}

	public void setClicks(int clicks) {
		this.clicks = clicks;
	}
	
	public void incrementClicks() {
		this.clicks++;
	}

	public String getSystemType() {
		return systemType;
	}

	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}
	public Set<String> getUserIds() {
		return userIds;
	}

	public void setUserIds(Set<String> userIds) {
		this.userIds = userIds;
	}
	
	public AdvertisementMobileSystemVisitStatistic toPo(){
		AdvertisementMobileSystemVisitStatistic advertisementMobileSystemVisitStatistic = new AdvertisementMobileSystemVisitStatistic();
		advertisementMobileSystemVisitStatistic.setAdvertisementUuid(advertisementUuid);
		advertisementMobileSystemVisitStatistic.setClicks(this.clicks);
		advertisementMobileSystemVisitStatistic.setTotalVisit(this.userIds.size());
		advertisementMobileSystemVisitStatistic.setTime(this.statisticsTime);
		advertisementMobileSystemVisitStatistic.setSystemType(this.systemType);
		return advertisementMobileSystemVisitStatistic;
	}

}
