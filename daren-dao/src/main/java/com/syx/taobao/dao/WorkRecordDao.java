package com.syx.taobao.dao;

import java.util.Date;

import com.syx.taobao.exception.DaoException;
import com.syx.taobao.model.WorkRecord;
import com.syx.taobao.model.common.Pager;
import com.syx.taobao.vo.WorkRecordVo;

public interface WorkRecordDao {

	public void addWorkRecord(WorkRecord o) throws DaoException;

	public void deleteWorkRecord(int id) throws DaoException;

	public void updateWorkRecord(WorkRecord o) throws DaoException;

	public WorkRecord queryWorkRecord(int id) throws DaoException;

	public WorkRecord queryWorkRecordByMIdAndSDate(int mId, Date sdate) throws DaoException;

	public Pager<WorkRecordVo> queryWorkRecordPagerByCustom(WorkRecordVo vo, String orderby, String sort, int pageIndex,
			int pageSize) throws DaoException;
}
