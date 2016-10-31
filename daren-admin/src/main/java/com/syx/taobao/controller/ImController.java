package com.syx.taobao.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.syx.taobao.common.AppConstant;
import com.syx.taobao.controller.common.AbstractController;
import com.syx.taobao.exception.BizException;
import com.syx.taobao.service.CommonImService;
import com.syx.taobao.vo.ImVo;

@Controller
@RequestMapping("im")
public class ImController extends AbstractController {

	private Logger logger = LoggerFactory.getLogger(ImController.class);

	@Autowired
	private CommonImService commonImService;

	@RequestMapping(value = "init", method = RequestMethod.GET)
	@ResponseBody
	protected ImVo init(HttpSession session, HttpServletRequest request, ModelMap map) throws JsonProcessingException {
		Integer mId = (Integer) session.getAttribute(AppConstant.SESSION_MUSER_ID);
		ImVo vo = null;
		try {
			vo = commonImService.getAllDataByMId(mId);
		} catch (BizException e) {
			logger.error("get im info failed! mId=[" + mId + "]");
		}
		if (vo == null) {
			vo = new ImVo();
		}
		return vo;
	}
}
