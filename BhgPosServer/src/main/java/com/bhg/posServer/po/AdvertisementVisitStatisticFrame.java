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
@Table(name = "advertisement_visit_frame_statistics")
public class AdvertisementVisitStatisticFrame extends BaseEntity implements Serializable {

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

	@Column(name = "page_view")
	private long pageView;

	@Column(name = "user_view")
	private long userView;

	@Column(name = "clicks")
	private long clicks;
	
	@Column(name = "frame")
	private int frame;

	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	@Column(name = "update_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;
	
    @Column(name = "is_settled")
    private boolean isSettled = false;
    
    public AdvertisementVisitStatisticFrame() {
	}

	public AdvertisementVisitStatisticFrame(Date time, long advertisementUuid, int frame) {
		this.time = time;
		this.advertisementUuid = advertisementUuid;
		this.frame = frame;
	}
    
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public long getAdvertisementUuid() {
		return advertisementUuid;
	}

	public void setAdvertisementUuid(long advertisementUuid) {
		this.advertisementUuid = advertisementUuid;
	}

	public long getPageView() {
		return pageView;
	}

	public void setPageView(long pageView) {
		this.pageView = pageView;
	}

	public long getUserView() {
		return userView;
	}

	public void setUserView(long userView) {
		this.userView = userView;
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isSettled() {
		return isSettled;
	}

	public void setSettled(boolean isSettled) {
		this.isSettled = isSettled;
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

	public int getFrame() {
		return frame;
	}

	public void setFrame(int frame) {
		this.frame = frame;
	}

}
