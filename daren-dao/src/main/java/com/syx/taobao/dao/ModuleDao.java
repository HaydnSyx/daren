package com.syx.taobao.dao;

import java.util.List;

import com.syx.taobao.exception.DaoException;
import com.syx.taobao.model.authority.Module;

public interface ModuleDao {

	public void addModule(Module m) throws DaoException;
	
	public void deleteModule(int id) throws DaoException;
	
	public void updateModule(Module m) throws DaoException;
	
	public Module queryModule(int id) throws DaoException;
	
	public List<Module> queryAllModule(Integer pid) throws DaoException;	
	
}
