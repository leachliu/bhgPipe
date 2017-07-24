package com.bhg.posServer.vo;

import java.io.Serializable;
import java.util.Date;

import com.bhg.posServer.po.AdvertisementCinemaMovieStatistic;

public class AdvertisementCinemaMovieStatisticVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private long advertisementUuid;

    private Date statisticsTime;

    private String cinemaId;

    private String movieId;

    private long clicks;

    private long totalVisit;

    private long pageView;

    public AdvertisementCinemaMovieStatistic toPo() {
        AdvertisementCinemaMovieStatistic advertisementCinemaMovieStatistic = new AdvertisementCinemaMovieStatistic();
        advertisementCinemaMovieStatistic.setAdvertisementUuid(advertisementUuid);
        advertisementCinemaMovieStatistic.setTotalVisit(totalVisit);
        advertisementCinemaMovieStatistic.setTime(statisticsTime);
        advertisementCinemaMovieStatistic.setPageView(pageView);
        advertisementCinemaMovieStatistic.setClicks(clicks);
        advertisementCinemaMovieStatistic.setCinemaId(cinemaId);
        advertisementCinemaMovieStatistic.setMovieId(movieId);
        return advertisementCinemaMovieStatistic;
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

    public String getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(String cinemaId) {
        this.cinemaId = cinemaId;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public long getClicks() {
        return clicks;
    }

    public void setClicks(long clicks) {
        this.clicks = clicks;
    }

    public long getTotalVisit() {
        return totalVisit;
    }

    public void setTotalVisit(long totalVisit) {
        this.totalVisit = totalVisit;
    }

    public long getPageView() {
        return pageView;
    }

    public void setPageView(long pageView) {
        this.pageView = pageView;
    }
}
