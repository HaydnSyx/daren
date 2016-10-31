package com.syx.taobao.dao.common;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.syx.taobao.exception.DaoException;

public class SimpleDao<T, ID extends Serializable> extends PagerDao<T>{


	private Logger logger = LoggerFactory.getLogger(SimpleDao.class);
	/*public SessionFactory sessionFactory;
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}*/
	/**
	 * @see Session.get(Class,Serializable)
	 * @param id
	 * @return 持久化对象
	 */
	@SuppressWarnings("unchecked")
	protected T get(ID id)  throws DaoException {
		try {
			return (T)getSession().get(getEntityClass(), id);
		} catch (Exception e) {
			logger.error("get " + id + " failure!", e);
			throw new DaoException("get " + id + " failure!");
		}
	}

	/**
	 * 按属性查找对象列表
	 * @throws DaoException 
	 */
	@SuppressWarnings("unchecked")
	protected List<T> findByProperty(String property, Object value) throws DaoException {
		try {
			Assert.hasText(property);
			return createCriteria(Restrictions.eq(property, value)).list();
		} catch (HibernateException e) {
			logger.error("find By Property failure! property = [" + property + "] value = [" + value + "]", e);
			throw new DaoException("find By Property failure! property = [" + property + "] value = [" + value + "]");
		}
	}

	/**
	 * 按属性查找唯一对象
	 * @throws DaoException 
	 */
	@SuppressWarnings("unchecked")
	protected T findUniqueByProperty(String property, Object value) throws DaoException {
		try {
			Assert.hasText(property);
			Assert.notNull(value);
			return (T) createCriteria(Restrictions.eq(property, value))
					.uniqueResult();
		} catch (HibernateException e) {
			logger.error("find Unique By Property failure! property = [" + property + "] value = [" + value + "]", e);
			throw new DaoException("find Unique By Property failure! property = [" + property + "] value = [" + value + "]");
		}
	}

	/**
	 * 按属性统计记录数
	 * 
	 * @param property
	 * @param value
	 * @return
	 * @throws DaoException 
	 */
	protected int countByProperty(String property, Object value) throws DaoException {
		try {
			Assert.hasText(property);
			Assert.notNull(value);
			return ((Number) (createCriteria(Restrictions.eq(property, value))
					.setProjection(Projections.rowCount()).uniqueResult()))
					.intValue();
		} catch (HibernateException e) {
			logger.error("count By Property failure! property = [" + property + "] value = [" + value + "]", e);
			throw new DaoException("count By Property failure! property = [" + property + "] value = [" + value + "]");
		}
	}

	/**
	 * 按Criterion查询列表数据.
	 * 
	 * @param criterion
	 *            数量可变的Criterion.
	 * @throws DaoException 
	 */
	@SuppressWarnings("unchecked")
	protected List<T> findByCriteria(Criterion... criterion) throws DaoException {
		try {
			return createCriteria(criterion).list();
		} catch (HibernateException e) {
			logger.error("find By Criteria failure! ", e);
			throw new DaoException("find By Criteria failure! ");
		}
	}

	/**
	 * 根据Criterion条件创建Criteria,后续可进行更多处理,辅助函数.
	 */
	protected Criteria createCriteria(Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(getEntityClass());
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

	protected Class<T> getEntityClass() {
		return entryClass;
	}

	protected void update(T object) throws DaoException {
		try {
			getSession().update(object);
		} catch (Exception e) {
			logger.error("update " + object + " failure!", e);
			throw new DaoException("update " + object + " failure!");
		}
	}

	protected void add(T object) throws DaoException {
		try {
			getSession().save(object);
		} catch (Exception e) {
			logger.error("save " + object + " failure!", e);
			throw new DaoException("save " + object + " failure!");
		}
	}

	@SuppressWarnings("unchecked")
	protected List<T> queryAll(String hql) throws DaoException {
		try {
			Query query = getSession().createQuery(hql);
			return query.list();
		} catch (Exception e) {
			logger.error("query [" + hql + "] failure!", e);
			throw new DaoException("query [" + hql + "] failure!");
		}
	}

	protected void delete(T object) throws DaoException {
		try {
			getSession().delete(object);
		} catch (HibernateException e) {
			logger.error("delete " + object + " failure!", e);
			throw new DaoException("delete " + object + " failure!");
		}
	}

	
	

	/**
	 * 通过HQL查询对象列表
	 * 
	 * @param hql
	 *            hql语句
	 * @param values
	 *            数量可变的参数
	 * @throws DaoException 
	 */
	@SuppressWarnings("rawtypes")
	protected List find(String hql, List<? extends Object> values) throws DaoException {
		try {
			return createQuery(hql, values).list();
		} catch (HibernateException e) {
			logger.error("find failure! hql = [" + hql + "] values = [" + values + "] ", e);
			throw new DaoException("find failure! hql = [" + hql + "] values = [" + values + "] ");
		}
	}

	/**
	 * 通过HQL查询唯一对象
	 * @throws DaoException 
	 */
	protected Object findUnique(String hql, List<? extends Object> values) throws DaoException {
		try {
			return createQuery(hql, values).uniqueResult();
		} catch (HibernateException e) {
			logger.error("find Unique failure! hql = [" + hql + "] values = [" + values + "] ", e);
			throw new DaoException("find Unique failure! hql = [" + hql + "] values = [" + values + "] ");
		}
	}

	/**
	 * 根据查询函数与参数列表创建Query对象,后续可进行更多处理,辅助函数.
	 */
	protected Query createQuery(String queryString, List<? extends Object> values) {
		Assert.hasText(queryString);
		Query queryObject = getSession().createQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.size(); i++) {
				queryObject.setParameter(i, values.get(i));
			}
		}
		return queryObject;
	}
	
	
	@SuppressWarnings("unchecked")
	protected Class<T> entryClass = GenericType.getSuperClassGenricType(this
			.getClass());

	
}
