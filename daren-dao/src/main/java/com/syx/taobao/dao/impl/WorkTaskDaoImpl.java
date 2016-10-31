package com.syx.taobao.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.syx.taobao.dao.WorkTaskDao;
import com.syx.taobao.dao.common.SimpleDao;
import com.syx.taobao.exception.DaoException;
import com.syx.taobao.model.WorkTask;
import com.syx.taobao.model.common.Pager;
import com.syx.taobao.util.StringUtil;
import com.syx.taobao.vo.WorkTaskVo;

@Repository
public class WorkTaskDaoImpl extends SimpleDao<WorkTask, Integer> implements WorkTaskDao {

	@Override
	public void addWorkTask(WorkTask o) throws DaoException {
		add(o);
	}

	@Override
	public void deleteWorkTask(int id) throws DaoException {
		WorkTask o = get(id);
		if (o != null) {
			delete(o);
		}
	}

	@Override
	public void updateWorkTask(WorkTask o) throws DaoException {
		update(o);
	}

	@Override
	public WorkTask queryWorkTask(int id) throws DaoException {
		return get(id);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Pager<WorkTaskVo> queryWorkTaskPagerByCustom(WorkTaskVo vo, String orderby, String sort, int pageIndex,
			int pageSize) throws DaoException {
		StringBuilder hql = new StringBuilder();
		List params = new ArrayList();
		hql.append(" select bean.id as id,bean.mId as mId,bean.sDate as sDate,");
		hql.append(" bean.webType as webType,bean.productType as productType,bean.status as status,");
		hql.append(" bean.settleStatus as settleStatus,bean.enterStatus as enterStatus,");
		hql.append(" bean.level as level,bean.remark as remark,bean.createTime as createTime,bean.updateTime as updateTime,");
		hql.append(" muser.username as mname,muser.nickname as mnickname,muser.role.id as roleId");
		hql.append(" from WorkTask bean,MUser muser ");
		hql.append(" where bean.mId = muser.id ");
		if (vo != null) {
			// 角色
			if (vo.getRoleId() != 0) {
				hql.append(" and muser.role.id = ? ");
				params.add(vo.getRoleId());
			}
		}
		if (StringUtil.isNotNull(orderby) && StringUtil.isNotNull(sort)) {
			hql.append(" order by bean.").append(orderby).append(" ").append(sort);
		} else {
			hql.append(" order by bean.createTime desc ");
		}
		return getPager(hql.toString(), params.toArray(), pageIndex, pageSize, WorkTaskVo.class);
	}

}
