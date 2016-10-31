package com.syx.taobao.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.syx.taobao.dao.ImFriendGroupDao;
import com.syx.taobao.dao.common.SimpleDao;
import com.syx.taobao.exception.DaoException;
import com.syx.taobao.model.ImFriendGroup;

@Repository
public class ImFriendGroupDaoImpl extends SimpleDao<ImFriendGroup, Integer> implements ImFriendGroupDao {

	@Override
	public void addImFriendGroup(ImFriendGroup o) throws DaoException {
		add(o);
	}

	@Override
	public void deleteImFriendGroup(int id) throws DaoException {
		ImFriendGroup o = get(id);
		if (o != null) {
			delete(o);
		}
	}

	@Override
	public void updateImFriendGroup(ImFriendGroup o) throws DaoException {
		update(o);
	}

	@Override
	public ImFriendGroup queryImFriendGroup(int id) throws DaoException {
		return get(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ImFriendGroup> queryImFriendGroupByMId(int mId) throws DaoException {
		StringBuffer hql = new StringBuffer();
		hql.append(" from ImFriendGroup bean ");
		// hql.append(" inner join fetch bean.friendGroupMems ");
		hql.append(" where bean.mId = :mId ");
		return getSession().createQuery(hql.toString()).setParameter("mId", mId).list();
	}

}
