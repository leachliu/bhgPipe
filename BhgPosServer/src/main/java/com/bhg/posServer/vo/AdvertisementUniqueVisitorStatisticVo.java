package com.bhg.posServer.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.bhg.posServer.po.AdvertisementUniqueVisitorStatistic;

public class AdvertisementUniqueVisitorStatisticVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private long advertisementUuid;


    private Set<String> userIds = new HashSet<String>();


    private Date statisticsTime;

    private int count;
    
    private int frame;

    public long getAdvertisementUuid() {
        return advertisementUuid;
    }


    public void setAdvertisementUuid(long advertisementUuid) {
        this.advertisementUuid = advertisementUuid;
    }


    public void addUserId(String userId) {
        userIds.add(userId);
    }

    public long getUserView() {
        return userIds.size();
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getFrame() {
		return frame;
	}

	public void setFrame(int frame) {
		this.frame = frame;
	}

	public AdvertisementUniqueVisitorStatistic toPo() {
        AdvertisementUniqueVisitorStatistic advertisementUniqueVisitorStatistic = new AdvertisementUniqueVisitorStatistic();
        advertisementUniqueVisitorStatistic.setAdvertisementUuid(advertisementUuid);
        advertisementUniqueVisitorStatistic.setTime(this.statisticsTime);
        advertisementUniqueVisitorStatistic.setUserView(getCount());
        return advertisementUniqueVisitorStatistic;
    }
}
