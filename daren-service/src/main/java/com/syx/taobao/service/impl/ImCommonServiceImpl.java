package com.syx.taobao.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.syx.taobao.common.AppConstant;
import com.syx.taobao.exception.BizException;
import com.syx.taobao.service.ImCommonService;
import com.syx.taobao.util.JsonTools;
import com.syx.taobao.vo.ResultVo;
import com.syx.taobao.vo.im.BaseImVo;
import com.syx.taobao.vo.im.MsgVo;

@Service
@Transactional(rollbackFor = BizException.class, propagation = Propagation.REQUIRED)
public class ImCommonServiceImpl implements ImCommonService {

	@Override
	public ResultVo handleMsg(String msg) throws BizException {
		Date now = new Date();

		BaseImVo vo = JsonTools.read(msg, BaseImVo.class);
		if ("bind".equals(vo.getType())) {
			vo.setResultCode(AppConstant.IM_SEND_TYPE_BIND);
		} else if ("friend".equals(vo.getType())) {
			vo.setResultCode(AppConstant.IM_SEND_TYPE_FRIEND);
			MsgVo m = new MsgVo();
			m.setUsername(vo.getMine().getUsername());
			m.setAvatar(vo.getMine().getAvatar());
			m.setId(vo.getMine().getId());
			m.setType(vo.getTo().getType());
			m.setContent(vo.getMine().getContent());
			m.setMine(false);
			m.setTimestamp(now.getTime());

			vo.setMsg(m);
		}
		return new ResultVo(AppConstant.RESULT_STATUS_SUCCESS, null, vo);
	}
}
