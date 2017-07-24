package com.bhg.posServer.vo;

import java.io.Serializable;
import java.util.Date;

import com.bhg.posServer.po.AdvertisementMaterialVisitStatistic;

public class AdvertisementMaterialVisitStatisticVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String materialUUID;

	private int pageViews;

    private Date statisticsTime;

    private int clicks;

    private int userViews;


	public AdvertisementMaterialVisitStatistic toPo(){
		AdvertisementMaterialVisitStatistic advertisementMaterialVisitStatistic = new AdvertisementMaterialVisitStatistic();
		advertisementMaterialVisitStatistic.setClicks(this.clicks);
        advertisementMaterialVisitStatistic.setUserView(userViews);
		advertisementMaterialVisitStatistic.setPageViews(this.pageViews);
		advertisementMaterialVisitStatistic.setMaterialUUID(this.materialUUID);
        advertisementMaterialVisitStatistic.setTime(statisticsTime);
		return advertisementMaterialVisitStatistic;
	}

    public String getMaterialUUID() {
        return materialUUID;
    }

    public void setMaterialUUID(String materialUUID) {
        this.materialUUID = materialUUID;
    }

    public int getPageViews() {
        return pageViews;
    }

    public void setPageViews(int pageViews) {
        this.pageViews = pageViews;
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

    public int getUserViews() {
        return userViews;
    }

    public void setUserViews(int userViews) {
        this.userViews = userViews;
    }
}
