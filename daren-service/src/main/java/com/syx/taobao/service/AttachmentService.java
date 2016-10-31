package com.syx.taobao.service;

import java.util.List;

import com.syx.taobao.exception.BizException;
import com.syx.taobao.model.Attachment;

public interface AttachmentService {

	public List<Attachment> queryAttachmentByTaskId(int taskId) throws BizException;

	public Attachment queryAttachment(int id) throws BizException;
}
