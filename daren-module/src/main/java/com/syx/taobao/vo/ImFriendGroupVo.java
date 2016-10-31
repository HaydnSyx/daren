package com.syx.taobao.vo;

import java.util.ArrayList;
import java.util.List;

public class ImFriendGroupVo {

	private int id;

	private int mId;

	private String groupname;

	private Integer online;

	private List<ImFriendGroupMemVo> list = new ArrayList<>();

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

	public List<ImFriendGroupMemVo> getList() {
		return list;
	}

	public void setList(List<ImFriendGroupMemVo> list) {
		this.list = list;
	}
}
