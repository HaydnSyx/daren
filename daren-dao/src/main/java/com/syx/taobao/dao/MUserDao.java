package com.syx.taobao.dao;

import java.util.List;

import com.syx.taobao.exception.DaoException;
import com.syx.taobao.model.MUser;
import com.syx.taobao.model.common.Pager;

public interface MUserDao {

	public MUser queryMUserByUsername(String username) throws DaoException;

	public MUser queryMUser(int uid) throws DaoException;

	public Pager<MUser> queryMUserPager(MUser user, int pageIndex, int pageSize) throws DaoException;

	public void updateMUser(MUser muser) throws DaoException;

	public void addMUser(MUser user) throws DaoException;

	public List<MUser> queryMUserListByRoleId(int roleId) throws DaoException;

	public List<MUser> queryMUserAll() throws DaoException;

	public List<MUser> queryMUserByRoleId(Integer[] roleIdArr) throws DaoException;
}
