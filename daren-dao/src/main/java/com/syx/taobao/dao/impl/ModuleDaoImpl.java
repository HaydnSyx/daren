package com.syx.taobao.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.syx.taobao.dao.ModuleDao;
import com.syx.taobao.dao.common.SimpleDao;
import com.syx.taobao.exception.DaoException;
import com.syx.taobao.model.authority.Module;
@Repository
public class ModuleDaoImpl extends SimpleDao<Module, Integer> implements ModuleDao {
	
	private Logger logger = LoggerFactory.getLogger(ModuleDaoImpl.class);

	@Override
	public void addModule(Module qt) throws DaoException {
		add(qt);
	}

	@Override
	public void deleteModule(int id) throws DaoException {
		Module qt = get(id);
		if(qt != null) {
			delete(qt);
		}
	}

	@Override
	public void updateModule(Module qt) throws DaoException {
		update(qt);
	}

	@Override
	public Module queryModule(int id) throws DaoException {
		return get(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Module> queryAllModule(Integer pid) throws DaoException {
		try {
			String hql = "from Module bean where 1=1 ";
			if(pid != null) {
				if(pid <=0) {
					hql += " and bean.pId is null ";
				}else {
					hql += " and bean.pId = " + pid;
				}
			}
			return getSession().createQuery(hql).list();
		} catch (HibernateException e) {
			logger.error("query All Module failure! pid = [" + pid + "] ", e);
			throw new DaoException("query All Module failure! pid = [" + pid + "] ");
		}
	}

}
