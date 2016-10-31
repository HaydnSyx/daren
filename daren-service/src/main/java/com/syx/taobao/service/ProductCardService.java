package com.syx.taobao.service;

import com.syx.taobao.exception.BizException;
import com.syx.taobao.model.ProductCard;

public interface ProductCardService {

	public void addProductCard(ProductCard o) throws BizException;

	public void deleteProductCard(int id) throws BizException;

	public ProductCard queryProductCard(int id) throws BizException;

	public void updateProductCard(ProductCard o) throws BizException;

	public ProductCard queryProductCardByTaskId(int taskId) throws BizException;
}
