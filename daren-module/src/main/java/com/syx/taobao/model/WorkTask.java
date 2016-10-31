package com.syx.taobao.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tb_work_task")
public class WorkTask {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private int id;

	@Column(name = "M_ID")
	private int mId;

	@Temporal(TemporalType.DATE)
	@Column(name = "S_DATE")
	private Date sDate;

	@Column(name = "WEB_TYPE")
	private int webType;

	@Column(name = "PRODUCT_TYPE")
	private int productType;

	@Column(name = "`STATUS`")
	private int status;

	@Column(name = "SETTLE_STATUS")
	private int settleStatus;

	@Column(name = "ENTER_STATUS")
	private int enterStatus;

	@Column(name = "LEVEL")
	private Integer level;

	@Column(name = "REMARK")
	private String remark;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME")
	private Date createTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATE_TIME")
	private Date updateTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getmId() {
		return mId;
	}

	public void setmId(int mId) {
		this.mId = mId;
	}

	public Date getsDate() {
		return sDate;
	}

	public void setsDate(Date sDate) {
		this.sDate = sDate;
	}

	public int getWebType() {
		return webType;
	}

	public void setWebType(int webType) {
		this.webType = webType;
	}

	public int getProductType() {
		return productType;
	}

	public void setProductType(int productType) {
		this.productType = productType;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getSettleStatus() {
		return settleStatus;
	}

	public void setSettleStatus(int settleStatus) {
		this.settleStatus = settleStatus;
	}

	public int getEnterStatus() {
		return enterStatus;
	}

	public void setEnterStatus(int enterStatus) {
		this.enterStatus = enterStatus;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
