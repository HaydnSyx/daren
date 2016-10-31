package com.syx.taobao.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.syx.taobao.dao.ProductDao;
import com.syx.taobao.exception.BizException;
import com.syx.taobao.exception.DaoException;
import com.syx.taobao.model.Product;
import com.syx.taobao.service.ProductService;

@Service
@Transactional(rollbackFor = BizException.class, propagation = Propagation.REQUIRED)
public class ProductServiceImpl implements ProductService {

	private Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
	@Autowired
	private ProductDao productDao;

	@Override
	public void addProduct(Product qt) throws BizException {
		try {
			productDao.addProduct(qt);
		} catch (DaoException e) {
			logger.error("add Product failure! Product=[" + qt + "]", e);
			throw new BizException("add Product failure! Product=[" + qt + "]");
		}
	}

	@Override
	public void deleteProduct(int id) throws BizException {
		try {
			productDao.deleteProduct(id);
		} catch (DaoException e) {
			logger.error("delete Product failure! id=[" + id + "]", e);
			throw new BizException("delete Product failure! id=[" + id + "]");
		}
	}

	@Override
	public Product queryProduct(int id) throws BizException {
		try {
			return productDao.queryProduct(id);
		} catch (DaoException e) {
			logger.error("query Product failure! id=[" + id + "]", e);
			throw new BizException("query Product id! Module=[" + id + "]");
		}
	}

	@Override
	public void updateProduct(Product pt) throws BizException {
		try {
			productDao.updateProduct(pt);
		} catch (DaoException e) {
			logger.error("update Product failure! Product=[" + pt + "]", e);
			throw new BizException("update Product failure! Product=[" + pt + "]");
		}
	}

	@Transactional(readOnly = true)
	@Override
	public List<Product> queryAllProduct() throws BizException {
		try {
			return productDao.queryAllProduct();
		} catch (DaoException e) {
			logger.error("query Product List failure!", e);
			throw new BizException("query Product List failure!");
		}
	}
}
