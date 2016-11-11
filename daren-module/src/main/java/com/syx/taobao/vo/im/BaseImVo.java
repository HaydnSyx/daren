package com.syx.taobao.vo.im;

public class BaseImVo {

	private ImMineVo mine;

	private ImToVo to;

	private MsgVo msg;

	private String type;

	private Integer resultCode;

	private Object data;

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

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public BaseImVo() {
	}

	public BaseImVo(Integer resultCode) {
		this.resultCode = resultCode;
	}

	public BaseImVo(Integer resultCode, Object data) {
		this.resultCode = resultCode;
		this.data = data;
	}
}
