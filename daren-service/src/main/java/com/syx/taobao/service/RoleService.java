package com.syx.taobao.service;

import java.util.List;

import com.syx.taobao.exception.BizException;
import com.syx.taobao.model.authority.Perm;
import com.syx.taobao.model.authority.Role;
import com.syx.taobao.model.common.Pager;

public interface RoleService {

	public void addRole(Role qt) throws BizException;

	public void deleteRole(int id) throws BizException;

	public Role queryRole(int id) throws BizException;

	public void updateRole(Role f) throws BizException;

	public void deleteRole(int[] ids) throws BizException;

	public Pager<Role> queryRolePager(int pageIndex, int pageSize) throws BizException;

	public void addPerms(int roleId, int[] funcIds) throws BizException;

	public List<Perm> queryAllPerm(int roleId) throws BizException;

	public List<Role> queryAllRole() throws BizException;

}
