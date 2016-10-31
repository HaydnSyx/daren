package com.syx.taobao.dao;

import com.syx.taobao.exception.DaoException;
import com.syx.taobao.model.WorkTask;
import com.syx.taobao.model.common.Pager;
import com.syx.taobao.vo.WorkTaskVo;

public interface WorkTaskDao {

	public void addWorkTask(WorkTask o) throws DaoException;

	public void deleteWorkTask(int id) throws DaoException;

	public void updateWorkTask(WorkTask o) throws DaoException;

	public WorkTask queryWorkTask(int id) throws DaoException;

	public Pager<WorkTaskVo> queryWorkTaskPagerByCustom(WorkTaskVo vo, String orderby, String sort, int pageIndex, int pageSize) throws DaoException;
}
