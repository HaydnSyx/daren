package com.syx.taobao.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.syx.taobao.dao.PermDao;
import com.syx.taobao.dao.common.SimpleDao;
import com.syx.taobao.exception.DaoException;
import com.syx.taobao.model.authority.Perm;

@Repository
public class PermDaoImpl extends SimpleDao<Perm, Integer> implements PermDao {

	private Logger logger = LoggerFactory.getLogger(PermDaoImpl.class);

	@Override
	public void addPerm(Perm p) throws DaoException {
		add(p);
	}

	@Override
	public void deletePerm(int funcId, int roleId) throws DaoException {
		try {
			String hql = "delete from Perm bean where 1=1";
			if (funcId > 0) {
				hql += " and bean.funcId=" + funcId;
			}
			if (roleId > 0) {
				hql += " and bean.roleId=" + roleId;
			}
			getSession().createQuery(hql).executeUpdate();
		} catch (HibernateException e) {
			logger.error("delete Perm failure! funcId = [" + funcId + "] roleId = [" + roleId + "] ", e);
			throw new DaoException("delete Perm failure! funcId = [" + funcId + "] roleId = [" + roleId + "] ");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Perm> queryPermList(int roleId) throws DaoException {
		try {
			String hql = "from Perm bean where bean.roleId=" + roleId;
			return getSession().createQuery(hql).list();
		} catch (HibernateException e) {
			logger.error("query Perm List failure! roleId = [" + roleId + "] ", e);
			throw new DaoException("query Perm List failure! roleId = [" + roleId + "] ");
		}
	}

}
