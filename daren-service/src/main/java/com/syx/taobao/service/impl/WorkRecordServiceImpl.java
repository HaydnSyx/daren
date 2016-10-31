package com.syx.taobao.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.syx.taobao.dao.WorkRecordDao;
import com.syx.taobao.exception.BizException;
import com.syx.taobao.exception.DaoException;
import com.syx.taobao.model.WorkRecord;
import com.syx.taobao.model.common.Pager;
import com.syx.taobao.service.WorkRecordService;
import com.syx.taobao.vo.WorkRecordVo;

@Service
@Transactional(rollbackFor = BizException.class, propagation = Propagation.REQUIRED)
public class WorkRecordServiceImpl implements WorkRecordService {

	private Logger logger = LoggerFactory.getLogger(WorkRecordServiceImpl.class);
	@Autowired
	private WorkRecordDao workRecordDao;

	@Override
	public void addWorkRecord(WorkRecord o) throws BizException {
		try {
			workRecordDao.addWorkRecord(o);
		} catch (DaoException e) {
			logger.error("add WorkRecord failure! WorkRecord=[" + o + "]", e);
			throw new BizException("add WorkRecord failure! WorkRecord=[" + o + "]");
		}
	}

	@Override
	public void deleteWorkRecord(int id) throws BizException {
		try {
			workRecordDao.deleteWorkRecord(id);
		} catch (DaoException e) {
			logger.error("delete WorkRecord failure! id=[" + id + "]", e);
			throw new BizException("delete WorkRecord failure! id=[" + id + "]");
		}
	}

	@Override
	public WorkRecord queryWorkRecord(int id) throws BizException {
		try {
			return workRecordDao.queryWorkRecord(id);
		} catch (DaoException e) {
			logger.error("query WorkRecord failure! id=[" + id + "]", e);
			throw new BizException("query WorkRecord failure! id=[" + id + "]");
		}
	}

	@Override
	public void updateWorkRecord(WorkRecord o) throws BizException {
		try {
			o.setUpdateTime(new Date());
			workRecordDao.updateWorkRecord(o);
		} catch (DaoException e) {
			logger.error("update WorkRecord failure! WorkRecord=[" + o + "]", e);
			throw new BizException("update WorkRecord failure! WorkRecord=[" + o + "]");
		}
	}

	@Transactional(readOnly = true)
	@Override
	public Pager<WorkRecordVo> queryWorkRecordPagerByCustom(WorkRecordVo vo, String orderby, String sort, int pageIndex, int pageSize) throws BizException {
		try {
			return workRecordDao.queryWorkRecordPagerByCustom(vo, orderby, sort, pageIndex, pageSize);
		} catch (DaoException e) {
			logger.error("query WorkRecord Pager failure!", e);
			throw new BizException("query WorkRecord Pager failure!");
		}
	}
}
