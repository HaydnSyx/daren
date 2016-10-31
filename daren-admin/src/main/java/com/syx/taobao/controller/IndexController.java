package com.syx.taobao.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.syx.taobao.common.AppConstant;
import com.syx.taobao.controller.common.AbstractController;

@Controller
public class IndexController extends AbstractController {
	
	
	@RequestMapping("/index")
	public String index(HttpSession session, HttpServletRequest request, Model map) {
		final Integer mId = (Integer) session.getAttribute(AppConstant.SESSION_MUSER_ID);
		if (mId == null || mId == 0) {
			return "redirect:/signin.htm";
		}
		return "index";
	}
}
