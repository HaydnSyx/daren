package com.syx.taobao.exception;

public class BizException extends Exception {

	private static final long serialVersionUID = 1L;

	private int isStaleObjectStateException;

	public int getIsStaleObjectStateException() {
		return isStaleObjectStateException;
	}

	public void setIsStaleObjectStateException(int isStaleObjectStateException) {
		this.isStaleObjectStateException = isStaleObjectStateException;
	}

	public BizException(String msg) {
		super(msg);
		this.isStaleObjectStateException = DaoException.OTHER_EXCEPTION;
	}

	public BizException(String msg, int isStaleObjectStateException) {
		super(msg);
		this.isStaleObjectStateException = isStaleObjectStateException;
	}
}
