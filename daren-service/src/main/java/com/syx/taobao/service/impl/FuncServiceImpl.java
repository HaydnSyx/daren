package com.syx.taobao.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.syx.taobao.dao.FuncDao;
import com.syx.taobao.exception.BizException;
import com.syx.taobao.exception.DaoException;
import com.syx.taobao.model.authority.Func;
import com.syx.taobao.service.FuncService;

@Service
@Transactional(rollbackFor = BizException.class, propagation = Propagation.REQUIRED)
public class FuncServiceImpl implements FuncService {
	
	@Autowired
	private FuncDao funcDao;
	
	private Logger logger = LoggerFactory.getLogger(FuncServiceImpl.class);

	@Override
	public void addFunc(Func qt) throws BizException {
		try {
			qt.setCreateTime(new Date());
			funcDao.addFunc(qt);
		} catch (DaoException e) {
			logger.error("add Func failure! Func=[" + qt + "]", e);
			throw new BizException("add Func failure! Func=[" + qt + "]");
		}
	}

	@Override
	public void deleteFunc(int id) throws BizException {
		try {
			funcDao.deleteFunc(id);
		} catch (DaoException e) {
			logger.error("delete Func failure! id=[" + id + "]", e);
			throw new BizException("delete Func failure! id=[" + id + "]");
		}
	}

	@Override
	@Transactional(readOnly=true)
	public Func queryFunc(int id) throws BizException {
		try {
			return funcDao.queryFunc(id);
		} catch (DaoException e) {
			logger.error("query Func failure! id=[" + id + "]", e);
			throw new BizException("query Func failure! id=[" + id + "]");
		}
	}

	@Override
	public void updateFunc(Func f) throws BizException {
		try {
			Func func = funcDao.queryFunc(f.getId());
			if(func != null) {
				func.setName(f.getName());
				func.setUrl(f.getUrl());
				funcDao.updateFunc(func);
			}
		} catch (DaoException e) {
			logger.error("update Func failure! Func=[" + f + "]", e);
			throw new BizException("update Func failure! Func=[" + f + "]");
		}
	}

	@Override
	public void deleteFunc(int[] ids) throws BizException {
		if(ids != null && ids.length > 0) {
			for (int i = 0; i < ids.length; i++) {
				deleteFunc(ids[i]);
			}
		}

	}

}
