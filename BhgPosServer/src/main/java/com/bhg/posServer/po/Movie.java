package com.bhg.posServer.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.bhg.posServer.utils.Constants;

@Entity
@Table(name =  Constants.CORE_DATABASE +".movie")
public class Movie extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
//	@GenericGenerator(name = "UUID", strategy = "uuid")
//	@GeneratedValue(generator = "UUID")
	@Column(name = "movie_id")
	private String movieId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "movie_code")
	private String movieCode;
	
	@Column(name = "first_week_profit")
	private BigDecimal firstWeekProfit;
	
	@Column(name = "last_profit")
	private BigDecimal lastProfit;
	
	@Column(name = "release_date")
	private String releaseDate;
	
	@Column(name = "release_version")
	private String releaseVersion;
	
	@Column(name = "movie_class")
	private String movieClass;
	
	@Column(name = "manufacturer")
	private String manufacturer;
	
	@Column(name = "publisher")
	private String publisher;
	
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	@Column(name = "update_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;

	public String getMovieId() {
		return movieId;
	}

	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getFirstWeekProfit() {
		return firstWeekProfit;
	}

	public void setFirstWeekProfit(BigDecimal firstWeekProfit) {
		this.firstWeekProfit = firstWeekProfit;
	}

	public BigDecimal getLastProfit() {
		return lastProfit;
	}

	public void setLastProfit(BigDecimal lastProfit) {
		this.lastProfit = lastProfit;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getMovieCode() {
		return movieCode;
	}

	public void setMovieCode(String movieCode) {
		this.movieCode = movieCode;
	}

	public String getReleaseVersion() {
		return releaseVersion;
	}

	public void setReleaseVersion(String releaseVersion) {
		this.releaseVersion = releaseVersion;
	}

	public String getMovieClass() {
		return movieClass;
	}

	public void setMovieClass(String movieClass) {
		this.movieClass = movieClass;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
