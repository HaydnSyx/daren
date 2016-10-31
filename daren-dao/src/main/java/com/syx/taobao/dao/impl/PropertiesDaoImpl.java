package com.syx.taobao.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.syx.taobao.dao.PropertiesDao;
import com.syx.taobao.dao.common.SimpleDao;
import com.syx.taobao.exception.DaoException;
import com.syx.taobao.model.Properties;

@Repository
public class PropertiesDaoImpl extends SimpleDao<Properties, Integer> implements PropertiesDao {

	private Logger logger = LoggerFactory.getLogger(PropertiesDaoImpl.class);

	@Override
	public void addProperties(Properties prop) throws DaoException {
		add(prop);
	}

	@Override
	public void updateProperties(Properties prop) throws DaoException {
		update(prop);
	}

	@Override
	public List<Properties> queryAllProperties() throws DaoException {
		String hql = "from Properties bean ";
		return queryAll(hql);
	}

	@Override
	public Properties queryProperties(String key) throws DaoException {
		try {
			List<Properties> list = findByProperty("key", key);
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (Exception e) {
			logger.error("query Properties failure! key = [" + key + "] ", e);
			throw new DaoException("query Properties failure! key = [" + key + "] ");
		}
	}

	@Override
	public void deleteProperties(int id) throws DaoException {
		Properties o = get(id);
		if (o != null) {
			delete(o);
		}
	}

}
