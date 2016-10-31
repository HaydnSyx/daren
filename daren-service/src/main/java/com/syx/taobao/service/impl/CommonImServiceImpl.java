package com.syx.taobao.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.syx.taobao.common.AppConstant;
import com.syx.taobao.dao.ImFriendGroupDao;
import com.syx.taobao.dao.ImGroupDao;
import com.syx.taobao.dao.MUserDao;
import com.syx.taobao.exception.BizException;
import com.syx.taobao.exception.DaoException;
import com.syx.taobao.model.ImFriendGroup;
import com.syx.taobao.model.ImFriendGroupMem;
import com.syx.taobao.model.ImGroup;
import com.syx.taobao.model.MUser;
import com.syx.taobao.service.CommonImService;
import com.syx.taobao.util.StringUtil;
import com.syx.taobao.vo.ImFriendGroupMemVo;
import com.syx.taobao.vo.ImFriendGroupVo;
import com.syx.taobao.vo.ImVo;

@Service
@Transactional(rollbackFor = BizException.class, propagation = Propagation.REQUIRED)
public class CommonImServiceImpl implements CommonImService {

	private Logger logger = LoggerFactory.getLogger(CommonImServiceImpl.class);

	@Autowired
	private MUserDao mUserDao;
	@Autowired
	private ImFriendGroupDao imFriendGroupDao;
	@Autowired
	private ImGroupDao imGroupDao;

	@Transactional(readOnly = true)
	@Override
	public ImVo getAllDataByMId(int mId) throws BizException {
		try {
			ImVo vo = new ImVo();
			vo.setCode(0);
			vo.setMsg("");

			Map<String, Object> map = new HashMap<>();
			MUser muser = mUserDao.queryMUser(mId);
			// 个人信息
			Map<String, String> mine = new HashMap<>();
			mine.put("username", StringUtil.isNull(muser.getNickname()) ? muser.getUsername() : muser.getNickname());
			mine.put("id", String.valueOf(muser.getId()));
			mine.put("status", "online");
			mine.put("sign", muser.getSign());
			mine.put("avatar", StringUtil.isNull(muser.getAvatar()) ? AppConstant.USER_HEADER_IMG_DEFAULTURL : muser.getAvatar());
			map.put("mine", mine);
			// 好友列表
			List<ImFriendGroup> imFriendGroupList = imFriendGroupDao.queryImFriendGroupByMId(mId);
			/**
			 * hibernate在进行json序列化时由于懒加载性质<br>
			 * 致使无法正确json化从而出现[no session or session was closed]现象<br>
			 * 故采取折中办法<br>
			 * 重新定义相关的vo，将原始数据copy到vo中，对vo进行json化
			 */
			List<ImFriendGroupVo> imFriendGroupVoList = new ArrayList<>();
			ImFriendGroupVo imFriendGroupVo = null;
			for (ImFriendGroup imFriendGroup : imFriendGroupList) {
				imFriendGroupVo = new ImFriendGroupVo();
				BeanUtils.copyProperties(imFriendGroup, imFriendGroupVo);
				List<ImFriendGroupMemVo> imFriendGroupMemVoList = new ArrayList<>();
				ImFriendGroupMemVo imFriendGroupMemVo = null;
				for (ImFriendGroupMem imFriendGroupMem : imFriendGroup.getFriendGroupMems()) {
					imFriendGroupMemVo = new ImFriendGroupMemVo();
					BeanUtils.copyProperties(imFriendGroupMem, imFriendGroupMemVo);
					imFriendGroupMemVoList.add(imFriendGroupMemVo);
				}
				imFriendGroupVo.setList(imFriendGroupMemVoList);
				imFriendGroupVoList.add(imFriendGroupVo);
			}
			map.put("friend", imFriendGroupVoList);
			// 群组
			List<ImGroup> imGroupList = imGroupDao.queryImGroupByMId(mId);
			map.put("group", imGroupList);

			vo.setData(map);
			return vo;
		} catch (DaoException e) {
			logger.error("get Data failure! mId=[" + mId + "]", e);
			throw new BizException("get Data failure! mId=[" + mId + "]");
		}
	}

}
