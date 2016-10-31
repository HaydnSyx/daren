package com.syx.taobao.exception;

public class DaoException extends Exception {
	/**
	 * 乐观锁导致的异常
	 */
	public final static int STALE_OBJECT_STATE_EXCEPTION = 1;
	/**
	 * 其他异常
	 */
	public final static int OTHER_EXCEPTION = 0;

	private static final long serialVersionUID = 1L;

	private int isStaleObjectStateException;

	public int getIsStaleObjectStateException() {
		return isStaleObjectStateException;
	}

	public void setIsStaleObjectStateException(int isStaleObjectStateException) {
		this.isStaleObjectStateException = isStaleObjectStateException;
	}

	public DaoException(String msg) {
		super(msg);
		this.isStaleObjectStateException = OTHER_EXCEPTION;
	}

	public DaoException(String msg, int isStaleObjectStateException) {
		super(msg);
		this.isStaleObjectStateException = isStaleObjectStateException;
	}
}
