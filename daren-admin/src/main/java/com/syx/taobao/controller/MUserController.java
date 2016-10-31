package com.syx.taobao.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.syx.taobao.common.AppConstant;
import com.syx.taobao.controller.common.AbstractController;
import com.syx.taobao.exception.BizException;
import com.syx.taobao.model.MUser;
import com.syx.taobao.model.authority.Role;
import com.syx.taobao.model.common.Pager;
import com.syx.taobao.service.MUserService;
import com.syx.taobao.service.RoleService;
import com.syx.taobao.util.MD5;
import com.syx.taobao.util.StringUtil;
import com.syx.taobao.vo.ResultVo;

@Controller
@RequestMapping("muser")
public class MUserController extends AbstractController {
	@Autowired
	private MUserService mUserService;
	@Autowired
	private RoleService roleService;

	@RequestMapping("list")
	public String list(MUser user, HttpSession session, HttpServletRequest request, ModelMap map) {
		if (user == null) {
			user = new MUser();
		}
		map.addAttribute("user", user);
		Pager<MUser> pager = null;
		try {
			pager = mUserService.queryMUserPager(user, getPageIndex(request), getPageSize(request));
		} catch (BizException e) {
			e.printStackTrace();
		}
		if (pager == null) {
			pager = new Pager<MUser>(0, 0);
		}
		map.addAttribute("pager", pager);
		return "muser/list";
	}

	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String add(HttpSession session, HttpServletRequest request, ModelMap map) {
		Integer mId = (Integer) session.getAttribute(AppConstant.SESSION_MUSER_ID);
		List<Role> roles = null;
		MUser user = null;
		try {
			roles = roleService.queryAllRole();
			user = mUserService.queryMUser(mId);
		} catch (BizException e) {
			e.printStackTrace();
		}
		if (roles == null) {
			roles = new ArrayList<Role>();
		}
		if (user == null) {
			user = new MUser();
		}

		map.addAttribute("roles", roles);
		map.addAttribute("mUser", user);

		return "muser/add";
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String muser_add(MUser user, int roleId, HttpSession session, HttpServletRequest request, ModelMap map) {
		try {
			if (StringUtil.isNotNull(user.getPassword())) {
				String pwd = MD5.MD5Encode(user.getPassword());
				user.setPassword(pwd);
				if (roleId > 0) {
					Role r = roleService.queryRole(roleId);
					user.setRole(r);
				}
				mUserService.addMUser(user);
			}
		} catch (BizException e) {
			e.printStackTrace();
		}
		return list(null, session, request, map);
	}

	@RequestMapping(value = "update", method = RequestMethod.GET)
	public String update(@RequestParam("id") int id, HttpSession session, HttpServletRequest request, ModelMap map) {

		List<Role> roles = null;
		MUser mUser = null;
		try {
			roles = roleService.queryAllRole();
			mUser = mUserService.queryMUser(id);
		} catch (BizException e) {
			e.printStackTrace();
		}
		if (roles == null) {
			roles = new ArrayList<Role>();
		}
		if (mUser == null) {
			mUser = new MUser();
		}
		map.addAttribute("roles", roles);
		map.addAttribute("mUser", mUser);

		return "muser/update";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@RequestParam("id") int id, @RequestParam("roleId") int roleId,MUser muser, HttpSession session,
			HttpServletRequest request, ModelMap map) {
		try {
			MUser user = mUserService.queryMUser(id);
			if (roleId > 0) {
				Role r = roleService.queryRole(roleId);
				user.setRole(r);
			}
			
			user.setNickname(muser.getNickname());
			user.setAvatar(muser.getAvatar());
			user.setSign(muser.getSign());
			user.setPhone(muser.getPhone());
			user.setAddress(muser.getAddress());
			user.setEmail(muser.getEmail());
			user.setLevel(muser.getLevel());
			
			mUserService.updateMUser(user);
		} catch (BizException e) {
			e.printStackTrace();
		}
		return list(null, session, request, map);
	}

	@RequestMapping("updateStatus")
	public String muser_updateStatus(int[] ids, @RequestParam("recStatus") String recStatus, HttpSession session,
			HttpServletRequest request, ModelMap map) {
		try {
			mUserService.updateMUserStatus(ids, recStatus);
		} catch (BizException e) {
			e.printStackTrace();
		}
		return list(null, session, request, map);
	}

	@RequestMapping(value = "editpwd", method = RequestMethod.GET)
	public String editpwd() {
		return "muser/editpwd";
	}

	@RequestMapping(value = "editpwd", method = RequestMethod.POST)
	public String editpwd_post(@RequestParam("pwd") String pwd, @RequestParam("newpwd") String newpwd,
			HttpSession session, ModelMap map) {
		int id = (Integer) session.getAttribute(AppConstant.SESSION_MUSER_ID);
		try {
			ResultVo result = mUserService.editPwd(id, pwd, newpwd);
			if (result.getStatus() == AppConstant.RESULT_STATUS_ERROR) {
				map.addAttribute("msg", result.getMessage());
				return editpwd();
			}
		} catch (BizException e) {
			e.printStackTrace();
		}
		return "redirect:/signout.htm";
	}

}