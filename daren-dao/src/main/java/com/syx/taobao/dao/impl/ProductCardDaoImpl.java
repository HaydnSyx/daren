package com.syx.taobao.dao.impl;

import org.springframework.stereotype.Repository;

import com.syx.taobao.dao.ProductCardDao;
import com.syx.taobao.dao.common.SimpleDao;
import com.syx.taobao.exception.DaoException;
import com.syx.taobao.model.ProductCard;

@Repository
public class ProductCardDaoImpl extends SimpleDao<ProductCard, Integer> implements ProductCardDao {

	@Override
	public void addProductCard(ProductCard o) throws DaoException {
		add(o);
	}

	@Override
	public void deleteProductCard(int id) throws DaoException {
		ProductCard o = get(id);
		if (o != null) {
			delete(o);
		}
	}

	@Override
	public void updateProductCard(ProductCard o) throws DaoException {
		update(o);
	}

	@Override
	public ProductCard queryProductCard(int id) throws DaoException {
		return get(id);
	}

	@Override
	public ProductCard queryProductCardByTaskId(int taskId) throws DaoException {
		return findUniqueByProperty("taskId", taskId);
	}

}
