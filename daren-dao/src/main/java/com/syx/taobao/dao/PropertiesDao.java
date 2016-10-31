package com.syx.taobao.dao;

import java.util.List;

import com.syx.taobao.exception.DaoException;
import com.syx.taobao.model.Properties;

public interface PropertiesDao {

	public void addProperties(Properties prop) throws DaoException;

	public void updateProperties(Properties prop) throws DaoException;

	public List<Properties> queryAllProperties() throws DaoException;

	public Properties queryProperties(String key) throws DaoException;

	public void deleteProperties(int id) throws DaoException;
}
