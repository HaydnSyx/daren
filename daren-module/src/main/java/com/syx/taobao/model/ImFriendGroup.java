package com.syx.taobao.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tb_im_friend_group")
public class ImFriendGroup {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private int id;

	@Column(name = "M_ID")
	private int mId;

	@Column(name = "GROUPNAME")
	private String groupname;

	@Column(name = "ONLINE")
	private Integer online;

	@OneToMany(targetEntity = ImFriendGroupMem.class)
	@JoinColumn(name = "GROUP_ID")
	private List<ImFriendGroupMem> friendGroupMems = new ArrayList<>();

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

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public Integer getOnline() {
		return online;
	}

	public void setOnline(Integer online) {
		this.online = online;
	}

	public List<ImFriendGroupMem> getFriendGroupMems() {
		return friendGroupMems;
	}

	public void setFriendGroupMems(List<ImFriendGroupMem> friendGroupMems) {
		this.friendGroupMems = friendGroupMems;
	}

	public ImFriendGroup() {
	}

	public ImFriendGroup(int mId, String groupname, Integer online) {
		this.mId = mId;
		this.groupname = groupname;
		this.online = online;
	}

	@Override
	public String toString() {
		return "ImFriendGroup [id=" + id + ", mId=" + mId + ", groupname=" + groupname + ", online=" + online + "]";
	}
}
