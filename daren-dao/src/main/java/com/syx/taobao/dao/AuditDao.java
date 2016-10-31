package com.syx.taobao.dao;

import java.util.List;

import com.syx.taobao.exception.DaoException;
import com.syx.taobao.model.Audit;
import com.syx.taobao.vo.AuditVo;

public interface AuditDao {

	public void addAudit(Audit o) throws DaoException;

	public List<AuditVo> queryAuditBytaskId(int taskId) throws DaoException;
}
