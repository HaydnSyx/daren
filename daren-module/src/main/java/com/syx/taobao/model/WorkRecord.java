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
@Table(name = "tb_work_record")
public class WorkRecord {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private int id;

	@Column(name = "M_ID")
	private int mId;

	@Temporal(TemporalType.DATE)
	@Column(name = "S_DATE")
	private Date sDate;

	@Column(name = "TB_SINGLEPRODUCT_NUM")
	private Integer tbSingleproductNum;

	@Column(name = "TB_SINGLEPRODUCT_AMOUNT")
	private Double tbSingleproductAmount;

	@Column(name = "TB_DETAILLIST_NUM")
	private Integer tbDetaillistNum;

	@Column(name = "TB_DETAILLIST_AMOUNT")
	private Double tbDetaillistAmount;

	@Column(name = "TB_CARD_NUM")
	private Integer tbCardNum;

	@Column(name = "TB_CARD_AMOUNT")
	private Double tbCardAmount;

	@Column(name = "TM_SINGLEPRODUCT_NUM")
	private Integer tmSingleproductNum;

	@Column(name = "TM_SINGLEPRODUCT_AMOUNT")
	private Double tmSingleproductAmount;

	@Column(name = "TM_DETAILLIST_NUM")
	private Integer tmDetaillistNum;

	@Column(name = "TM_DETAILLIST_AMOUNT")
	private Double tmDetaillistAmount;

	@Column(name = "TM_CARD_NUM")
	private Integer tmCardNum;

	@Column(name = "TM_CARD_AMOUNT")
	private Double tmCardAmount;

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

	public Integer getTbSingleproductNum() {
		return tbSingleproductNum;
	}

	public void setTbSingleproductNum(Integer tbSingleproductNum) {
		this.tbSingleproductNum = tbSingleproductNum;
	}

	public Double getTbSingleproductAmount() {
		return tbSingleproductAmount;
	}

	public void setTbSingleproductAmount(Double tbSingleproductAmount) {
		this.tbSingleproductAmount = tbSingleproductAmount;
	}

	public Integer getTbDetaillistNum() {
		return tbDetaillistNum;
	}

	public void setTbDetaillistNum(Integer tbDetaillistNum) {
		this.tbDetaillistNum = tbDetaillistNum;
	}

	public Double getTbDetaillistAmount() {
		return tbDetaillistAmount;
	}

	public void setTbDetaillistAmount(Double tbDetaillistAmount) {
		this.tbDetaillistAmount = tbDetaillistAmount;
	}

	public Integer getTbCardNum() {
		return tbCardNum;
	}

	public void setTbCardNum(Integer tbCardNum) {
		this.tbCardNum = tbCardNum;
	}

	public Double getTbCardAmount() {
		return tbCardAmount;
	}

	public void setTbCardAmount(Double tbCardAmount) {
		this.tbCardAmount = tbCardAmount;
	}

	public Integer getTmSingleproductNum() {
		return tmSingleproductNum;
	}

	public void setTmSingleproductNum(Integer tmSingleproductNum) {
		this.tmSingleproductNum = tmSingleproductNum;
	}

	public Double getTmSingleproductAmount() {
		return tmSingleproductAmount;
	}

	public void setTmSingleproductAmount(Double tmSingleproductAmount) {
		this.tmSingleproductAmount = tmSingleproductAmount;
	}

	public Integer getTmDetaillistNum() {
		return tmDetaillistNum;
	}

	public void setTmDetaillistNum(Integer tmDetaillistNum) {
		this.tmDetaillistNum = tmDetaillistNum;
	}

	public Double getTmDetaillistAmount() {
		return tmDetaillistAmount;
	}

	public void setTmDetaillistAmount(Double tmDetaillistAmount) {
		this.tmDetaillistAmount = tmDetaillistAmount;
	}

	public Integer getTmCardNum() {
		return tmCardNum;
	}

	public void setTmCardNum(Integer tmCardNum) {
		this.tmCardNum = tmCardNum;
	}

	public Double getTmCardAmount() {
		return tmCardAmount;
	}

	public void setTmCardAmount(Double tmCardAmount) {
		this.tmCardAmount = tmCardAmount;
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
