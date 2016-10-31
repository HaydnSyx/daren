package com.syx.taobao.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.syx.taobao.dao.MUserDao;
import com.syx.taobao.dao.common.SimpleDao;
import com.syx.taobao.exception.DaoException;
import com.syx.taobao.model.MUser;
import com.syx.taobao.model.common.Pager;
import com.syx.taobao.util.StringUtil;

/**
 * 管理员 dao 实现
 * 
 * @author kelvin.luo
 *
 */
@Repository
public class MUserDaoImpl extends SimpleDao<MUser, Integer> implements MUserDao {

	private Logger logger = LoggerFactory.getLogger(MUserDaoImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public MUser queryMUserByUsername(String username) throws DaoException {
		try {
			String hql = "from MUser bean where bean.username=:username ";
			List<MUser> list = getSession().createQuery(hql).setParameter("username", username).list();
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (HibernateException e) {
			logger.error("query MUser By Username failure! username = [" + username + "] ", e);
			throw new DaoException("query MUser By Username failure! username = [" + username + "] ");
		}
	}

	@Override
	public MUser queryMUser(int uid) throws DaoException {
		return get(uid);
	}

	@Override
	public List<MUser> queryMUserListByRoleId(int roleId) throws DaoException {
		return findByProperty("role.id", roleId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MUser> queryMUserAll() throws DaoException {
		String hql = "from MUser bean order by bean.recDate asc ";
		return getSession().createQuery(hql).list();
	}

	@Override
	public List<MUser> queryMUserByRoleId(Integer[] roleIdArr) throws DaoException {
		return findByCriteria(Restrictions.in("role.id", roleIdArr));
	}

	@Override
	public Pager<MUser> queryMUserPager(MUser user, int pageIndex, int pageSize) throws DaoException {
		String hql = "from MUser bean where 1=1 ";
		if (user != null) {
			if (StringUtil.isNotNull(user.getUsername())) {
				hql += " and bean.username='" + user.getUsername() + "'";
			}
			if (StringUtil.isNotNull(user.getNickname())) {
				hql += " and bean.nickname='" + user.getNickname() + "'";
			}
			if (StringUtil.isNotNull(user.getRecStatus())) {
				hql += " and bean.recStatus='" + user.getRecStatus() + "'";
			}
		}
		return getPager(hql, null, pageIndex, pageSize);
	}

	@Override
	public void updateMUser(MUser muser) throws DaoException {
		update(muser);
	}

	@Override
	public void addMUser(MUser user) throws DaoException {
		add(user);
	}
}
