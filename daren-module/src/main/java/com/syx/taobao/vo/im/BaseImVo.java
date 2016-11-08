package com.syx.taobao.vo.im;

public class BaseImVo {

	private ImMineVo mine;

	private ImToVo to;

	private MsgVo msg;

	private String type;

	private Integer resultCode;

	public ImMineVo getMine() {
		return mine;
	}

	public void setMine(ImMineVo mine) {
		this.mine = mine;
	}

	public ImToVo getTo() {
		return to;
	}

	public void setTo(ImToVo to) {
		this.to = to;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getResultCode() {
		return resultCode;
	}

	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}

	public MsgVo getMsg() {
		return msg;
	}

	public void setMsg(MsgVo msg) {
		this.msg = msg;
	}
}
