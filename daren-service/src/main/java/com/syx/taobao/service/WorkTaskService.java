package com.syx.taobao.service;

import java.util.Map;

import com.syx.taobao.exception.BizException;
import com.syx.taobao.model.ProductCard;
import com.syx.taobao.model.WorkTask;
import com.syx.taobao.model.common.Pager;
import com.syx.taobao.vo.ResultVo;
import com.syx.taobao.vo.WorkTaskVo;

public interface WorkTaskService {

	public Map<String, Integer> addWorkTask(WorkTask o) throws BizException;

	public void deleteWorkTask(int id) throws BizException;

	public WorkTask queryWorkTask(int id) throws BizException;

	public ResultVo updateWorkTaskAndProduct(WorkTask o, ProductCard pc, String[] url, String[] serverName,
			String[] originName, Long[] fileSize, Integer[] attachmentId) throws BizException;

	public void updateWorkTask(WorkTask o) throws BizException;

	public Pager<WorkTaskVo> queryWorkTaskPagerByCustom(WorkTaskVo vo, String orderby, String sort, int pageIndex,
			int pageSize) throws BizException;

	public ResultVo auditWorkTask(int mId, int taskId, int type, int staus, String remark) throws BizException;
}
