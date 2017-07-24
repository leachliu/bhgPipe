package com.bhg.posServer.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.bhg.posServer.po.AdvertisementUserIncomeVisitStatistic;

public class AdvertisementUserIncomeStatisticVo implements Serializable {

    private static final long serialVersionUID = 1L;
	
	private long advertisementUuid;
	
	private Date statisticsTime;
	
	private String income;
	
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

	
	public String getIncome() {
		return income;
	}

	public void setIncome(String income) {
		this.income = income;
	}

	public Set<String> getUserIds() {
		return userIds;
	}

	public void setUserIds(Set<String> userIds) {
		this.userIds = userIds;
	}

	public AdvertisementUserIncomeVisitStatistic toPo() {
		AdvertisementUserIncomeVisitStatistic advertisementUserIncomeVisitStatistic = new AdvertisementUserIncomeVisitStatistic();
		advertisementUserIncomeVisitStatistic.setAdvertisementUuid(advertisementUuid);
		advertisementUserIncomeVisitStatistic.setTime(statisticsTime);
		advertisementUserIncomeVisitStatistic.setIncome(income);
		advertisementUserIncomeVisitStatistic.setTotalVisit(userIds.size());
		advertisementUserIncomeVisitStatistic.setClicks(clicks);
		return advertisementUserIncomeVisitStatistic;
	}
}
