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
@Table(name = "tb_attachment")
public class Attachment {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private int id;

	@Column(name = "TASK_ID")
	private int taskId;

	@Column(name = "WEB_TYPE")
	private Integer webType;

	@Column(name = "PRODUCT_TYPE")
	private Integer productType;

	@Column(name = "ATTACHMENT_ORIGINNAME")
	private String attachmentOriginname;

	@Column(name = "ATTACHMENT_SERVERNAME")
	private String attachmentServername;

	@Column(name = "ATTACHMENT_URL")
	private String attachmentUrl;

	@Column(name = "ATTACHMENT_SIZE")
	private Long attachmentSize;

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

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public Integer getWebType() {
		return webType;
	}

	public void setWebType(Integer webType) {
		this.webType = webType;
	}

	public Integer getProductType() {
		return productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
	}

	public String getAttachmentOriginname() {
		return attachmentOriginname;
	}

	public void setAttachmentOriginname(String attachmentOriginname) {
		this.attachmentOriginname = attachmentOriginname;
	}

	public String getAttachmentServername() {
		return attachmentServername;
	}

	public void setAttachmentServername(String attachmentServername) {
		this.attachmentServername = attachmentServername;
	}

	public String getAttachmentUrl() {
		return attachmentUrl;
	}

	public void setAttachmentUrl(String attachmentUrl) {
		this.attachmentUrl = attachmentUrl;
	}

	public Long getAttachmentSize() {
		return attachmentSize;
	}

	public void setAttachmentSize(Long attachmentSize) {
		this.attachmentSize = attachmentSize;
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
