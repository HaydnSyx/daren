package com.syx.taobao.dao.impl;

import org.springframework.stereotype.Repository;

import com.syx.taobao.dao.ImFriendGroupMemDao;
import com.syx.taobao.dao.common.SimpleDao;
import com.syx.taobao.exception.DaoException;
import com.syx.taobao.model.ImFriendGroupMem;

@Repository
public class ImFriendGroupMemDaoImpl extends SimpleDao<ImFriendGroupMem, Integer> implements ImFriendGroupMemDao {

	@Override
	public void addImFriendGroupMem(ImFriendGroupMem o) throws DaoException {
		add(o);
	}

	@Override
	public void deleteImFriendGroupMem(int id) throws DaoException {
		ImFriendGroupMem o = get(id);
		if (o != null) {
			delete(o);
		}
	}

	@Override
	public void updateImFriendGroupMem(ImFriendGroupMem o) throws DaoException {
		update(o);
	}

	@Override
	public ImFriendGroupMem queryImFriendGroupMem(int id) throws DaoException {
		return get(id);
	}

}
