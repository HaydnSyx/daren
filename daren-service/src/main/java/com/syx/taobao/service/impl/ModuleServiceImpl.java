package com.syx.taobao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.syx.taobao.dao.ModuleDao;
import com.syx.taobao.exception.BizException;
import com.syx.taobao.exception.DaoException;
import com.syx.taobao.model.authority.Module;
import com.syx.taobao.service.ModuleService;
import com.syx.taobao.vo.ModuleVo;
@Service
@Transactional(rollbackFor = BizException.class, propagation = Propagation.REQUIRED)
public class ModuleServiceImpl implements ModuleService {

	private Logger logger = LoggerFactory.getLogger(ModuleServiceImpl.class);
	@Autowired
	private ModuleDao moduleDao;
	
	@Override
	public void addModule(Module qt) throws BizException {
		try {
			qt.setCreateTime(new Date());
			moduleDao.addModule(qt);
		} catch (DaoException e) {
			logger.error("add Module failure! Module=[" + qt + "]", e);
			throw new BizException("add Module failure! Module=[" + qt + "]");
		}
	}

	@Override
	public void deleteModule(int id) throws BizException {
		try {
			moduleDao.deleteModule(id);
		} catch (DaoException e) {
			logger.error("delete Module failure! id=[" + id + "]", e);
			throw new BizException("delete Module failure! id=[" + id + "]");
		}
	}

	@Override
	@Transactional(readOnly=true)
	public Module queryModule(int id) throws BizException {
		try {
			return moduleDao.queryModule(id);
		} catch (DaoException e) {
			logger.error("query Module failure! id=[" + id + "]", e);
			throw new BizException("query Module failure! id=[" + id + "]");
		}
	}


	@Override
	public void updateModule(Module pt) throws BizException {
		int id = pt.getId();
		try {
			Module qt = moduleDao.queryModule(id);
			if(qt != null) {
				qt.setName(pt.getName());
				qt.setUrl(pt.getUrl());
				moduleDao.updateModule(qt);
			}
		} catch (DaoException e) {
			logger.error("update Module failure! pt=[" + pt + "]", e);
			throw new BizException("update Module failure! pt=[" + pt + "]");
		}
	}

	@Override
	public void deleteModule(int[] ids) throws BizException {
		if(ids != null) {
			for (int i = 0; i < ids.length; i++) {
				deleteModule(ids[i]);
			}
		}
	}

	@Override
	@Transactional(readOnly=true)
	public List<Module> queryAllModule(Integer parentId)
			throws BizException {
		try {
			return moduleDao.queryAllModule(parentId);
		} catch (DaoException e) {
			logger.error("query All Module failure! parentId=[" + parentId + "]", e);
			throw new BizException("query All Module failure! parentId=[" + parentId + "]");
		}
	}

	@Override
	public List<ModuleVo> queryAllModuleVo() throws BizException {
		List<Module> list = queryAllModule(-1);
		List<ModuleVo> vos = null;
		if(list != null && list.size() > 0) {
			vos = new ArrayList<ModuleVo>();
			for (int i = 0; i < list.size(); i++) {
				Module m = list.get(i);
				getModuleVo(vos, m);
			}
		}
		
		return vos;
	}

	private void getModuleVo(List<ModuleVo> vos, Module m) throws BizException {
		if(m != null) {
			int pId = m.getId();
			List<ModuleVo> cs = null;
			List<Module> childs = queryAllModule(pId);
			if(childs != null && childs.size() > 0) {
				cs = new ArrayList<ModuleVo>();
				for (int j = 0; j < childs.size(); j++) {
					Module c = childs.get(j);
					getModuleVo(cs, c);
				}
			}
			
			ModuleVo vo = new ModuleVo(m,cs);
			vos.add(vo);
		}
	}




}
