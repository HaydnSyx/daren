package com.syx.taobao.controller;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.syx.taobao.common.AppConstant;
import com.syx.taobao.controller.common.AbstractFileController;
import com.syx.taobao.exception.BizException;
import com.syx.taobao.model.Attachment;
import com.syx.taobao.model.MUser;
import com.syx.taobao.service.AttachmentService;
import com.syx.taobao.service.MUserService;
import com.syx.taobao.util.StringUtil;
import com.syx.taobao.vo.CommentFileUpResult;
import com.syx.taobao.vo.im.FileUpResultVo;

@Controller
public class FileUploadDownController extends AbstractFileController {

	@Autowired
	private MUserService mUserService;
	@Autowired
	private AttachmentService attachmentService;

	@RequestMapping(value = "fileup", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public CommentFileUpResult fileup(String filePath, String targetType, Integer productType, MultipartHttpServletRequest request, HttpSession session) {
		return super.fileup(filePath, targetType, productType, request);
	}

	@RequestMapping(value = "filedown")
	public ResponseEntity<byte[]> filedown(@RequestParam("originAttachmentId") int attachmentId) {
		Attachment attachment = null;
		byte[] data = null;
		String fileName = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

			attachment = attachmentService.queryAttachment(attachmentId);
			String originFileName = StringUtil.isNull(attachment.getAttachmentOriginname()) ? sdf.format(new Date()) : attachment.getAttachmentOriginname();
			fileName = new String(originFileName.getBytes("UTF-8"), "iso-8859-1");
			data = super.filedo(attachmentId);
		} catch (UnsupportedEncodingException | BizException e) {
			e.printStackTrace();
		}
		return super.getDownloadResponseEntity(data, fileName, null);
	}

	@RequestMapping(value = "im/fileup", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public FileUpResultVo imfileup(String type, MultipartHttpServletRequest request, HttpSession session) {
		boolean file = false;
		String filePath = "/im";
		if ("img".equals(type)) {
			filePath = filePath + "/img";
		} else if ("file".equals(type)) {
			filePath = filePath + "/file";
			file = true;
		}
		return super.fileup(filePath, file, request);
	}

	/**
	 * 获取上传文件的前缀url
	 * 
	 * @throws BizException
	 */
	public String getFileUpUrl(HttpServletRequest request) throws BizException {
		String contextUrl = propertiesService.queryPropValue(AppConstant.PROP_WEBSITE); // 项目名称
		return contextUrl;
	}

	/**
	 * 获取项目跟路径
	 * 
	 * @throws BizException
	 */
	public String getRootPath(HttpServletRequest request) throws BizException {
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		String v = propertiesService.queryPropValue(AppConstant.FILE_PATH);
		if (v != null) {
			rootPath = getPath(v);
		}
		return rootPath;
	}

	public String getMuserPath(HttpServletRequest request) throws BizException {
		Integer mId = (Integer) request.getSession().getAttribute(AppConstant.SESSION_MUSER_ID);
		MUser muser = mUserService.queryMUser(mId);
		return muser == null ? "" : "/" + muser.getUsername();
	}
}
