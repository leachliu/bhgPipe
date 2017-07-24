package com.bhg.posServer.vo;

import java.io.Serializable;
import java.util.Date;

import com.bhg.posServer.po.AdvertisementMaterialAreaStatistic;

public class AdvertisementMaterialAreaStatisticVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String materialUUID;

    private String areaCode;

	private int clicks;

	private int pageViews;

    private int userView;

    private Date statisticsTime;

    public String getMaterialUUID() {
        return materialUUID;
    }

    public void setMaterialUUID(String materialUUID) {
        this.materialUUID = materialUUID;
    }

    public int getClicks() {
        return clicks;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }

    public int getPageViews() {
        return pageViews;
    }

    public void setPageViews(int pageViews) {
        this.pageViews = pageViews;
    }

    public int getUserView() {
        return userView;
    }

    public void setUserView(int userView) {
        this.userView = userView;
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

    public AdvertisementMaterialAreaStatistic toPo(){
        AdvertisementMaterialAreaStatistic advertisementMaterialAreaStatistic = new AdvertisementMaterialAreaStatistic();
        advertisementMaterialAreaStatistic.setClicks(this.clicks);
        advertisementMaterialAreaStatistic.setPageView(this.pageViews);
        advertisementMaterialAreaStatistic.setTotalVisit(this.userView);
        advertisementMaterialAreaStatistic.setCreateTime(this.statisticsTime);
        advertisementMaterialAreaStatistic.setMaterialUuid(this.materialUUID);
        advertisementMaterialAreaStatistic.setAreaCode(this.areaCode);
		return advertisementMaterialAreaStatistic;
	}
}
