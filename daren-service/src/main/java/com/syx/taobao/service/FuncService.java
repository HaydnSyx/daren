package com.syx.taobao.service;

import com.syx.taobao.exception.BizException;
import com.syx.taobao.model.authority.Func;

public interface FuncService {

	public void addFunc(Func qt) throws BizException;
	
	public void deleteFunc(int id) throws BizException;
	
	public Func queryFunc(int id) throws BizException;
	
	public void updateFunc(Func f) throws BizException;

	public void deleteFunc(int[] ids) throws BizException;
	
}
