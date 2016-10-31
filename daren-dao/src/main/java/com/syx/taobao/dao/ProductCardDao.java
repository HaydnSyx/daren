package com.syx.taobao.dao;

import com.syx.taobao.exception.DaoException;
import com.syx.taobao.model.ProductCard;

public interface ProductCardDao {

	public void addProductCard(ProductCard o) throws DaoException;

	public void deleteProductCard(int id) throws DaoException;

	public void updateProductCard(ProductCard o) throws DaoException;

	public ProductCard queryProductCard(int id) throws DaoException;
	
	public ProductCard queryProductCardByTaskId(int taskId) throws DaoException;
}
