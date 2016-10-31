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

import com.syx.taobao.component.PermComponent;
import com.syx.taobao.controller.common.AbstractController;
import com.syx.taobao.exception.BizException;
import com.syx.taobao.model.authority.Func;
import com.syx.taobao.model.authority.Module;
import com.syx.taobao.service.FuncService;
import com.syx.taobao.service.ModuleService;
import com.syx.taobao.vo.ModuleVo;

@Controller
@RequestMapping("func")
public class FuncController extends AbstractController{
	@Autowired
	private FuncService funcService;
	@Autowired
	private ModuleService moduleService;
	
	@RequestMapping("list")
	public String list(HttpServletRequest request,ModelMap map) {
		
		List<ModuleVo> list = null;
		try {
			list = moduleService.queryAllModuleVo();
		} catch (BizException e) {
			e.printStackTrace();
		}
		if(list == null) {
			list = new ArrayList<ModuleVo>();
		}
		map.addAttribute("list", list);
		return "authority/func/list";
	}
	
	@RequestMapping(value="add",method=RequestMethod.GET)
	public String addview(Integer moduleId,HttpServletRequest request,ModelMap map) {
		map.addAttribute("moduleId", moduleId);
		return "authority/func/add";
	}
	
	@RequestMapping(value="add",method=RequestMethod.POST)
	public String add(Func pt,int moduleId,HttpServletRequest request,ModelMap map) {
		try {
			Module m = moduleService.queryModule(moduleId);
			if(m != null) {
				pt.setModule(m);
				funcService.addFunc(pt);
			}
		} catch (BizException e) {
			e.printStackTrace();
		}
		return list(request, map);
	}
	
	@RequestMapping(value="update",method=RequestMethod.GET)
	public String updateview(@RequestParam("id")int id,HttpServletRequest request,ModelMap map) {
		Func pt = null;
		try {
			pt = funcService.queryFunc(id);
		} catch (BizException e) {
			e.printStackTrace();
		}
		if(pt == null) {
			pt = new Func();
		}
		map.addAttribute("bean", pt);
		return "authority/func/update";
	}
	
	@RequestMapping(value="update",method=RequestMethod.POST)
	public String update(Func pt,HttpServletRequest request,ModelMap map) {
		try {
			funcService.updateFunc(pt);
			PermComponent.urlsMap.clear();
		} catch (BizException e) {
			e.printStackTrace();
		}
		return list(request, map);
	}
	   
	@RequestMapping("delete")
	public String delete(@RequestParam("ids")int[] ids,HttpServletRequest request,ModelMap map) {
		try {
			funcService.deleteFunc(ids);
		} catch (BizException e) {
			e.printStackTrace();
		}
		return list(request, map);
	}
	
}
