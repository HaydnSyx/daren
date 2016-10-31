package com.syx.taobao.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.syx.taobao.common.AppConstant;
import com.syx.taobao.dao.AttachmentDao;
import com.syx.taobao.dao.AuditDao;
import com.syx.taobao.dao.ProductCardDao;
import com.syx.taobao.dao.ProductDao;
import com.syx.taobao.dao.WorkRecordDao;
import com.syx.taobao.dao.WorkTaskDao;
import com.syx.taobao.exception.BizException;
import com.syx.taobao.exception.DaoException;
import com.syx.taobao.model.Attachment;
import com.syx.taobao.model.Audit;
import com.syx.taobao.model.Product;
import com.syx.taobao.model.ProductCard;
import com.syx.taobao.model.WorkRecord;
import com.syx.taobao.model.WorkTask;
import com.syx.taobao.model.common.Pager;
import com.syx.taobao.service.WorkTaskService;
import com.syx.taobao.util.StringUtil;
import com.syx.taobao.vo.ResultVo;
import com.syx.taobao.vo.WorkTaskVo;

@Service
@Transactional(rollbackFor = BizException.class, propagation = Propagation.REQUIRED)
public class WorkTaskServiceImpl implements WorkTaskService {

	private Logger logger = LoggerFactory.getLogger(WorkTaskServiceImpl.class);
	@Autowired
	private WorkTaskDao workTaskDao;
	@Autowired
	private ProductCardDao productCardDao;
	@Autowired
	private AttachmentDao attachmentDao;
	@Autowired
	private AuditDao auditDao;
	@Autowired
	private WorkRecordDao workRecordDao;
	@Autowired
	private ProductDao productDao;

	@Override
	public Map<String, Integer> addWorkTask(WorkTask o) throws BizException {
		try {
			Date now = new Date();
			// 草稿
			o.setStatus(AppConstant.TASK_STATUS_DRAFT);
			// 未结算
			o.setSettleStatus(AppConstant.TASK_SETTLE_STATUS_NONE);
			// 未录入
			o.setEnterStatus(AppConstant.TASK_ENTER_STATUS_NONE);
			o.setsDate(now);
			o.setCreateTime(now);
			o.setUpdateTime(now);
			workTaskDao.addWorkTask(o);

			Map<String, Integer> map = new HashMap<>();
			map.put("taskId", o.getId());
			// 添加产品
			if (o.getProductType() == AppConstant.PRODUCT_SINGLEPRODUCT) {

			} else if (o.getProductType() == AppConstant.PRODUCT_DETAILLIST) {

			} else if (o.getProductType() == AppConstant.PRODUCT_CARD) {
				ProductCard pc = new ProductCard();
				pc.setTaskId(o.getId());
				productCardDao.addProductCard(pc);
			}
			return map;
		} catch (DaoException e) {
			logger.error("add WorkTask failure! WorkTask=[" + o + "]", e);
			throw new BizException("add WorkTask failure! WorkTask=[" + o + "]");
		}
	}

	@Override
	public void deleteWorkTask(int id) throws BizException {
		try {
			workTaskDao.deleteWorkTask(id);
		} catch (DaoException e) {
			logger.error("delete WorkTask failure! id=[" + id + "]", e);
			throw new BizException("delete WorkTask failure! id=[" + id + "]");
		}
	}

	@Override
	public WorkTask queryWorkTask(int id) throws BizException {
		try {
			return workTaskDao.queryWorkTask(id);
		} catch (DaoException e) {
			logger.error("query WorkTask failure! id=[" + id + "]", e);
			throw new BizException("query WorkTask failure! id=[" + id + "]");
		}
	}

