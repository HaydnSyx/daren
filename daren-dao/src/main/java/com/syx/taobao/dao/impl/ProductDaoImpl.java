package com.syx.taobao.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.syx.taobao.dao.ProductDao;
import com.syx.taobao.dao.common.SimpleDao;
import com.syx.taobao.exception.DaoException;
import com.syx.taobao.model.Product;

@Repository
public class ProductDaoImpl extends SimpleDao<Product, Integer> implements ProductDao {

	@Override
	public void addProduct(Product m) throws DaoException {
		add(m);
	}

	@Override
	public void deleteProduct(int id) throws DaoException {
		Product p = get(id);
		if (p != null) {
			delete(p);
		}
	}

	@Override
	public void updateProduct(Product m) throws DaoException {
		update(m);
	}

	@Override
	public Product queryProduct(int id) throws DaoException {
		return get(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> queryAllProduct() throws DaoException {
		final String hql = "from Product bean order by bean.productType ";
		return getSession().createQuery(hql).list();
	}

	@Override
	public Product queryProductByProductType(int productType) throws DaoException {
		return findUniqueByProperty("productType", productType);
	}

}
