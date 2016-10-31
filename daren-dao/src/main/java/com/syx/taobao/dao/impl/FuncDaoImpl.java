package com.syx.taobao.dao.impl;

import org.springframework.stereotype.Repository;

import com.syx.taobao.dao.FuncDao;
import com.syx.taobao.dao.common.SimpleDao;
import com.syx.taobao.exception.DaoException;
import com.syx.taobao.model.authority.Func;

@Repository
public class FuncDaoImpl extends SimpleDao<Func, Integer> implements FuncDao {

	@Override
	public void addFunc(Func f) throws DaoException {
		add(f);
	}

	@Override
	public void updateFunc(Func f) throws DaoException {
		update(f);
	}

	@Override
	public void deleteFunc(int id) throws DaoException {
		Func f = get(id);
		if(f != null) {
			delete(f);
		}
	}

	@Override
	public Func queryFunc(int id) throws DaoException {
		return get(id);
	}

}
