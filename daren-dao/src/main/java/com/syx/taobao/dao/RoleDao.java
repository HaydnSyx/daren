package com.syx.taobao.dao;

import java.util.List;

import com.syx.taobao.exception.DaoException;
import com.syx.taobao.model.authority.Role;
import com.syx.taobao.model.common.Pager;

public interface RoleDao {

	public void addRole(Role f) throws DaoException;

	public void updateRole(Role f) throws DaoException;

	public void deleteRole(int id) throws DaoException;

	public Role queryRole(int id) throws DaoException;

	public Pager<Role> queryRolePager(int pageIndex, int pageSize) throws DaoException;

	public List<Role> queryAllRole() throws DaoException;
}
