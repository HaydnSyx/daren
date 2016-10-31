package com.syx.taobao.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.syx.taobao.dao.PropertiesDao;
import com.syx.taobao.exception.BizException;
import com.syx.taobao.exception.DaoException;
import com.syx.taobao.model.Properties;
import com.syx.taobao.service.PropertiesService;

@Service
@Transactional(rollbackFor = BizException.class, propagation = Propagation.REQUIRED)
public class PropertiesServiceImpl implements PropertiesService {

	private Logger logger = LoggerFactory.getLogger(PropertiesServiceImpl.class);
	@Autowired
	private PropertiesDao propertiesDao;

	@Override
	public void addProperties(String key, String value) throws BizException {
		try {
			Properties p = propertiesDao.queryProperties(key);
			if (p == null) {
				p = new Properties(key, value);
				propertiesDao.addProperties(p);
			}
		} catch (DaoException e) {
			logger.error("add Properties failure! key=[" + key + "]", e);
			throw new BizException("add Properties failure! key=[" + key + "]");
		}
	}

	@Override
	public void updateProperties(String key, String value) throws BizException {
		try {
			Properties p = propertiesDao.queryProperties(key);
			if (p != null) {
				p.setValue(value);
				propertiesDao.updateProperties(p);
			}
		} catch (DaoException e) {
			logger.error("update Properties failure! key=[" + key + "]", e);
			throw new BizException("update Properties failure! key=[" + key + "]");
		}
	}

	@Transactional(readOnly = true)
	@Override
	public List<Properties> queryAllProperties() throws BizException {
		try {
			return propertiesDao.queryAllProperties();
		} catch (DaoException e) {
			logger.error("query all Properties failure!", e);
			throw new BizException("query all  Properties failure!");
		}
	}

	@Transactional(readOnly = true)
	@Override
	public Properties queryProperties(String key) throws BizException {
		try {
			return propertiesDao.queryProperties(key);
		} catch (DaoException e) {
			logger.error("query Properties failure! key=[" + key + "]", e);
			throw new BizException("query Properties failure! key=[" + key + "]");
		}
	}

	@Transactional(readOnly = true)
	@Override
	public String queryPropValue(String key) throws BizException {
		try {
			Properties p = propertiesDao.queryProperties(key);
			if (p != null) {
				return p.getValue();
			}
			return "";
		} catch (DaoException e) {
			logger.error("query Properties value failure! key=[" + key + "]", e);
			throw new BizException("query Properties value failure! key=[" + key + "]");
		}
	}

	@Override
	public void deleteProperties(int id) throws BizException {
		try {
			propertiesDao.deleteProperties(id);
		} catch (DaoException e) {
			logger.error("delete Properties failure! id=[" + id + "]", e);
			throw new BizException("delete Properties failure! id=[" + id + "]");
		}
	}

}
