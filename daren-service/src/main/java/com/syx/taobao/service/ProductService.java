package com.syx.taobao.service;

import java.util.List;

import com.syx.taobao.exception.BizException;
import com.syx.taobao.model.Product;

public interface ProductService {

	public void addProduct(Product qt) throws BizException;
	
	public void deleteProduct(int id) throws BizException;
	
	public Product queryProduct(int id) throws BizException;
	
	public void updateProduct(Product pt) throws BizException;

	public List<Product> queryAllProduct() throws BizException;
}
