package com.syx.taobao.vo.im;

import java.util.Map;

public class FileUpResultVo {

	private int code;
	private String msg;
	private Map<String, String> data;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}

	public FileUpResultVo() {
	}

	public FileUpResultVo(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public FileUpResultVo(int code, String msg, Map<String, String> data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
}
