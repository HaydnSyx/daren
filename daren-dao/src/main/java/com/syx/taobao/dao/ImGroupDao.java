package com.syx.taobao.dao;

import java.util.List;

import com.syx.taobao.exception.DaoException;
import com.syx.taobao.model.ImGroup;

public interface ImGroupDao {

	public void addImGroup(ImGroup o) throws DaoException;

	public void deleteImGroup(int id) throws DaoException;

	public void updateImGroup(ImGroup o) throws DaoException;

	public ImGroup queryImGroup(int id) throws DaoException;

	public List<ImGroup> queryImGroupByMId(int mId) throws DaoException;
}
