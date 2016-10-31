package com.syx.taobao.ueditor.upload;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.syx.taobao.exception.BizException;
import com.syx.taobao.service.PropertiesService;
import com.syx.taobao.ueditor.define.State;

public class Uploader {
	private HttpServletRequest request = null;
	private Map<String, Object> conf = null;
	private PropertiesService propertyService;

	public Uploader(HttpServletRequest request, Map<String, Object> conf, PropertiesService propertyService) {
		this.request = request;
		this.conf = conf;
		this.propertyService = propertyService;
	}

	public final State doExec() throws BizException, FileNotFoundException, IOException {
		String filedName = (String) this.conf.get("fieldName");
		State state = null;

		if ("true".equals(this.conf.get("isBase64"))) {
			state = Base64Uploader.save(this.request.getParameter(filedName), this.conf, propertyService,
					this.request.getParameter("filePath"));
		} else {
			state = BinaryUploader.save(this.request, this.conf, propertyService);
		}

		return state;
	}
}
