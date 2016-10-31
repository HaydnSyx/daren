package com.syx.taobao.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.syx.taobao.common.AppConstant;
import com.syx.taobao.component.PermComponent;
import com.syx.taobao.exception.BizException;
import com.syx.taobao.model.MUser;
import com.syx.taobao.service.MUserService;

public class MUserFilter implements Filter {

	private PermComponent permComponent;
	private MUserService mUserService;

	public void init(FilterConfig config) throws ServletException {
		ServletContext context = config.getServletContext();
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
		permComponent = (PermComponent) ctx.getBean("permComponent");
		mUserService = (MUserService) ctx.getBean("MUserServiceImpl");
	}

	public void destroy() {

	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		String requestUrl = ((HttpServletRequest) request).getRequestURI();

		if (!isMuserMainPage(requestUrl)) {

			if (isNeedCheckPage(requestUrl, request)) {
				if (!isMUserLogin(request)) {
					response.sendRedirect(request.getContextPath() + "/signin.htm");
					return;
				} else {
					try {
						HttpSession session = request.getSession();
						Integer muserId = (Integer) session.getAttribute(AppConstant.SESSION_MUSER_ID);
						String cp = request.getContextPath();
						String url = requestUrl;
						if (cp.length() > 0) {
							url = requestUrl.substring(cp.length());
						}
						if (url.equals("/")) {
							url = "/index.htm";
						}
						// 比较权限 防止每次都需要查询 是否修改成放入session
						MUser user = (MUser) session.getAttribute(AppConstant.SESSION_MUSER);
						if (user == null) {
							user = mUserService.queryMUser(muserId);
							session.setAttribute(AppConstant.SESSION_MUSER, user);
						}
						boolean status = permComponent.validMUserPerm(user, url);
						if (!status) {
							System.out.println("no perm : " + url);
							response.sendRedirect(request.getContextPath() + "/html/noperm.html");
							return;
						}
					} catch (BizException e) {
						return;
					}
				}
			}
		}
		chain.doFilter(req, resp);
	}

	private boolean isMUserLogin(HttpServletRequest request) {
		Integer adminId = (Integer) request.getSession().getAttribute(AppConstant.SESSION_MUSER_ID);
		if (adminId == null) {
			return false;
		}
		return true;
	}

	private boolean isMuserMainPage(String url) {
		return url.contains("/signin.htm");
	}

	private boolean isNeedCheckPage(String url, HttpServletRequest request) {
		String contextPath = request.getContextPath();
		if (url.equals(contextPath + "/")) {
			return true;
		}
		if (url.startsWith(contextPath + "/user")) {
			return false;
		}
		return true;
	}

}
