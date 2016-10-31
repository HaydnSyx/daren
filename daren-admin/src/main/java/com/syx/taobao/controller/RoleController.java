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
import com.syx.taobao.component.PermComponent;
import com.syx.taobao.controller.common.AbstractController;
import com.syx.taobao.exception.BizException;
import com.syx.taobao.model.MUser;
import com.syx.taobao.model.authority.Perm;
import com.syx.taobao.model.authority.Role;
import com.syx.taobao.model.common.Pager;
import com.syx.taobao.service.MUserService;
import com.syx.taobao.service.ModuleService;
import com.syx.taobao.service.RoleService;
import com.syx.taobao.vo.ModuleVo;
import com.syx.taobao.vo.ResultVo;

@Controller
@RequestMapping("role")
public class RoleController extends AbstractController {
	@Autowired
	private RoleService roleService;
	@Autowired
	private ModuleService moduleService;
	@Autowired
	private MUserService mUserService;

	@RequestMapping("list")
	public String list(HttpServletRequest request, ModelMap map) {

		Pager<Role> pager = null;
		try {
			pager = roleService.queryRolePager(getPageIndex(request), getPageSize(request));
		} catch (BizException e) {
			e.printStackTrace();
		}
		if (pager == null) {
			pager = new Pager<Role>(0, 0);
		}
		map.addAttribute("pager", pager);
		return "authority/role/list";
	}

	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String addview(HttpServletRequest request, ModelMap map) {
		return "authority/role/add";
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String add(Role pt, HttpServletRequest request, ModelMap map) {
		try {
			roleService.addRole(pt);
		} catch (BizException e) {
			e.printStackTrace();
		}
		return list(request, map);
	}

	@RequestMapping(value = "update", method = RequestMethod.GET)
	public String updateview(@RequestParam("id") int id, HttpServletRequest request, ModelMap map) {
		Role pt = null;
		try {
			pt = roleService.queryRole(id);
		} catch (BizException e) {
			e.printStackTrace();
		}
		if (pt == null) {
			pt = new Role();
		}
		map.addAttribute("bean", pt);
		return "authority/role/update";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(Role pt, HttpServletRequest request, ModelMap map) {
		try {
			roleService.updateRole(pt);
		} catch (BizException e) {
			e.printStackTrace();
		}
		return list(request, map);
	}

	@RequestMapping("delete")
	public String delete(@RequestParam("ids") int[] ids, HttpServletRequest request, ModelMap map) {
		try {
			roleService.deleteRole(ids);
		} catch (BizException e) {
			e.printStackTrace();
		}
		return list(request, map);
	}

	@RequestMapping(value = "addPerm", method = RequestMethod.GET)
	public String addPerm(@RequestParam("id") int id, HttpServletRequest request, ModelMap map) {
		Role pt = null;
		try {
			pt = roleService.queryRole(id);
			List<Perm> list = roleService.queryAllPerm(id);
			map.addAttribute("perms", list);

		} catch (BizException e) {
			e.printStackTrace();
		}
		if (pt == null) {
			pt = new Role();
		}
		map.addAttribute("bean", pt);

		List<ModuleVo> modules = null;
		try {
			modules = moduleService.queryAllModuleVo();
		} catch (BizException e) {
			e.printStackTrace();
		}
		if (modules == null) {
			modules = new ArrayList<ModuleVo>();
		}
		map.addAttribute("modules", modules);

		return "authority/role/addPerm";
	}

	@RequestMapping(value = "addPerm", method = RequestMethod.POST)
	public String addPerm(@RequestParam("roleId") int roleId, int[] funcIds, HttpServletRequest request, ModelMap map) {
		try {
			roleService.addPerms(roleId, funcIds);
			PermComponent.urlsMap.remove(roleId);
		} catch (BizException e) {
			e.printStackTrace();
		}
		return list(request, map);
	}

	@RequestMapping(value = "selectRole", method = RequestMethod.POST)
	@ResponseBody
	public ResultVo addRole(@RequestParam("roleId") int roleId, @RequestParam("mUserId") int mUserId, HttpServletRequest request, ModelMap map) {
		try {
			Role r = roleService.queryRole(roleId);
			MUser user = mUserService.queryMUser(mUserId);
			if (r != null && user != null) {
				user.setRole(r);
				mUserService.updateMUser(user);
				return new ResultVo(AppConstant.RESULT_STATUS_SUCCESS, "", "");
			}
		} catch (BizException e) {
			e.printStackTrace();
		}
		return new ResultVo(AppConstant.RESULT_STATUS_ERROR, "分配角色异常", "");
	}
}
