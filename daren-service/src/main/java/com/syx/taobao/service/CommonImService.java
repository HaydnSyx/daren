package com.syx.taobao.service;

import com.syx.taobao.exception.BizException;
import com.syx.taobao.vo.ImVo;

public interface CommonImService {

	public ImVo getAllDataByMId(int mId) throws BizException;
}
