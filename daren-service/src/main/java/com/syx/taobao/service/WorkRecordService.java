package com.syx.taobao.service;

import com.syx.taobao.exception.BizException;
import com.syx.taobao.model.WorkRecord;
import com.syx.taobao.model.common.Pager;
import com.syx.taobao.vo.WorkRecordVo;

public interface WorkRecordService {

	public void addWorkRecord(WorkRecord o) throws BizException;

	public void deleteWorkRecord(int id) throws BizException;

	public WorkRecord queryWorkRecord(int id) throws BizException;

	public void updateWorkRecord(WorkRecord o) throws BizException;

	public Pager<WorkRecordVo> queryWorkRecordPagerByCustom(WorkRecordVo vo, String orderby, String sort, int pageIndex, int pageSize) throws BizException;
}
