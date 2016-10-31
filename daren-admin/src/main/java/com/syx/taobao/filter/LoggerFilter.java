package com.syx.taobao.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.syx.taobao.common.AppConstant;
import com.syx.taobao.controller.common.AbstractController;

public class LoggerFilter extends AbstractController implements Filter {

	private Logger logger = LoggerFactory.getLogger(LoggerFilter.class);

	public void init(FilterConfig filterConfig) throws ServletException {

	}

	public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		StringBuilder sb = new StringBuilder();
		HttpServletRequest request = (HttpServletRequest) req;
		String ip = getRemoteIP(request);
		sb.append(" ip=" + ip);
		Map<String, Object> params = getRequestData(request);
		Integer adminId = (Integer) request.getSession().getAttribute(AppConstant.SESSION_MUSER_ID);
		String username = (String) request.getSession().getAttribute(AppConstant.SESSION_MUSER_NAME);
		if (adminId != null) {
			sb.append(" adminId=").append(adminId).append("[username=").append(username).append("]");
		}

		sb.append("  url=").append(request.getRequestURL());

		if (params != null) {
			sb.append("  params=").append(params.toString());
		}

		logger.info(sb.toString());

		chain.doFilter(req, response);
	}

	public void destroy() {

	}

	@SuppressWarnings("rawtypes")
	public Map<String, Object> getRequestData(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		Enumeration enumeration = request.getParameterNames();// 获取表单内所有元素
		if (enumeration.hasMoreElements()) {
			while (enumeration.hasMoreElements()) {
				String inputName = (String) enumeration.nextElement();// 获取元素名

				if (!isSensitiveName(inputName)) {

					String value = request.getParameter(inputName);
					/*
					 * //参数值太长的截取 if(StringUtil.isNotNull(value) &&
					 * value.length() > 200) { value = value.substring(0,200) +
					 * "..."; }
					 */

					map.put(inputName, value);// 以表单名作为key
				}
			}
		}
		return map;
	}

	/**
	 * 是否是敏感的参数
	 * 
	 * @param inputName
	 * @return
	 */
	private boolean isSensitiveName(String inputName) {
		if (inputName.startsWith("password") || inputName.startsWith("pwd") || inputName.startsWith("newpwd")) {
			return true;
		}
		return false;
	}

}
