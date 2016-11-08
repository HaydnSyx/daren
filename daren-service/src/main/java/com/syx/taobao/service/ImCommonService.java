package com.syx.taobao.service;

import com.syx.taobao.exception.BizException;
import com.syx.taobao.vo.ResultVo;

public interface ImCommonService {

	public ResultVo handleMsg(String msg) throws BizException;
}
