package com.syx.taobao.controller.common;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.syx.taobao.exception.BizException;
import com.syx.taobao.service.FileUploadDownService;
import com.syx.taobao.service.PropertiesService;
import com.syx.taobao.vo.CommentFileUpResult;

@Controller
public abstract class AbstractFileController {

	private Logger logger = LoggerFactory.getLogger(AbstractFileController.class);
	@Autowired
	protected PropertiesService propertiesService;
	@Autowired
	protected FileUploadDownService fileUploadService;

	public CommentFileUpResult fileup(String filePath, String targetType, Integer productType,
			MultipartHttpServletRequest request) {

		try {
			Iterator<String> itr = request.getFileNames();
			MultipartFile mpf = null;
			// List<Map<String, String>> fileList = new ArrayList<>();
			// Map<String, String> map = null;
			if (itr.hasNext()) {
				mpf = request.getFile(itr.next());

				// 文件保存目录路径
				String rootPath = getRootPath(request);
				// 最大文件大小
				long maxSize = 8388608;
				if (!ServletFileUpload.isMultipartContent(request)) {
					return getError("请选择文件。");
				}

				String fileName = mpf.getOriginalFilename();
				long fileSize = mpf.getSize();
				// 检查文件大小
				if (fileSize > maxSize) {
					return getError("上传文件大小超过限制。");
				}

				final String muserPath = getMuserPath(request);

				String url = "";
				if ("ftp".equals(targetType)) {
					url = fileUploadService.uploadFtp(mpf.getInputStream(), fileName, productType, muserPath, true);
				} else if("apache".equals(targetType)) {
					url = fileUploadService.upload(mpf.getInputStream(), fileName, getFileUpUrl(request), rootPath,
							filePath, true);
				}
				String serverFileName = url.substring(url.lastIndexOf("/") + 1);
				String uploadFileNameWithoutSuff = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));
				String suff = url.substring(url.lastIndexOf("."));
				CommentFileUpResult res = new CommentFileUpResult(0, "上传成功", url, serverFileName, fileName,
						uploadFileNameWithoutSuff, suff, fileSize);
				return res;
			}
		} catch (Exception e) {
			logger.error("img up failure!", e);
			return getError("图片上传异常!");
		}
		return null;

	}

	protected ResponseEntity<byte[]> getDownloadResponseEntity(byte[] data, String fileName, MediaType mediaType) {
		HttpHeaders headers = new HttpHeaders();
		MediaType mt = MediaType.APPLICATION_OCTET_STREAM;
		if (mediaType != null) {
			mt = mediaType;
		}
		headers.setContentType(mt);
		headers.setContentDispositionFormData("attachment", fileName);
		// HttpStatus.CREATED 修改为 HttpStatus.OK ie不认识201
		return new ResponseEntity<byte[]>(data, headers, HttpStatus.OK);
	}

	public byte[] filedo(int attachmentId) {
		try {
			return fileUploadService.downFtp(attachmentId);
		} catch (BizException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected CommentFileUpResult getError(String message) {
		CommentFileUpResult res = new CommentFileUpResult(1, message);
		return res;
	}

	/**
	 * 获取项目跟路径
	 * 
	 * @throws BizException
	 */
	public abstract String getRootPath(HttpServletRequest request) throws BizException;

	public abstract String getFileUpUrl(HttpServletRequest request) throws BizException;

	public abstract String getMuserPath(HttpServletRequest request) throws BizException;

	// 去掉路径中最后的一个"/"
	protected String getPath(String path) {
		if (path.endsWith("/")) {
			return path.substring(0, path.length() - 1);
		}
		return path;
	}

}
