package com.syx.taobao.controller.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

import com.syx.taobao.util.StringUtil;

public class AbstractController {

	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(AbstractController.class);

	public String getRemoteIP(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if (!StringUtils.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		ip = request.getHeader("X-Forwarded-For");
		if (!StringUtils.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个IP值，第一个为真实IP。
			int index = ip.indexOf(',');
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		} else {
			return request.getRemoteAddr();
		}
	}

	protected Object getSessionValue(String name, HttpSession session) {
		return session.getAttribute(name);
	}

	/**
	 * 获取项目url
	 */
	protected String getContextUrl(HttpServletRequest request) {
		String contextUrl = "http://" + request.getServerName() // 服务器地址
				+ ":" + request.getServerPort() // 端口号
				+ request.getContextPath(); // 项目名称
		return contextUrl;
	}

	protected ResponseEntity<byte[]> getDownloadResponseEntity(byte[] data, String fileName, MediaType mediaType) {
		HttpHeaders headers = new HttpHeaders();
		MediaType mt = MediaType.APPLICATION_OCTET_STREAM;
		if (mediaType != null) {
			mt = mediaType;
		}
		headers.setContentType(mt);
		headers.setContentDispositionFormData("attachment", fileName);
		// HttpStatus.CREATED 修改为 HttpStatus.OK ie不认识201
		return new ResponseEntity<byte[]>(data, headers, HttpStatus.OK);
	}

	protected Integer getIntParameter(String name, Integer defaultValue, HttpServletRequest request) {
		String value = request.getParameter(name);
		if (StringUtil.isNull(value)) {
			return defaultValue;
		}

		try {
			return Integer.parseInt(value);
		} catch (Exception ex) {
			return defaultValue;
		}

	}

	protected int getPageIndex(HttpServletRequest request) {
		return getIntParameter("pageIndex", 1, request);
	}

	protected int getPageSize(HttpServletRequest request) {
		return getIntParameter("pageSize", 10, request);
	}
}
