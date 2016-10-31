package com.syx.taobao.service;

import java.util.List;

import com.syx.taobao.exception.BizException;
import com.syx.taobao.model.authority.Module;
import com.syx.taobao.vo.ModuleVo;

public interface ModuleService {

	public void addModule(Module qt) throws BizException;
	
	public void deleteModule(int id) throws BizException;
	
	public Module queryModule(int id) throws BizException;
	
	public void updateModule(Module pt) throws BizException;

	public void deleteModule(int[] ids) throws BizException;
	
	public List<Module> queryAllModule(Integer parentId) throws BizException;

	public List<ModuleVo> queryAllModuleVo() throws BizException;

}
