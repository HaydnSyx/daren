package com.syx.taobao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.syx.taobao.dao.PermDao;
import com.syx.taobao.dao.RoleDao;
import com.syx.taobao.exception.BizException;
import com.syx.taobao.exception.DaoException;
import com.syx.taobao.model.authority.Perm;
import com.syx.taobao.model.authority.Role;
import com.syx.taobao.model.common.Pager;
import com.syx.taobao.service.RoleService;

@Service
@Transactional(rollbackFor = BizException.class, propagation = Propagation.REQUIRED)
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;
	@Autowired
	private PermDao permDao;

	private Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

	@Override
	public void addRole(Role qt) throws BizException {
		try {
			qt.setCreateTime(new Date());
			roleDao.addRole(qt);
		} catch (DaoException e) {
			logger.error("add Role failure! Role=[" + qt + "]", e);
			throw new BizException("add Role failure! Role=[" + qt + "]");
		}
	}

	@Override
	public void deleteRole(int id) throws BizException {
		try {
			roleDao.deleteRole(id);
		} catch (DaoException e) {
			logger.error("delete Role failure! id=[" + id + "]", e);
			throw new BizException("delete Role failure! id=[" + id + "]");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Role queryRole(int id) throws BizException {
		try {
			return roleDao.queryRole(id);
		} catch (DaoException e) {
			logger.error("query Role failure! id=[" + id + "]", e);
			throw new BizException("query Role failure! id=[" + id + "]");
		}
	}

	@Override
	public void updateRole(Role f) throws BizException {
		try {
			Role role = roleDao.queryRole(f.getId());
			if (role != null) {
				role.setName(f.getName());
				role.setContactPhone(f.getContactPhone());
				role.setIndexUrl(f.getIndexUrl());
				roleDao.updateRole(role);
			}
		} catch (DaoException e) {
			logger.error("update Role failure! Role=[" + f + "]", e);
			throw new BizException("update Role failure! Role=[" + f + "]");
		}
	}

	@Override
	public void deleteRole(int[] ids) throws BizException {
		if (ids != null && ids.length > 0) {
			for (int i = 0; i < ids.length; i++) {
				deleteRole(ids[i]);
			}
		}

	}

	@Override
	@Transactional(readOnly = true)
	public Pager<Role> queryRolePager(int pageIndex, int pageSize) throws BizException {
		try {
			return roleDao.queryRolePager(pageIndex, pageSize);
		} catch (DaoException e) {
			logger.error("query Role Pager failure! ", e);
			throw new BizException("query Role Pager failure! ");
		}
	}

	@Override
	public void addPerms(int roleId, int[] funcIds) throws BizException {

		try {
			List<Integer> addList = new ArrayList<Integer>();
			List<Integer> delList = new ArrayList<Integer>();

			Set<Integer> oldPerm = new HashSet<Integer>();
			List<Perm> ps = permDao.queryPermList(roleId);
			if (ps != null && ps.size() > 0) {
				for (int i = 0; i < ps.size(); i++) {
					Perm p = ps.get(i);
					oldPerm.add(p.getFuncId());
				}
			}
			Set<Integer> newPerm = new HashSet<Integer>();
			if (funcIds != null && funcIds.length > 0) {
				for (int i = 0; i < funcIds.length; i++) {
					int funcId = funcIds[i];
					if (!oldPerm.contains(funcId)) {
						addList.add(funcId);
					}
					newPerm.add(funcId);
				}
			}

			if (ps != null && ps.size() > 0) {
				for (int i = 0; i < ps.size(); i++) {
					Perm p = ps.get(i);
					int funcId = p.getFuncId();
					if (!newPerm.contains(funcId)) {
						delList.add(funcId);
					}
				}
			}

			if (delList.size() > 0) {
				for (int i = 0; i < delList.size(); i++) {
					permDao.deletePerm(delList.get(i), roleId);
				}
			}

			if (addList.size() > 0) {
				for (int i = 0; i < addList.size(); i++) {
					Perm p = new Perm();
					p.setRoleId(roleId);
					p.setFuncId(addList.get(i));
					permDao.addPerm(p);
				}
			}
		} catch (DaoException e) {
			logger.error("add perm failure! roleId=[" + roleId + "]", e);
			throw new BizException("add perm failure! roleId=[" + roleId + "]");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Perm> queryAllPerm(int roleId) throws BizException {
		try {
			return permDao.queryPermList(roleId);
		} catch (DaoException e) {
			logger.error("query All Perm failure! roleId=[" + roleId + "]", e);
			throw new BizException("query All Perm failure! roleId=[" + roleId + "]");
		}
	}

	@Override
	public List<Role> queryAllRole() throws BizException {
		try {
			return roleDao.queryAllRole();
		} catch (DaoException e) {
			logger.error("query All Role failure!", e);
			throw new BizException("query All Role failure!");
		}
	}
}
