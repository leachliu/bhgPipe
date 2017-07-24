package com.bhg.posServer.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.bhg.posServer.po.AdvertisementUserGenderVisitStatistic;

public class AdvertisementUserGenderStatisticVo implements Serializable {

    private static final long serialVersionUID = 1L;
	
	private long advertisementUuid;
	
	private Date statisticsTime;
	
	private String gender;
	
	private int clicks;
	
	private Set<String> userIds =  new HashSet<String>();

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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Set<String> getUserIds() {
		return userIds;
	}

	public void setUserIds(Set<String> userIds) {
		this.userIds = userIds;
	}
	public AdvertisementUserGenderVisitStatistic toPo(){
		AdvertisementUserGenderVisitStatistic advertisementUserGenderVisitStatistic = new AdvertisementUserGenderVisitStatistic();
		advertisementUserGenderVisitStatistic.setAdvertisementUuid(advertisementUuid);
		advertisementUserGenderVisitStatistic.setTime(statisticsTime);
		advertisementUserGenderVisitStatistic.setClicks(clicks);
		advertisementUserGenderVisitStatistic.setGender(gender);
		advertisementUserGenderVisitStatistic.setTotalVisit(userIds.size());
		return advertisementUserGenderVisitStatistic;
	}
}
