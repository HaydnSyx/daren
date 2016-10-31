package com.syx.taobao.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.syx.taobao.dao.AuditDao;
import com.syx.taobao.exception.BizException;
import com.syx.taobao.exception.DaoException;
import com.syx.taobao.model.Audit;
import com.syx.taobao.service.AuditService;
import com.syx.taobao.vo.AuditVo;

@Service
@Transactional(rollbackFor = BizException.class, propagation = Propagation.REQUIRED)
public class AuditServiceImpl implements AuditService {

	private Logger logger = LoggerFactory.getLogger(AuditServiceImpl.class);

	@Autowired
	private AuditDao auditDao;

	@Transactional(readOnly = true)
	@Override
	public List<AuditVo> queryAuditBytaskId(int taskId) throws BizException {
		try {
			return auditDao.queryAuditBytaskId(taskId);
		} catch (DaoException e) {
			logger.error("query Audit List failure! taskId=[" + taskId + "]", e);
			throw new BizException("query Audit List failure! taskId=[" + taskId + "]");
		}
	}

	@Override
	public void addAudit(Audit o) throws BizException {
		try {
			auditDao.addAudit(o);
		} catch (DaoException e) {
			logger.error("add Audit failure! o=[" + o.toString() + "]", e);
			throw new BizException("add Audit failure! o=[" + o.toString() + "]");
		}
	}

}
