package com.syx.taobao.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.syx.taobao.common.AppConstant;
import com.syx.taobao.controller.common.AbstractController;
import com.syx.taobao.exception.BizException;
import com.syx.taobao.model.Properties;
import com.syx.taobao.service.PropertiesService;
import com.syx.taobao.vo.ResultVo;

@Controller
@RequestMapping("setting/properties")
public class PropertiesController extends AbstractController {

	@Autowired
	private PropertiesService propertiesService;

	@RequestMapping("list")
	public String list(HttpServletRequest request, ModelMap map) {
		List<Properties> list = null;
		try {
			list = propertiesService.queryAllProperties();
		} catch (BizException e) {
			e.printStackTrace();
		}
		if (list == null) {
			list = new ArrayList<Properties>();
		}
		map.addAttribute("list", list);
		return "setting/properties/list";
	}

	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String addview(Integer parentId, HttpServletRequest request, ModelMap map) {
		return "setting/properties/add";
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public ResultVo add(Properties o, HttpServletRequest request, ModelMap map) {
		try {
			propertiesService.addProperties(o.getKey(), o.getValue());
		} catch (BizException e) {
			e.printStackTrace();
		}
		return new ResultVo(AppConstant.RESULT_STATUS_SUCCESS, null, null);
	}

	@RequestMapping(value = "update", method = RequestMethod.GET)
	public String updateview(@RequestParam("key") String key, HttpServletRequest request, ModelMap map) {
		Properties o = null;
		try {
			o = propertiesService.queryProperties(key);
		} catch (BizException e) {
			e.printStackTrace();
		}
		if (o == null) {
			o = new Properties();
		}
		map.addAttribute("bean", o);
		return "setting/properties/update";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public ResultVo update(Properties o, HttpServletRequest request, ModelMap map) {
		try {
			propertiesService.updateProperties(o.getKey(), o.getValue());
		} catch (BizException e) {
			e.printStackTrace();
		}
		return new ResultVo(AppConstant.RESULT_STATUS_SUCCESS, null, null);
	}

	@RequestMapping("delete")
	public String delete(@RequestParam("id") int id, HttpServletRequest request, ModelMap map) {
		try {
			propertiesService.deleteProperties(id);
		} catch (BizException e) {
			e.printStackTrace();
		}
		return "redirect:/setting/properties/list.htm";
	}
}
