package com.bhg.posServer.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.bhg.posServer.po.AdvertisementUserAgeVisitStatistic;

public class AdvertisementUserAgeStatisticVo implements Serializable {

    private static final long serialVersionUID = 1L;
	
	private long advertisementUuid;
	
	private Date statisticsTime;
	
	private String ageRange;
	
	private int clicks;
	
	private Set<String> userIds = new HashSet<String>();
	

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

	public String getAgeRange() {
		return ageRange;
	}

	public void setAgeRange(String ageRange) {
		this.ageRange = ageRange;
	}
	public Set<String> getUserIds() {
		return userIds;
	}

	public void setUserIds(Set<String> userIds) {
		this.userIds = userIds;
	}
	
	public AdvertisementUserAgeVisitStatistic toPo(){
		AdvertisementUserAgeVisitStatistic advertisementUserAgeVisitStatistic = new AdvertisementUserAgeVisitStatistic();
		advertisementUserAgeVisitStatistic.setAdvertisementUuid(advertisementUuid);
		advertisementUserAgeVisitStatistic.setClicks(clicks);
		advertisementUserAgeVisitStatistic.setTime(statisticsTime);
		advertisementUserAgeVisitStatistic.setAgeRange(ageRange);
		advertisementUserAgeVisitStatistic.setTotalVisit(userIds.size());
		return advertisementUserAgeVisitStatistic;	
	}
}
