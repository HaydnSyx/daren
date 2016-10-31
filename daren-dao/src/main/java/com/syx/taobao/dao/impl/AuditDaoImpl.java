package com.syx.taobao.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.syx.taobao.dao.AuditDao;
import com.syx.taobao.dao.common.SimpleDao;
import com.syx.taobao.exception.DaoException;
import com.syx.taobao.model.Audit;
import com.syx.taobao.vo.AuditVo;

@Repository
public class AuditDaoImpl extends SimpleDao<Audit, Integer> implements AuditDao {

	@Override
	public void addAudit(Audit o) throws DaoException {
		add(o);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AuditVo> queryAuditBytaskId(int taskId) throws DaoException {
		StringBuffer hql = new StringBuffer();
		hql.append("select bean.id as id,bean.taskId as taskId,bean.mId as mId, ");
		hql.append(" bean.status as status,bean.remark as remark, ");
		hql.append(" bean.createTime as createTime,muser.username as username, ");
		hql.append(" muser.nickname as nickname ");
		hql.append(" from Audit bean, MUser muser where bean.mId = muser.id and bean.taskId = :taskId ");
		hql.append(" order by bean.createTime desc ");
		Query query = getSession().createQuery(hql.toString()).setParameter("taskId", taskId);
		query.setResultTransformer(Transformers.aliasToBean(AuditVo.class));
		return query.list();
	}
}
