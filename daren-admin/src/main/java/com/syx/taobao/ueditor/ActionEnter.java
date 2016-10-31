package com.syx.taobao.ueditor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.syx.taobao.exception.BizException;
import com.syx.taobao.service.PropertiesService;
import com.syx.taobao.ueditor.define.ActionMap;
import com.syx.taobao.ueditor.define.AppInfo;
import com.syx.taobao.ueditor.define.BaseState;
import com.syx.taobao.ueditor.define.State;
import com.syx.taobao.ueditor.hunter.FileManager;
import com.syx.taobao.ueditor.hunter.ImageHunter;
import com.syx.taobao.ueditor.upload.Uploader;

public class ActionEnter {

	private HttpServletRequest request = null;

	private String rootPath = null;
	private String contextPath = null;

	private String actionType = null;

	private ConfigManager configManager = null;

	private PropertiesService propertyService;

	public ActionEnter(HttpServletRequest request, String rootPath, PropertiesService propertyService) {

		this.request = request;
		this.rootPath = rootPath;
		this.actionType = request.getParameter("action");
		this.contextPath = request.getContextPath();
		this.propertyService = propertyService;
		this.configManager = ConfigManager.getInstance(this.rootPath, this.contextPath, request.getRequestURI());

	}

	public String exec() throws BizException, FileNotFoundException, IOException {

		String callbackName = this.request.getParameter("callback");

		if (callbackName != null) {

			if (!validCallbackName(callbackName)) {
				return new BaseState(false, AppInfo.ILLEGAL).toJSONString();
			}

			return callbackName + "(" + this.invoke() + ");";

		} else {
			return this.invoke();
		}

	}

	public String invoke() throws BizException, FileNotFoundException, IOException {

		if (actionType == null || !ActionMap.mapping.containsKey(actionType)) {
			return new BaseState(false, AppInfo.INVALID_ACTION).toJSONString();
		}

		if (this.configManager == null || !this.configManager.valid()) {
			return new BaseState(false, AppInfo.CONFIG_ERROR).toJSONString();
		}

		State state = null;

		int actionCode = ActionMap.getType(this.actionType);

		Map<String, Object> conf = null;

		switch (actionCode) {

		case ActionMap.CONFIG:
			return this.configManager.getAllConfig().toString();

		case ActionMap.UPLOAD_IMAGE:
		case ActionMap.UPLOAD_SCRAWL:
		case ActionMap.UPLOAD_VIDEO:
		case ActionMap.UPLOAD_FILE:
			conf = this.configManager.getConfig(actionCode);
			state = new Uploader(request, conf, propertyService).doExec();
			break;

		case ActionMap.CATCH_IMAGE:
			conf = configManager.getConfig(actionCode);
			String[] list = this.request.getParameterValues((String) conf.get("fieldName"));
			state = new ImageHunter(conf, propertyService).capture(list);

			break;

		case ActionMap.LIST_IMAGE:
		case ActionMap.LIST_FILE:
			conf = configManager.getConfig(actionCode);
			int start = this.getStartIndex();
			state = new FileManager(conf, propertyService).listFile(start);
			break;

		}
		if (state != null) {
			return state.toJSONString();
		}

		return "";
	}

	public int getStartIndex() {

		String start = this.request.getParameter("start");

		try {
			return Integer.parseInt(start);
		} catch (Exception e) {
			return 0;
		}

	}

	/**
	 * callback参数验证
	 */
	public boolean validCallbackName(String name) {

		if (name.matches("^[a-zA-Z_]+[\\w0-9_]*$")) {
			return true;
		}

		return false;

	}

}