	@Override
	public ResultVo updateWorkTaskAndProduct(WorkTask o, ProductCard pc, String[] url, String[] serverName,
			String[] originName, Long[] fileSize, Integer[] originAttachmentId) throws BizException {
		try {
			final int taskId = pc.getTaskId();
			WorkTask workTask = workTaskDao.queryWorkTask(taskId);
			// 更新单品信息
			if (workTask.getProductType() == AppConstant.PRODUCT_SINGLEPRODUCT) {
			}
			// 更新清单信息
			else if (workTask.getProductType() == AppConstant.PRODUCT_DETAILLIST) {
			}
			// 更新帖子信息
			else if (workTask.getProductType() == AppConstant.PRODUCT_CARD) {
				ProductCard productCard = productCardDao.queryProductCardByTaskId(taskId);
				productCard.setTitle(pc.getTitle());
				productCard.setSubTitle(pc.getSubTitle());
				productCard.setContent(pc.getContent());
				productCard.setTag(pc.getTag());
				productCardDao.updateProductCard(productCard);
			}
			workTask.setUpdateTime(new Date());
			workTask.setRemark(o.getRemark());
			workTaskDao.updateWorkTask(workTask);

			// 删除附件信息
			List<Attachment> attachments = attachmentDao.queryAttachmentByTaskId(taskId);
			if (attachments.size() > 0) {
				List<Integer> serverAttachmentId = new ArrayList<>();
				List<Integer> sumbitAttachmentid = null;
				if (originAttachmentId == null || originAttachmentId.length == 0) {
					sumbitAttachmentid = new ArrayList<>();
				} else {
					sumbitAttachmentid = Arrays.asList(originAttachmentId);
				}
				for (Attachment a : attachments) {
					serverAttachmentId.add(a.getId());
				}
				// 收集被删除的附件
				Collection<Integer> noexits = new ArrayList<>(serverAttachmentId);
				noexits.removeAll(sumbitAttachmentid);

				for (Integer id : noexits) {
					if (id != null && id > 0) {
						attachmentDao.deleteAttachment(id);
					}
				}
			}

			Attachment attachment = null;
			// 添加附件信息
			for (int i = 0; i < url.length; i++) {
				attachment = new Attachment();
				attachment.setTaskId(taskId);
				attachment.setWebType(workTask.getWebType());
				attachment.setProductType(workTask.getProductType());
				attachment.setAttachmentUrl(url[i]);
				if (serverName.length > 0 && i < serverName.length && StringUtil.isNotNull(serverName[i])) {
					attachment.setAttachmentServername(serverName[i]);
				}
				if (originName.length > 0 && i < originName.length && StringUtil.isNotNull(originName[i])) {
					attachment.setAttachmentOriginname(originName[i]);
				}
				if (fileSize.length > 0 && i < fileSize.length && fileSize[i] != null && fileSize[i] > 0) {
					attachment.setAttachmentSize(fileSize[i]);
				}

				attachmentDao.addAttachment(attachment);
			}
			return new ResultVo(AppConstant.RESULT_STATUS_SUCCESS, null, null);
		} catch (DaoException e) {
			logger.error("update WorkTask failure! WorkTask=[" + o + "]", e);
			throw new BizException("update WorkTask failure! WorkTask=[" + o + "]");
		}
	}

	@Transactional(readOnly = true)
	@Override
	public Pager<WorkTaskVo> queryWorkTaskPagerByCustom(WorkTaskVo vo, String orderby, String sort, int pageIndex,
			int pageSize) throws BizException {
		try {
			return workTaskDao.queryWorkTaskPagerByCustom(vo, orderby, sort, pageIndex, pageSize);
		} catch (DaoException e) {
			logger.error("query WorkTask Pager failure!", e);
			throw new BizException("query WorkTask Pager failure!");
		}
	}

	@Override
	public void updateWorkTask(WorkTask o) throws BizException {
		try {
			workTaskDao.updateWorkTask(o);
		} catch (DaoException e) {
			logger.error("update WorkTask failure! WorkTask=[" + o + "]", e);
			throw new BizException("update WorkTask failure! WorkTask=[" + o + "]");
		}
	}

