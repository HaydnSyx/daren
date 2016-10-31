package com.syx.taobao.service;

import java.util.List;

import com.syx.taobao.exception.BizException;
import com.syx.taobao.model.Audit;
import com.syx.taobao.vo.AuditVo;

public interface AuditService {

	public List<AuditVo> queryAuditBytaskId(int taskId) throws BizException;

	public void addAudit(Audit o) throws BizException;
}
