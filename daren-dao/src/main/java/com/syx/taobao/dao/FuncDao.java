package com.syx.taobao.dao;

import com.syx.taobao.exception.DaoException;
import com.syx.taobao.model.authority.Func;

public interface FuncDao {

	public void addFunc(Func f) throws DaoException;
	
	public void updateFunc(Func f) throws DaoException;
	
	public void deleteFunc(int id) throws DaoException;
	
	public Func queryFunc(int id) throws DaoException;
}
