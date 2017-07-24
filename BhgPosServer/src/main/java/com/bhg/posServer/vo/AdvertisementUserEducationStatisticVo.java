package com.bhg.posServer.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.bhg.posServer.po.AdvertisementUserEducationVisitStatistic;

public class AdvertisementUserEducationStatisticVo implements Serializable {

    private static final long serialVersionUID = 1L;
	
	private long advertisementUuid;
	
	private Date statisticsTime;
	
	private String education;
	
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

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public Set<String> getUserIds() {
		return userIds;
	}

	public void setUserIds(Set<String> userIds) {
		this.userIds = userIds;
	}
	public AdvertisementUserEducationVisitStatistic toPo(){
		AdvertisementUserEducationVisitStatistic advertisementUserEducationVisitStatistic = new AdvertisementUserEducationVisitStatistic();
		advertisementUserEducationVisitStatistic.setAdvertisementUuid(advertisementUuid);
		advertisementUserEducationVisitStatistic.setClicks(clicks);
		advertisementUserEducationVisitStatistic.setTotalVisit(userIds.size());
		advertisementUserEducationVisitStatistic.setTime(statisticsTime);
		advertisementUserEducationVisitStatistic.setEducation(education);
		return advertisementUserEducationVisitStatistic;
	}
}
