package com.syx.taobao.service;

import java.util.List;

import com.syx.taobao.exception.BizException;
import com.syx.taobao.model.Properties;

public interface PropertiesService {

	public void addProperties(String key, String value) throws BizException;

	public void updateProperties(String key, String value) throws BizException;

	public List<Properties> queryAllProperties() throws BizException;

	public Properties queryProperties(String key) throws BizException;

	public String queryPropValue(String key) throws BizException;

	public void deleteProperties(int id) throws BizException;
}
