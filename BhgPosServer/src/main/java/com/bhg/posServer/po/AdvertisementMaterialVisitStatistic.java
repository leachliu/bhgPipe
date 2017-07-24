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
@Table(name = "advertisement_material_visit_statistics")
public class AdvertisementMaterialVisitStatistic extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
    
    @Column(name = "statistics_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

	@Column(name = "material_uuid")
	private String materialUUID;
	
	@Column(name = "click_times")
	private long clicks;

	@Column(name = "user_view")
	private long userView;

    @Column(name = "show_times")
    private long pageViews;
	
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	
	@Column(name = "update_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;
	
	
	public String getMaterialUUID() {
		return materialUUID;
	}

	public void setMaterialUUID(String materialUUID) {
		this.materialUUID = materialUUID;
	}

    public long getClicks() {
        return clicks;
    }

    public void setClicks(long clicks) {
        this.clicks = clicks;
    }

    public long getUserView() {
        return userView;
    }

    public void setUserView(long userView) {
        this.userView = userView;
    }

    public long getPageViews() {
        return pageViews;
    }

    public void setPageViews(long pageViews) {
        this.pageViews = pageViews;
    }

    @Override
	public Date getCreateTime() {
		return createTime;
	}

	@Override
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	
}
