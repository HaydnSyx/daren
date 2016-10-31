package com.syx.taobao.vo;

import java.util.List;
import java.util.Map;

public class CommentFileUpResult {
	// 错误码
	private int error;
	// 信息
	private String message;
	// 文件的链接
	private String url;
	// 显示的名称
	private String uploadFileName;

	private String originFileName;

	private String uploadFileNameWithoutSuff;

	private String suff;

	private long fileSize;

	private List<Map<String, String>> fileList;

	public List<Map<String, String>> getFileList() {
		return fileList;
	}

	public void setFileList(List<Map<String, String>> fileList) {
		this.fileList = fileList;
	}

	public int getError() {
		return error;
	}

	public void setError(int error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getUploadFileNameWithoutSuff() {
		return uploadFileNameWithoutSuff;
	}

	public void setUploadFileNameWithoutSuff(String uploadFileNameWithoutSuff) {
		this.uploadFileNameWithoutSuff = uploadFileNameWithoutSuff;
	}

	public String getSuff() {
		return suff;
	}

	public void setSuff(String suff) {
		this.suff = suff;
	}

	public CommentFileUpResult() {
		super();
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public CommentFileUpResult(int error, String message, String url, String uploadFileName) {
		super();
		this.error = error;
		this.url = url;
		this.message = message;
		this.uploadFileName = uploadFileName;
	}

	public CommentFileUpResult(int error, String message, String url) {
		super();
		this.error = error;
		this.url = url;
		this.message = message;
	}

	public CommentFileUpResult(int error, String message) {
		super();
		this.error = error;
		this.message = message;
	}

	public String getOriginFileName() {
		return originFileName;
	}

	public void setOriginFileName(String originFileName) {
		this.originFileName = originFileName;
	}

	public CommentFileUpResult(int error, String message, String url, String uploadFileName,
			List<Map<String, String>> fileList) {
		super();
		this.error = error;
		this.message = message;
		this.url = url;
		this.uploadFileName = uploadFileName;
		this.fileList = fileList;
	}

	public CommentFileUpResult(int error, String message, List<Map<String, String>> fileList) {
		this.error = error;
		this.message = message;
		this.fileList = fileList;
	}

	public CommentFileUpResult(int error, String message, String url, String uploadFileName,
			String uploadFileNameWithoutSuff, String suff) {
		super();
		this.error = error;
		this.message = message;
		this.url = url;
		this.uploadFileName = uploadFileName;
		this.uploadFileNameWithoutSuff = uploadFileNameWithoutSuff;
		this.suff = suff;
	}

	public CommentFileUpResult(int error, String message, String url, String uploadFileName,
			String uploadFileNameWithoutSuff, String suff, long fileSize) {
		super();
		this.error = error;
		this.message = message;
		this.url = url;
		this.uploadFileName = uploadFileName;
		this.uploadFileNameWithoutSuff = uploadFileNameWithoutSuff;
		this.suff = suff;
		this.fileSize = fileSize;
	}

	public CommentFileUpResult(int error, String message, String url, String uploadFileName, String originFileName,
			String uploadFileNameWithoutSuff, String suff, long fileSize) {
		super();
		this.error = error;
		this.message = message;
		this.url = url;
		this.uploadFileName = uploadFileName;
		this.originFileName = originFileName;
		this.uploadFileNameWithoutSuff = uploadFileNameWithoutSuff;
		this.suff = suff;
		this.fileSize = fileSize;
	}
}