package com.syx.taobao.dao.common;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.syx.taobao.exception.DaoException;
import com.syx.taobao.model.common.Pager;

public class PagerDao<T> {

	private Logger logger = LoggerFactory.getLogger(PagerDao.class);
	public SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * 根据条件查询PogeModel 返回model page
	 * 
	 * @param hql
	 *            查询语句
	 * @param params
	 *            条件集合
	 * @param pageIndex
	 *            页号
	 * @param pageSize
	 *            显示的记录数
	 * @return
	 * @throws DaoException
	 */
	protected Pager<T> getPager(String hql, Object[] params, int pageIndex, int pageSize) throws DaoException {
		try {
			int index = hql.indexOf("from");
			String countHql = "";
			if (index != -1) {
				countHql = "select count(*) " + hql.substring(index);
			} else {
				throw new DaoException("查询语句出现错误！ ");
			}
			int total = this.getSaleChanceListCount(countHql, params);
			Pager<T> pager = new Pager<T>(total, pageIndex, pageSize);
			int offset = (pageIndex - 1) * pageSize;

			pager.setList(this.getListForPage(hql, params, offset, pageSize));
			return pager;
		} catch (Exception e) {
			logger.error("get Pager failure! hql = [ " + hql + " ] ", e);
			throw new DaoException("get Pager failure! hql = [ " + hql + " ] ");

		}
	}

	// 获得集合的总数（聚合查询）
	protected Integer getSaleChanceListCount(final String hql, final Object[] params) {
		Integer result = null;
		Query query = getSession().createQuery(hql);
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		result = 0;
		Object obj = query.uniqueResult();
		if (obj != null) {
			result = ((Long) obj).intValue();
		}
		return result;
	}

	/**
	 * 使用hql 语句进行操作
	 * 
	 * @param hql
	 * @param offset
	 * @param length
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	protected List<T> getListForPage(final String hql, final Object[] params, final int offset, final int length) {
		List<T> list = null;
		Query query = getSession().createQuery(hql);
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		query.setFirstResult(offset);
		query.setMaxResults(length);
		list = query.list();
		return list;
	}

	/**
	 * 根据条件查询PogeModel 返回vo page
	 * 
	 * @param hql
	 *            查询语句
	 * @param params
	 *            条件集合
	 * @param pageIndex
	 *            页号
	 * @param pageSize
	 *            显示的记录数
	 * @param clazz
	 * @return
	 * @throws DaoException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected Pager getPager(String hql, Object[] params, int pageIndex, int pageSize, Class clazz) throws DaoException {
		try {
			int index = hql.indexOf("from");
			String countHql = "";
			if (index != -1) {
				countHql = "select count(*) " + hql.substring(index);
			} else {
				throw new DaoException("查询语句出现错误！ ");
			}
			int total = this.getSaleChanceListCount(countHql, params);
			Pager pager = new Pager(total, pageIndex, pageSize);
			int offset = (pageIndex - 1) * pageSize;

			pager.setList(this.getListToClazzPage(hql, params, offset, pageSize, clazz));
			return pager;
		} catch (Exception e) {
			logger.error("get Pager failure! hql = [ " + hql + " ] ", e);
			logger.error(e.getMessage());
			throw new DaoException("get Pager failure! hql = [ " + hql + " ] ");
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected Pager getSQLPager(String hql, Object[] params, int pageIndex, int pageSize, Class clazz) throws DaoException {
		try {
			int index = hql.indexOf("from");
			String countHql = "";
			if (index != -1) {
				countHql = "select count(*) " + hql.substring(index);
			} else {
				throw new DaoException("查询语句出现错误！ ");
			}
			int total = this.getSQLSaleChanceListCount(countHql, params);
			Pager pager = new Pager(total, pageIndex, pageSize);
			int offset = (pageIndex - 1) * pageSize;

			pager.setList(this.getSQLListToClazzPage(hql, params, offset, pageSize, clazz));
			return pager;
		} catch (Exception e) {
			logger.error("get SQL Pager failure! hql = [ " + hql + " ] ", e);
			throw new DaoException("get SQL Pager failure! hql = [ " + hql + " ] ");

		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected List getListToClazzPage(final String hql, final Object[] params, final int offset, final int length, Class clazz) {
		List<T> list = null;
		Query query = getSession().createQuery(hql);
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		if (clazz != null) {
			query.setResultTransformer(Transformers.aliasToBean(clazz));
		}
		query.setFirstResult(offset);
		query.setMaxResults(length);
		list = query.list();
		return list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List getSQLListToClazzPage(final String hql, final Object[] params, final int offset, final int length, Class clazz) {
		List<T> list = null;
		Query query = getSession().createSQLQuery(hql);
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		if (clazz != null) {
			query.setResultTransformer(Transformers.aliasToBean(clazz));
		}
		query.setFirstResult(offset);
		query.setMaxResults(length);
		list = query.list();
		return list;
	}

	protected Integer getSQLSaleChanceListCount(final String hql, final Object[] params) {
		Query query = getSession().createSQLQuery(hql);
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		BigInteger bi = (BigInteger) query.uniqueResult();
		if (bi == null) {
			return 0;
		}
		String biStr = bi.toString();
		return Integer.parseInt(biStr);
	}

}
