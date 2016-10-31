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
@Table(name = "tb_audit")
public class Audit {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private int id;

	@Column(name = "TASK_ID")
	private int taskId;

	@Column(name = "M_ID")
	private int mId;

	@Column(name = "`STATUS`")
	private int status;

	@Column(name = "REMARK")
	private String remark;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME")
	private Date createTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public int getmId() {
		return mId;
	}

	public void setmId(int mId) {
		this.mId = mId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public Audit(int taskId, int mId, int status, String remark, Date createTime) {
		this.taskId = taskId;
		this.mId = mId;
		this.status = status;
		this.remark = remark;
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "Audit [taskId=" + taskId + ", mId=" + mId + ", status=" + status + ", remark=" + remark
				+ ", createTime=" + createTime + "]";
	}
}
