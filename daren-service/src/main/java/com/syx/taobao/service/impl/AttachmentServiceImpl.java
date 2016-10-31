package com.syx.taobao.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.syx.taobao.dao.AttachmentDao;
import com.syx.taobao.exception.BizException;
import com.syx.taobao.exception.DaoException;
import com.syx.taobao.model.Attachment;
import com.syx.taobao.service.AttachmentService;

@Service
@Transactional(rollbackFor = BizException.class, propagation = Propagation.REQUIRED)
public class AttachmentServiceImpl implements AttachmentService {

	private Logger logger = LoggerFactory.getLogger(AttachmentServiceImpl.class);

	@Autowired
	private AttachmentDao attachmentDao;

	@Override
	public List<Attachment> queryAttachmentByTaskId(int taskId) throws BizException {
		try {
			return attachmentDao.queryAttachmentByTaskId(taskId);
		} catch (DaoException e) {
			logger.error("query Attachment List failure! taskId=[" + taskId + "]", e);
			throw new BizException("query Attachment List failure! taskId=[" + taskId + "]");
		}
	}

	@Override
	public Attachment queryAttachment(int id) throws BizException {
		try {
			return attachmentDao.queryAttachment(id);
		} catch (DaoException e) {
			logger.error("query Attachment failure! id=[" + id + "]", e);
			throw new BizException("query Attachment failure! id=[" + id + "]");
		}
	}

}
