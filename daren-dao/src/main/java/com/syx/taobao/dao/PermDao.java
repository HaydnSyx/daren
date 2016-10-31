package com.syx.taobao.dao;

import java.util.List;

import com.syx.taobao.exception.DaoException;
import com.syx.taobao.model.authority.Perm;

public interface PermDao {

	public void addPerm(Perm p) throws DaoException;
	
	public void deletePerm(int funcId,int roleId) throws DaoException;
	
	public List<Perm> queryPermList(int roleId) throws DaoException;
}
