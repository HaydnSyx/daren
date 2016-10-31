package com.syx.taobao.dao;

import java.util.List;

import com.syx.taobao.exception.DaoException;
import com.syx.taobao.model.Product;

public interface ProductDao {

	public void addProduct(Product m) throws DaoException;

	public void deleteProduct(int id) throws DaoException;

	public void updateProduct(Product m) throws DaoException;

	public Product queryProduct(int id) throws DaoException;

	public Product queryProductByProductType(int productType) throws DaoException;

	public List<Product> queryAllProduct() throws DaoException;
}
