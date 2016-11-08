package com.syx.taobao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tb_im_friend_group_mem")
public class ImFriendGroupMem {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private int id;

	@Column(name = "M_ID")
	private int mId;

	@Column(name = "USERNAME")
	private String username;

	@Column(name = "AVATAR")
	private String avatar;

	@Column(name = "SIGN")
	private String sign;

	@Column(name = "`STATUS`")
	private int status;

	@ManyToOne(targetEntity = ImFriendGroup.class)
	@JoinColumn(name = "GROUP_ID")
	private ImFriendGroup imFriendGroup;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public ImFriendGroup getImFriendGroup() {
		return imFriendGroup;
	}

	public void setImFriendGroup(ImFriendGroup imFriendGroup) {
		this.imFriendGroup = imFriendGroup;
	}

	public ImFriendGroupMem() {
	}

	public int getmId() {
		return mId;
	}

	public void setmId(int mId) {
		this.mId = mId;
	}

	public ImFriendGroupMem(String username, String avatar, String sign, int status, ImFriendGroup imFriendGroup) {
		this.username = username;
		this.avatar = avatar;
		this.sign = sign;
		this.status = status;
		this.imFriendGroup = imFriendGroup;
	}
}
