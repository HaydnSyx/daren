package com.syx.taobao.directive;

import static freemarker.template.ObjectWrapper.DEFAULT_WRAPPER;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.syx.taobao.common.AppConstant;
import com.syx.taobao.component.PermComponent;
import com.syx.taobao.controller.common.AbstractController;
import com.syx.taobao.exception.BizException;
import com.syx.taobao.model.MUser;
import com.syx.taobao.service.MUserService;

import freemarker.core.Environment;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class PermDirective extends AbstractController implements TemplateDirectiveModel {
	private Logger logger = LoggerFactory.getLogger(PermDirective.class);
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private PermComponent permComponent;
	@Autowired
	private MUserService mUserService;

	@SuppressWarnings("rawtypes")
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		HttpSession session = request.getSession();
		boolean flag = false;
		try {
			SimpleScalar u = (SimpleScalar) params.get("url");
			int muserId = 0;
			Object mId = getSessionValue(AppConstant.SESSION_MUSER_ID, session);
			if (mId != null && (Integer) mId > 0) {
				muserId = (Integer) mId;
			}

			MUser user = (MUser) session.getAttribute(AppConstant.SESSION_MUSER);
			if (user == null) {
				user = mUserService.queryMUser(muserId);
				session.setAttribute(AppConstant.SESSION_MUSER, user);
			}

			String url = u.toString();
			boolean hasPerm = permComponent.validMUserPerm(user, url);
			if (hasPerm) {
				flag = true;
			}
		} catch (BizException e) {
			logger.error("get user login info directive failure!", e);
		} catch (Throwable t) {
			logger.error("get user login info directive failure!", t);
		}

		env.setVariable("hasPerm", DEFAULT_WRAPPER.wrap(flag));
		body.render(env.getOut());
	}

}