	@Override
	public ResultVo auditWorkTask(int mId, int taskId, int type, int status, String remark) throws BizException {
		Audit audit = null;
		String message = null;
		List<Attachment> attachmentList = null;
		try {
			WorkTask wt = workTaskDao.queryWorkTask(taskId);
			// 状态
			if (type == 1) {
				// 提交
				if (status == 2) {
					// 单品
					if (AppConstant.PRODUCT_SINGLEPRODUCT == wt.getProductType()) {

					}
					// 清单
					else if (AppConstant.PRODUCT_DETAILLIST == wt.getProductType()) {

					}
					// 帖子
					else if (AppConstant.PRODUCT_CARD == wt.getProductType()) {
						ProductCard pc = productCardDao.queryProductCardByTaskId(taskId);
						attachmentList = attachmentDao.queryAttachmentByTaskId(taskId);
						if (StringUtil.isNull(pc.getContent())
								&& (attachmentList == null || attachmentList.isEmpty())) {
							return new ResultVo(AppConstant.RESULT_STATUS_ERROR, "帖子未完成不能进行提交（内容或者附件，二者保证至少完成其一）",
									null);
						}
					}
					wt.setStatus(AppConstant.TASK_STATUS_SUMBIT);
					audit = new Audit(taskId, mId, AppConstant.AUDIT_SUBMIT, remark, new Date());
				}
				// 驳回
				if (status == 3) {
					if (StringUtil.isNull(remark)) {
						return new ResultVo(AppConstant.RESULT_STATUS_ERROR, "请填写驳回原因", null);
					}
					wt.setStatus(AppConstant.TASK_STATUS_REJECT);
					audit = new Audit(taskId, mId, AppConstant.AUDIT_REJECT, remark, new Date());
				}
				// 通过
				if (status == 4) {
					wt.setStatus(AppConstant.TASK_STATUS_PASS);
					audit = new Audit(taskId, mId, AppConstant.AUDIT_PASS, remark, new Date());

					Date today = new Date();
					double price = 0;
					int productType = 0;
					// 查找产品价格
					if (AppConstant.WEB_TAOBAO == wt.getWebType()) {
						if (AppConstant.PRODUCT_SINGLEPRODUCT == wt.getProductType()) {
							productType = 1;
						} else if (AppConstant.PRODUCT_DETAILLIST == wt.getProductType()) {
							productType = 2;
						} else if (AppConstant.PRODUCT_CARD == wt.getProductType()) {
							productType = 3;
						}
					} else if (AppConstant.WEB_TAOBAO == wt.getWebType()) {
						if (AppConstant.PRODUCT_SINGLEPRODUCT == wt.getProductType()) {
							productType = 4;
						} else if (AppConstant.PRODUCT_DETAILLIST == wt.getProductType()) {
							productType = 5;
						} else if (AppConstant.PRODUCT_CARD == wt.getProductType()) {
							productType = 6;
						}
					}
					Product p = productDao.queryProductByProductType(productType);
					if (p != null) {
						price = p.getProductPrice();
					}

					final int workMId = wt.getmId();
					// 增加到工作记录中
					WorkRecord wr = workRecordDao.queryWorkRecordByMIdAndSDate(workMId, today);
					if (wr == null) {
						wr = new WorkRecord();
						wr.setmId(workMId);
						wr.setsDate(today);
						if (AppConstant.WEB_TAOBAO == wt.getWebType()) {
							if (AppConstant.PRODUCT_SINGLEPRODUCT == wt.getProductType()) {
							} else if (AppConstant.PRODUCT_DETAILLIST == wt.getProductType()) {
							} else if (AppConstant.PRODUCT_CARD == wt.getProductType()) {
								wr.setTbCardNum(1);
								wr.setTbCardAmount(1 * price);
							}
						} else if (AppConstant.WEB_TAOBAO == wt.getWebType()) {
							if (AppConstant.PRODUCT_SINGLEPRODUCT == wt.getProductType()) {
							} else if (AppConstant.PRODUCT_DETAILLIST == wt.getProductType()) {
							} else if (AppConstant.PRODUCT_CARD == wt.getProductType()) {
								wr.setTmCardNum(1);
								wr.setTmCardAmount(1 * price);
							}
						}
						workRecordDao.addWorkRecord(wr);
					} else {
						int num = 0;
						double total = 0;
						if (AppConstant.WEB_TAOBAO == wt.getWebType()) {
							if (AppConstant.PRODUCT_SINGLEPRODUCT == wt.getProductType()) {
							} else if (AppConstant.PRODUCT_DETAILLIST == wt.getProductType()) {
							} else if (AppConstant.PRODUCT_CARD == wt.getProductType()) {
								num = wr.getTbCardNum();
								total = wr.getTbCardAmount();
								wr.setTbCardNum(++num);
								wr.setTbCardAmount(total + 1 * price);
							}
						} else if (AppConstant.WEB_TAOBAO == wt.getWebType()) {
							if (AppConstant.PRODUCT_SINGLEPRODUCT == wt.getProductType()) {
							} else if (AppConstant.PRODUCT_DETAILLIST == wt.getProductType()) {
							} else if (AppConstant.PRODUCT_CARD == wt.getProductType()) {
								num = wr.getTmCardNum();
								total = wr.getTmCardAmount();
								wr.setTmCardNum(++num);
								wr.setTmCardAmount(total + 1 * price);
							}
						}
						wr.setUpdateTime(today);
						workRecordDao.updateWorkRecord(wr);
					}
				}
			}
			// 结算
			else if (type == 2) {
				wt.setSettleStatus(status);
				if (status == AppConstant.TASK_SETTLE_STATUS_HAS) {
					audit = new Audit(taskId, mId, AppConstant.AUDIT_SETTED, remark, new Date());
				}
			}
			// 录入
			else if (type == 3) {
				wt.setEnterStatus(status);
				if (status == AppConstant.TASK_ENTER_STATUS_HAS) {
					audit = new Audit(taskId, mId, AppConstant.AUDIT_ENTERD, remark, new Date());
				}
			}
			workTaskDao.updateWorkTask(wt);
			if (audit != null) {
				auditDao.addAudit(audit);
			}
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return new ResultVo(AppConstant.RESULT_STATUS_SUCCESS, message, null);
	}
}
