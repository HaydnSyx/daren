package com.syx.taobao.dao;

import java.util.List;

import com.syx.taobao.exception.DaoException;
import com.syx.taobao.model.Attachment;

public interface AttachmentDao {

	public void addAttachment(Attachment o) throws DaoException;

	public void deleteAttachment(int id) throws DaoException;

	public void updateAttachment(Attachment o) throws DaoException;

	public Attachment queryAttachment(int id) throws DaoException;

	public List<Attachment> queryAttachmentByTaskId(int taskId) throws DaoException;
}
