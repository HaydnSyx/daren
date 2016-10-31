package com.syx.taobao.dao;

import java.util.List;

import com.syx.taobao.exception.DaoException;
import com.syx.taobao.model.ImFriendGroup;

public interface ImFriendGroupDao {

	public void addImFriendGroup(ImFriendGroup o) throws DaoException;

	public void deleteImFriendGroup(int id) throws DaoException;

	public void updateImFriendGroup(ImFriendGroup o) throws DaoException;

	public ImFriendGroup queryImFriendGroup(int id) throws DaoException;

	public List<ImFriendGroup> queryImFriendGroupByMId(int mId) throws DaoException;
}
