package com.bhg.posServer.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name ="advertisement_cinema_movie_statistics")
public class AdvertisementCinemaMovieStatistic extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "statistics_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    @Column(name = "advertisement_uuid")
    private long advertisementUuid;

    @Column(name = "cinema_id")
    private String cinemaId;

    @Column(name = "movie_id")
    private String movieId;

    @Column(name = "clicks")
    private long clicks;

    @Column(name = "user_view")
    private long totalVisit;

    @Column(name = "page_view")
    private long pageView;

    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Column(name = "update_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;


    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public long getClicks() {
        return clicks;
    }

    public void setClicks(long clicks) {
        this.clicks = clicks;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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

    public long getAdvertisementUuid() {
        return advertisementUuid;
    }

    public void setAdvertisementUuid(long advertisementUuid) {
        this.advertisementUuid = advertisementUuid;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
