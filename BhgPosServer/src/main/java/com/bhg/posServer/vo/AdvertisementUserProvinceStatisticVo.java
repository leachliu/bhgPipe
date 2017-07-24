package com.bhg.posServer.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.bhg.posServer.po.AdvertisementUserProvinceVisitStatistic;

public class AdvertisementUserProvinceStatisticVo implements Serializable {

    private static final long serialVersionUID = 1L;
	
	private long advertisementUuid;
	
	private Date statisticsTime;
	
	private String province;
	
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
	
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public Set<String> getUserIds() {
		return userIds;
	}

	public void setUserIds(Set<String> userIds) {
		this.userIds = userIds;
	}

	public AdvertisementUserProvinceVisitStatistic toPo() {
		AdvertisementUserProvinceVisitStatistic advertisementUserProvinceVisitStatistic = new AdvertisementUserProvinceVisitStatistic();
		advertisementUserProvinceVisitStatistic.setAdvertisementUuid(advertisementUuid);
		advertisementUserProvinceVisitStatistic.setTime(statisticsTime);
		advertisementUserProvinceVisitStatistic.setProvince(province);
		advertisementUserProvinceVisitStatistic.setTotalVisit(userIds.size());
		advertisementUserProvinceVisitStatistic.setClicks(clicks);
		return advertisementUserProvinceVisitStatistic;
	}
}
