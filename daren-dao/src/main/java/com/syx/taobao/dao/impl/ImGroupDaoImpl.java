package com.syx.taobao.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.syx.taobao.dao.ImGroupDao;
import com.syx.taobao.dao.common.SimpleDao;
import com.syx.taobao.exception.DaoException;
import com.syx.taobao.model.ImGroup;

@Repository
public class ImGroupDaoImpl extends SimpleDao<ImGroup, Integer> implements ImGroupDao {

	@Override
	public void addImGroup(ImGroup o) throws DaoException {
		add(o);
	}

	@Override
	public void deleteImGroup(int id) throws DaoException {
		ImGroup o = get(id);
		if (o != null) {
			delete(o);
		}
	}

	@Override
	public void updateImGroup(ImGroup o) throws DaoException {
		update(o);
	}

	@Override
	public ImGroup queryImGroup(int id) throws DaoException {
		return get(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ImGroup> queryImGroupByMId(int mId) throws DaoException {
		StringBuffer hql = new StringBuffer();
		hql.append(" from ImGroup bean where bean.mId = :mId order by bean.id asc ");
		return getSession().createQuery(hql.toString()).setParameter("mId", mId).list();
	}
}
