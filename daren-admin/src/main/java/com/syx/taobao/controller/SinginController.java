package com.syx.taobao.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.syx.taobao.common.AppConstant;
import com.syx.taobao.service.MUserService;
import com.syx.taobao.util.MD5;
import com.syx.taobao.vo.ResultVo;

@Controller
public class SinginController {

	@Autowired
	private MUserService mUserService;

	@RequestMapping(value = "signin", method = RequestMethod.GET)
	public String signin() {
		return "signin";
	}

	@RequestMapping(value = "signin", method = RequestMethod.POST)
	@ResponseBody
	public ResultVo login(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session) {
		String pwd = MD5.MD5Encode(password);
		ResultVo vo = mUserService.login(username, pwd);
		if (vo.getStatus() == AppConstant.RESULT_STATUS_SUCCESS) {
			session.setAttribute(AppConstant.SESSION_MUSER_ID, vo.getData());
			session.setAttribute(AppConstant.SESSION_MUSER_NAME, username);
			session.removeAttribute(AppConstant.SESSION_MUSER);
		}
		return vo;
	}

	@RequestMapping("signout")
	public String logout(HttpSession session) {
		session.removeAttribute(AppConstant.SESSION_MUSER_ID);
		session.removeAttribute(AppConstant.SESSION_MUSER_NAME);
		session.removeAttribute(AppConstant.SESSION_MUSER);
		return signin();
	}

}
