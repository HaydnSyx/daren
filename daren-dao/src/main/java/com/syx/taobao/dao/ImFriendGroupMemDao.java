package com.syx.taobao.dao;

import com.syx.taobao.exception.DaoException;
import com.syx.taobao.model.ImFriendGroupMem;

public interface ImFriendGroupMemDao {

	public void addImFriendGroupMem(ImFriendGroupMem o) throws DaoException;

	public void deleteImFriendGroupMem(int id) throws DaoException;

	public void updateImFriendGroupMem(ImFriendGroupMem o) throws DaoException;

	public ImFriendGroupMem queryImFriendGroupMem(int id) throws DaoException;
}
