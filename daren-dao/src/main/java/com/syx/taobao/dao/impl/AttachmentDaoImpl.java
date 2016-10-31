package com.syx.taobao.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.syx.taobao.dao.AttachmentDao;
import com.syx.taobao.dao.common.SimpleDao;
import com.syx.taobao.exception.DaoException;
import com.syx.taobao.model.Attachment;

@Repository
public class AttachmentDaoImpl extends SimpleDao<Attachment, Integer> implements AttachmentDao {

	@Override
	public void addAttachment(Attachment o) throws DaoException {
		add(o);
	}

	@Override
	public void deleteAttachment(int id) throws DaoException {
		Attachment o = get(id);
		if (o != null) {
			delete(o);
		}
	}

	@Override
	public void updateAttachment(Attachment o) throws DaoException {
		update(o);
	}

	@Override
	public Attachment queryAttachment(int id) throws DaoException {
		return get(id);
	}

	@Override
	public List<Attachment> queryAttachmentByTaskId(int taskId) throws DaoException {
		return findByProperty("taskId", taskId);
	}
}
