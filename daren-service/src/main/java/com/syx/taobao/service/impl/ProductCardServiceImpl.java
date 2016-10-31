package com.syx.taobao.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.syx.taobao.dao.ProductCardDao;
import com.syx.taobao.exception.BizException;
import com.syx.taobao.exception.DaoException;
import com.syx.taobao.model.ProductCard;
import com.syx.taobao.service.ProductCardService;

@Service
@Transactional(rollbackFor = BizException.class, propagation = Propagation.REQUIRED)
public class ProductCardServiceImpl implements ProductCardService {

	private Logger logger = LoggerFactory.getLogger(ProductCardServiceImpl.class);
	@Autowired
	private ProductCardDao productCardDao;

	@Override
	public void addProductCard(ProductCard o) throws BizException {
		try {
			productCardDao.addProductCard(o);
		} catch (DaoException e) {
			logger.error("add ProductCard failure! ProductCard=[" + o + "]", e);
			throw new BizException("add ProductCard failure! ProductCard=[" + o + "]");
		}
	}

	@Override
	public void deleteProductCard(int id) throws BizException {
		try {
			productCardDao.deleteProductCard(id);
		} catch (DaoException e) {
			logger.error("delete ProductCard failure! id=[" + id + "]", e);
			throw new BizException("delete ProductCard failure! id=[" + id + "]");
		}
	}

	@Override
	public ProductCard queryProductCard(int id) throws BizException {
		try {
			return productCardDao.queryProductCard(id);
		} catch (DaoException e) {
			logger.error("query ProductCard failure! id=[" + id + "]", e);
			throw new BizException("query ProductCard failure! id=[" + id + "]");
		}
	}

	@Override
	public void updateProductCard(ProductCard o) throws BizException {
		try {
			productCardDao.updateProductCard(o);
			;
		} catch (DaoException e) {
			logger.error("update ProductCard failure! ProductCard=[" + o + "]", e);
			throw new BizException("update ProductCard failure! ProductCard=[" + o + "]");
		}
	}

	@Override
	public ProductCard queryProductCardByTaskId(int taskId) throws BizException {
		try {
			return productCardDao.queryProductCardByTaskId(taskId);
		} catch (DaoException e) {
			logger.error("query ProductCard failure! taskId=[" + taskId + "]", e);
			throw new BizException("query ProductCard failure! taskId=[" + taskId + "]");
		}
	}

}
