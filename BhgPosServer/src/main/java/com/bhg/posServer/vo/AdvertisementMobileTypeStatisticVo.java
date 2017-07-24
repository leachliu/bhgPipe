package com.bhg.posServer.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.bhg.posServer.po.AdvertisementMobileTypeVisitStatistic;

public class AdvertisementMobileTypeStatisticVo implements Serializable {

    private static final long serialVersionUID = 1L;
	
	private long advertisementUuid;
	
	private Date statisticsTime;
	
	private String mobileType;
	
	private Set<String> userIds = new HashSet<String>();
	
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

	public String getMobileType() {
		return mobileType;
	}

	public void setMobileType(String mobileType) {
		this.mobileType = mobileType;
	}
	public Set<String> getUserIds() {
		return userIds;
	}

	public void setUserIds(Set<String> userIds) {
		this.userIds = userIds;
	}
	public AdvertisementMobileTypeVisitStatistic toPo(){
		AdvertisementMobileTypeVisitStatistic advertisementMobileTypeVisitStatistic = new AdvertisementMobileTypeVisitStatistic();
		advertisementMobileTypeVisitStatistic.setAdvertisementUuid(advertisementUuid);
		advertisementMobileTypeVisitStatistic.setClicks(this.clicks);
		advertisementMobileTypeVisitStatistic.setTotalVisit(this.userIds.size());
		advertisementMobileTypeVisitStatistic.setTime(this.statisticsTime);
		advertisementMobileTypeVisitStatistic.setMobileType(this.mobileType);
		return advertisementMobileTypeVisitStatistic;
	}
}
