package com.bhg.posServer.vo;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.bhg.posServer.po.AdvertisementVisitStatistic;

public class AdvertisementVisitStatisticVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private long advertisementUuid;
	
	private int pageView;
	
	private Set<String> userIds =  new HashSet<String>();
	
	private int clicks;
	
	private Date statisticsTime;

	public long getAdvertisementUuid() {
		return advertisementUuid;
	}

	public void setAdvertisementUuid(long advertisementUuid) {
		this.advertisementUuid = advertisementUuid;
	}

	public int getPageView() {
		return pageView;
	}

	public void setPageView(int pageView) {
		this.pageView = pageView;
	}
	
	public void incrementPageView() {
		pageView++;
	}
	
	public void addUserId(String userId) {
		userIds.add(userId);
	}

	public long getUserView() {
		return userIds.size();
	}

	public int getClicks() {
		return clicks;
	}

	public void setClicks(int clicks) {
		this.clicks = clicks;
	}
	
	public void incrementClicks() {
		clicks++;
	}

	public Date getStatisticsTime() {
		return statisticsTime;
	}

	public void setStatisticsTime(Date statisticsTime) {
		this.statisticsTime = statisticsTime;
	}

    public Set<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(Set<String> userIds) {
        this.userIds = userIds;
    }

	public AdvertisementVisitStatistic toPo(){
		AdvertisementVisitStatistic advertisementVisitStatistic = new AdvertisementVisitStatistic();
		advertisementVisitStatistic.setAdvertisementUuid(advertisementUuid);
		advertisementVisitStatistic.setClicks(this.clicks);
		advertisementVisitStatistic.setPageView(this.pageView);
		advertisementVisitStatistic.setTime(this.statisticsTime);
		return advertisementVisitStatistic;
	}
}
