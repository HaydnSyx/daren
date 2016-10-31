package com.syx.taobao.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.syx.taobao.dao.WorkRecordDao;
import com.syx.taobao.dao.common.SimpleDao;
import com.syx.taobao.exception.DaoException;
import com.syx.taobao.model.WorkRecord;
import com.syx.taobao.model.common.Pager;
import com.syx.taobao.util.StringUtil;
import com.syx.taobao.vo.WorkRecordVo;

@Repository
public class WorkRecordDaoImpl extends SimpleDao<WorkRecord, Integer> implements WorkRecordDao {

	@Override
	public void addWorkRecord(WorkRecord o) throws DaoException {
		add(o);
	}

	@Override
	public void deleteWorkRecord(int id) throws DaoException {
		WorkRecord o = get(id);
		if (o != null) {
			delete(o);
		}
	}

	@Override
	public void updateWorkRecord(WorkRecord o) throws DaoException {
		update(o);
	}

	@Override
	public WorkRecord queryWorkRecord(int id) throws DaoException {
		return get(id);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Pager<WorkRecordVo> queryWorkRecordPagerByCustom(WorkRecordVo vo, String orderby, String sort, int pageIndex,
			int pageSize) throws DaoException {
		StringBuilder hql = new StringBuilder();
		List params = new ArrayList();
		hql.append(" select bean.id as id,bean.mId as mId,bean.sDate as sDate,");
		hql.append(
				" bean.tbSingleproductNum as tbSingleproductNum,bean.tbSingleproductAmount as tbSingleproductAmount,");
		hql.append(" bean.tbDetaillistNum as tbDetaillistNum,bean.tbDetaillistAmount as tbDetaillistAmount,");
		hql.append(" bean.tbCardNum as tbCardNum,bean.tbCardAmount as tbCardAmount,");
		hql.append(
				" bean.tmSingleproductNum as tmSingleproductNum,bean.tmSingleproductAmount as tmSingleproductAmount,");
		hql.append(" bean.tmDetaillistNum as tmDetaillistNum,bean.tmDetaillistAmount as tmDetaillistAmount,");
		hql.append(" bean.tmCardNum as tmCardNum,bean.tmCardAmount as tmCardAmount,");
		hql.append(
				" bean.level as level,bean.remark as remark,bean.createTime as createTime,bean.updateTime as updateTime,");
		hql.append(" muser.username as mname,muser.nickname as mnickname,muser.role.id as roleId");
		hql.append(" from WorkRecord bean,MUser muser ");
		hql.append(" where bean.mId = muser.id ");

		if (StringUtil.isNotNull(orderby) && StringUtil.isNotNull(sort)) {
			hql.append(" order by bean.").append(orderby).append(" ").append(sort);
		} else {
			hql.append(" order by bean.id desc ");
		}
		return getPager(hql.toString(), params.toArray(), pageIndex, pageSize, WorkRecordVo.class);
	}

	@Override
	public WorkRecord queryWorkRecordByMIdAndSDate(int mId, Date sdate) throws DaoException {
		String hql = " from WorkRecord bean where bean.mId = :mId and bean.sDate = :sdate ";
		return (WorkRecord) getSession().createQuery(hql).setParameter("mId", mId).setDate("sdate", sdate)
				.uniqueResult();
	}

}
