package com.bhg.posServer.vo;

import java.io.Serializable;
import java.util.Date;

import com.bhg.posServer.po.AdvertisementAreaVisitStatistic;

public class AdvertisementAreaCodeStatisticVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private long advertisementUuid;

    private Date statisticsTime;

    private String areaCode;

    private long totalVisit;

    private long clicks;

    private long pageView;

    public AdvertisementAreaVisitStatistic toPo() {
        AdvertisementAreaVisitStatistic advertisementAreaVisitStatistic = new AdvertisementAreaVisitStatistic();
        advertisementAreaVisitStatistic.setAdvertisementUuid(advertisementUuid);
        advertisementAreaVisitStatistic.setAreaCode(areaCode);
        advertisementAreaVisitStatistic.setTime(statisticsTime);
        advertisementAreaVisitStatistic.setTotalVisit(totalVisit);
        advertisementAreaVisitStatistic.setClicks(clicks);
        advertisementAreaVisitStatistic.setPageView(pageView);
        return advertisementAreaVisitStatistic;
    }

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

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public long getTotalVisit() {
        return totalVisit;
    }

    public void setTotalVisit(long totalVisit) {
        this.totalVisit = totalVisit;
    }

    public long getClicks() {
        return clicks;
    }

    public void setClicks(long clicks) {
        this.clicks = clicks;
    }

    public long getPageView() {
        return pageView;
    }

    public void setPageView(long pageView) {
        this.pageView = pageView;
    }
}
