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
import com.syx.taobao.model.authority.Module;
import com.syx.taobao.service.ModuleService;

@Controller
@RequestMapping("module")
public class ModuleController extends AbstractController{
	@Autowired
	private ModuleService moduleService;
	
	@RequestMapping("list")
	public String list(Integer parentId,HttpServletRequest request,ModelMap map) {
		int pid = -1;
		if(parentId != null && parentId > 0) {
			pid = parentId;
			map.addAttribute("parentId", parentId);
		}
		List<Module> list = null;
		try {
			list = moduleService.queryAllModule(pid);
		} catch (BizException e) {
			e.printStackTrace();
		}
		if(list == null) {
			list = new ArrayList<Module>();
		}
		map.addAttribute("list", list);
		return "authority/module/list";
	}
	
	@RequestMapping(value="add",method=RequestMethod.GET)
	public String addview(Integer parentId,HttpServletRequest request,ModelMap map) {
		map.addAttribute("parentId", parentId);
		return "authority/module/add";
	}
	
	@RequestMapping(value="add",method=RequestMethod.POST)
	public String add(Module pt,HttpServletRequest request,ModelMap map) {
		try {
			moduleService.addModule(pt);
		} catch (BizException e) {
			e.printStackTrace();
		}
		return list(pt.getpId(),request, map);
	}
	
	@RequestMapping(value="update",method=RequestMethod.GET)
	public String updateview(@RequestParam("id")int id,HttpServletRequest request,ModelMap map) {
		Module pt = null;
		try {
			pt = moduleService.queryModule(id);
		} catch (BizException e) {
			e.printStackTrace();
		}
		if(pt == null) {
			pt = new Module();
		}
		map.addAttribute("bean", pt);
		return "authority/module/update";
	}
	
	@RequestMapping(value="update",method=RequestMethod.POST)
	public String update(Module pt,HttpServletRequest request,ModelMap map) {
		try {
			moduleService.updateModule(pt);
			PermComponent.urlsMap.clear();
		} catch (BizException e) {
			e.printStackTrace();
		}
		return list(pt.getpId(),request, map);
	}
	   
	@RequestMapping("delete")
	public String delete(@RequestParam("ids")int[] ids,HttpServletRequest request,ModelMap map) {
		try {
			moduleService.deleteModule(ids);
		} catch (BizException e) {
			e.printStackTrace();
		}
		return list(null,request, map);
	}
	
}
