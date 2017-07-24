package com.bhg.posServer.vo;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import com.bhg.posServer.utils.DateUtil;


public class UserClickLogVo implements Serializable {

    private static final long serialVersionUID = 1L;
	
    private Date clickTime;

    private String userId;

    private long advertisementUuid;

    private String movieCode;

    public UserClickLogVo( Map<String, String> params) {
        this.clickTime = DateUtil.getDate(params.get("clickTime"));
        this.userId = params.get("uuid");
        this.advertisementUuid = Long.parseLong(params.get("advertisementUuid"));
        this.movieCode = params.get("movieId");
    }

    public Date getClickTime() {
        return clickTime;
    }

    public void setClickTime(Date clickTime) {
        this.clickTime = clickTime;
    }
    

    public long getAdvertisementUuid() {
		return advertisementUuid;
	}

	public void setAdvertisementUuid(long advertisementUuid) {
		this.advertisementUuid = advertisementUuid;
	}

	public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMovieCode() {
        return movieCode;
    }

    public void setMovieCode(String movieCode) {
        this.movieCode = movieCode;
    }
}
