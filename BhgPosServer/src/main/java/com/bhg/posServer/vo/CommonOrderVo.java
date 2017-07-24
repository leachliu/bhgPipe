package com.bhg.posServer.vo;

import java.io.Serializable;
import java.util.Date;

public class CommonOrderVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String commonOrderId;
    
    private String orderUserId;

    private Date paymentTime;

    private String movieCode;
    
	private long advertisementUuid;
	
	private long latestUpdateTimeStamp = 0;

	public String getCommonOrderId() {
		return commonOrderId;
	}

	public void setCommonOrderId(String commonOrderId) {
		this.commonOrderId = commonOrderId;
	}

	public String getOrderUserId() {
		return orderUserId;
	}

	public void setOrderUserId(String orderUserId) {
		this.orderUserId = orderUserId;
	}

	public Date getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}

	public String getMovieCode() {
		return movieCode;
	}

	public void setMovieCode(String movieCode) {
		this.movieCode = movieCode;
	}

	public long getAdvertisementUuid() {
		return advertisementUuid;
	}

	public void setAdvertisementUuid(long advertisementUuid) {
		this.advertisementUuid = advertisementUuid;
	}

	public long getLatestUpdateTimeStamp() {
		return latestUpdateTimeStamp;
	}

	public void setLatestUpdateTimeStamp(long latestUpdateTimeStamp) {
		this.latestUpdateTimeStamp = latestUpdateTimeStamp;
	}
	
}
