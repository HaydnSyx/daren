package com.syx.taobao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.syx.taobao.common.AppConstant;
import com.syx.taobao.dao.ImFriendGroupDao;
import com.syx.taobao.exception.BizException;
import com.syx.taobao.exception.DaoException;
import com.syx.taobao.model.ImFriendGroup;
import com.syx.taobao.model.ImFriendGroupMem;
import com.syx.taobao.service.ImCommonService;
import com.syx.taobao.util.JsonTools;
import com.syx.taobao.vo.ResultVo;
import com.syx.taobao.vo.im.BaseImVo;
import com.syx.taobao.vo.im.MsgVo;

@Service
@Transactional(rollbackFor = BizException.class, propagation = Propagation.REQUIRED)
public class ImCommonServiceImpl implements ImCommonService {

	@Autowired
	private ImFriendGroupDao imFriendGroupDao;

	@Override
	public ResultVo handleMsg(String msg) throws BizException {
		Date now = new Date();

		BaseImVo vo = JsonTools.read(msg, BaseImVo.class);
		try {
			if ("bind".equals(vo.getType())) {
				vo.setResultCode(AppConstant.IM_CODE_CONNECT_SUCCESS);
				List<Integer> friends = new ArrayList<>();
				// 好友列表
				List<ImFriendGroup> imFriendGroupList = imFriendGroupDao.queryImFriendGroupByMId(vo.getMine().getId());
				for (ImFriendGroup imFriendGroup : imFriendGroupList) {
					for (ImFriendGroupMem imFriendGroupMem : imFriendGroup.getFriendGroupMems()) {
						friends.add(imFriendGroupMem.getmId());
					}
				}
				vo.setData(friends);
			} else if ("friend".equals(vo.getType())) {
				vo.setResultCode(AppConstant.IM_CODE_NOTICE_NEWMSG);
				MsgVo m = new MsgVo();
				m.setUsername(vo.getMine().getUsername());
				m.setAvatar(vo.getMine().getAvatar());
				m.setId(vo.getMine().getId());
				m.setType(vo.getTo().getType());
				m.setContent(vo.getMine().getContent());
				m.setMine(false);
				m.setTimestamp(now.getTime());
				vo.setMsg(m);
				// TODO 聊天记录存储

			}
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return new ResultVo(AppConstant.RESULT_STATUS_SUCCESS, null, vo);
	}
}
