package com.syx.taobao.vo;

import java.util.Date;

public class WorkTaskVo {

	private int id;

	private int mId;

	private Date sDate;

	private int webType;

	private int productType;

	private int status;

	private int settleStatus;

	private int enterStatus;

	private Integer level;
	
	private String remark;

	private Date createTime;

	private Date updateTime;
	
	private String mname;
	
	private String mnickname;
	
	private int roleId;

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

	public String getMname() {
		return mname;
	}

	public void setMname(String mname) {
		this.mname = mname;
	}

	public String getMnickname() {
		return mnickname;
	}

	public void setMnickname(String mnickname) {
		this.mnickname = mnickname;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
}
