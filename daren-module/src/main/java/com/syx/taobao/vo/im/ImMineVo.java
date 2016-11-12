package com.syx.taobao.vo.im;

public class ImMineVo {

	private String avatar;
	private String content;
	private int id;
	private boolean mine;
	private String username;

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isMine() {
		return mine;
	}

	public void setMine(boolean mine) {
		this.mine = mine;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public ImMineVo() {
	}

	public ImMineVo(String avatar, String content, int id, boolean mine, String username) {
		this.avatar = avatar;
		this.content = content;
		this.id = id;
		this.mine = mine;
		this.username = username;
	}
}
