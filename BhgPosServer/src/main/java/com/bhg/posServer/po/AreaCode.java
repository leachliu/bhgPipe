package com.bhg.posServer.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import com.bhg.posServer.utils.Constants;

@Entity
@Table(name = Constants.WXMOVIE_DATABASE + ".area_code")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AreaCode extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "UUID", strategy = "uuid")
//	@GeneratedValue(generator = "UUID")
	@Column(name = "area_code_id")
	private String areaCodeId;

	@Column(name = "name")
	private String name;

	@Column(name = "wepiao_no")
	private int wepiaoNo;
	
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	@Column(name = "update_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;
	
	public String getAreaCodeId() {
		return areaCodeId;
	}

	public void setAreaCodeId(String areaCodeId) {
		this.areaCodeId = areaCodeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWepiaoNo() {
		return wepiaoNo;
	}

	public void setWepiaoNo(int wepiaoNo) {
		this.wepiaoNo = wepiaoNo;
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
