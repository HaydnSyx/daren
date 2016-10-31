package com.syx.taobao.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.syx.taobao.dao.RoleDao;
import com.syx.taobao.dao.common.SimpleDao;
import com.syx.taobao.exception.DaoException;
import com.syx.taobao.model.authority.Role;
import com.syx.taobao.model.common.Pager;

@Repository
public class RoleDaoImpl extends SimpleDao<Role, Integer> implements RoleDao {

	@Override
	public void addRole(Role f) throws DaoException {
		add(f);
	}

	@Override
	public void updateRole(Role f) throws DaoException {
		update(f);
	}

	@Override
	public void deleteRole(int id) throws DaoException {
		Role f = get(id);
		if(f != null) {
			delete(f);
		}
	}

	@Override
	public Role queryRole(int id) throws DaoException {
		return get(id);
	}

	@Override
	public Pager<Role> queryRolePager(int pageIndex, int pageSize)
			throws DaoException {
		String hql = "from Role bean ";
		return getPager(hql, null, pageIndex, pageSize);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> queryAllRole() throws DaoException {
		String hql = "from Role bean ";
		return find(hql, null);
	}

}
