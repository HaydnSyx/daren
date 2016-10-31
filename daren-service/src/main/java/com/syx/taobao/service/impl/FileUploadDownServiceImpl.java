package com.syx.taobao.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.io.Files;
import com.syx.taobao.common.AppConstant;
import com.syx.taobao.dao.AttachmentDao;
import com.syx.taobao.exception.BizException;
import com.syx.taobao.exception.DaoException;
import com.syx.taobao.model.Attachment;
import com.syx.taobao.service.FileUploadDownService;
import com.syx.taobao.service.PropertiesService;
import com.syx.taobao.util.FileIOUtil;
import com.syx.taobao.util.FtpUtil;
import com.syx.taobao.util.StringUtil;

@Service
@Transactional(rollbackFor = BizException.class, propagation = Propagation.REQUIRED)
public class FileUploadDownServiceImpl implements FileUploadDownService {

	private Logger logger = LoggerFactory.getLogger(FileUploadDownServiceImpl.class);

	@Autowired
	private PropertiesService propertyService;
	@Autowired
	private AttachmentDao attachmentDao;

	@Override
	public String upload(InputStream is, String fileName, String contextUrl, String defaultRootPath, String filePath, boolean rename)
			throws BizException {
		return upload(is, fileName, contextUrl, defaultRootPath, filePath, rename, 0, 0, -1, -1, 0, 0, null, false);
	}

	public String imgZoomUpload(InputStream is, String fileName, String contextUrl, String defaultRootPath, String filePath, boolean rename,
			int zoomWidth, int zoomHeight) throws BizException {
		return upload(is, fileName, contextUrl, defaultRootPath, filePath, rename, zoomWidth, zoomHeight, -1, -1, 0, 0, null, true);
	}

	public String imgCropUpload(InputStream is, String fileName, String contextUrl, String defaultRootPath, String filePath, boolean rename,
			int cropX, int cropY, int cropWidth, int cropHeight, String fileExtension) throws BizException {
		return upload(is, fileName, contextUrl, defaultRootPath, filePath, rename, 0, 0, cropX, cropY, cropWidth, cropHeight, fileExtension, false);
	}

	/**
	 * 文件上传
	 * 
	 * @param is
	 *            输入流
	 * @param fileName
	 *            文件名
	 * @param defaultRootPath
	 *            默认跟路径(在没有配置跟路径时有效)
	 * @param filePath
	 *            文件存放路径（默认为images）
	 * @param rename
	 *            是否修改文件名 为true时将修改为不重复的文件名 FALSE时采用传人的文件名
	 * @param zoomWidth
	 *            压缩后的宽度
	 * @param zoomHeight
	 *            压缩后的高度
	 * @param cropX
	 *            剪切的X坐标
	 * @param cropY
	 *            剪切的Y坐标
	 * @param cropWidth
	 *            剪切的宽度
	 * @param cropHeight
	 *            剪切的高度
	 * @param fileExtension
	 *            文件后缀名
	 * @param zoomPriority
	 *            true 压缩优先 false 裁剪优先 （压缩和剪切同时存在时有效)
	 * @return
	 * @throws BizException
	 */
	public String upload(InputStream is, String fileName, String contextUrl, String defaultRootPath, String filePath, boolean rename, int zoomWidth,
			int zoomHeight, int cropX, int cropY, int cropWidth, int cropHeight, String fileExtension, boolean zoomPriority) throws BizException {
		InputStream imgIs = null;
		try {

			final String apacheSite = propertyService.queryPropValue(AppConstant.APACHE_SERVER_WEBSITE);
			final String rootPath = propertyService.queryPropValue(AppConstant.FILE_PATH);
			final String apacheRealPath = propertyService.queryPropValue(AppConstant.APACHE_SERVER_REAL_PATH);

			// 文件保存真实路径
			String savePath = apacheRealPath + rootPath + getPath(filePath) + "/";
			// 文件保存URL
			String saveUrl = apacheSite + rootPath + getPath(filePath) + "/";

			String newFileName = fileName;
			if (rename) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				String ymd = sdf.format(new Date());
				savePath += ymd + "/";
				saveUrl += ymd + "/";
				String fileExt = Files.getFileExtension(fileName).toLowerCase();
				if (StringUtil.isNull(fileExt)) {
					fileExt = "jpg";
				}
				SimpleDateFormat df = new SimpleDateFormat("ddHHmmss");
				newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
			}
			File upDir = new File(savePath);
			if (!upDir.exists()) {
				upDir.mkdirs();
			}
			File uploadedFile = new File(savePath, newFileName);

			imgIs = is;

			// EasyImage image2 = new EasyImage(is,newFileName);
			if (zoomPriority) {
				if (zoomWidth > 0 && zoomHeight > 0) {
					imgIs = FileIOUtil.imageZoomOut(imgIs, zoomWidth, zoomHeight);
				}
				if (cropX > -1 && cropY > -1 && cropWidth > 0 && cropHeight > 0) {
					if (StringUtil.isNull(fileExtension)) {
						fileExtension = "png";
					}
					imgIs = FileIOUtil.imageCrop(imgIs, cropX, cropY, cropWidth, cropHeight, fileExtension);
				}

			} else {
				if (cropX > -1 && cropY > -1 && cropWidth > 0 && cropHeight > 0) {

					if (StringUtil.isNull(fileExtension)) {
						fileExtension = "png";
					}
					imgIs = FileIOUtil.imageCrop(imgIs, cropX, cropY, cropWidth, cropHeight, fileExtension);
				}
				if (zoomWidth > 0 && zoomHeight > 0) {
					imgIs = FileIOUtil.imageZoomOut(imgIs, zoomWidth, zoomHeight);
				}
			}
			FileIOUtil.writeFile(imgIs, uploadedFile.getAbsolutePath());
			return saveUrl + newFileName;
		} catch (Exception e) {
			logger.error("file upload failure!", e);
		} finally {
			if (imgIs != null) {
				try {
					imgIs.close();
					imgIs = null;
				} catch (IOException e) {
					logger.error("upload inputStream close failure!", e);
				}
			}
		}
		return null;
	}

	// 去掉路径中最后的一个"/"
	private String getPath(String path) {
		if (path.endsWith("/")) {
			return path.substring(0, path.length() - 1);
		}
		return path;
	}

	@Override
	public String uploadFtp(InputStream is, String fileName, Integer productType, String muserPath, boolean rename) throws BizException {

		final String ftpSite = propertyService.queryPropValue(AppConstant.FTP_SERVER_WEBSITE);
		final String rootPath = propertyService.queryPropValue(AppConstant.FILE_PATH);
		String path = "";
		if (productType != null) {
			// 单品
			if (AppConstant.PRODUCT_SINGLEPRODUCT == productType) {
				path = propertyService.queryPropValue(AppConstant.FILE_SINGLEPRODUCT_PATH);
			}
			// 清单
			else if (AppConstant.PRODUCT_DETAILLIST == productType) {
				path = propertyService.queryPropValue(AppConstant.FILE_DETAILLIST_PATH);
			}
			// 帖子
			else if (AppConstant.PRODUCT_CARD == productType) {
				path = propertyService.queryPropValue(AppConstant.FILE_CARD_PATH);
			}
			// 通用
			else {
				path = propertyService.queryPropValue(AppConstant.FILE_COMMON_PATH);
			}
		} else {
			path = propertyService.queryPropValue(AppConstant.FILE_COMMON_PATH);
		}

		final String addr = propertyService.queryPropValue(AppConstant.FTP_SERVER_ADDR);
		final int port = Integer.parseInt(propertyService.queryPropValue(AppConstant.FTP_SERVER_PORT));
		final String username = propertyService.queryPropValue(AppConstant.FTP_SERVER_USERNAME);
		final String password = propertyService.queryPropValue(AppConstant.FTP_SERVER_PASSWORD);

		// 文件保存真实路径
		String workPath = rootPath + path + muserPath;
		// 文件保存URL
		String saveUrl = ftpSite + rootPath + path + muserPath;

		String newFileName = "";
		String fileExt = "";
		if (rename) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			String ymd = sdf.format(new Date());
			workPath += "/" + ymd + "/";
			saveUrl += "/" + ymd + "/";
			fileExt = Files.getFileExtension(fileName).toLowerCase();
			if (StringUtil.isNull(fileExt)) {
				fileExt = "";
			}
			SimpleDateFormat df = new SimpleDateFormat("ddHHmmss");
			newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
		} else {
			newFileName = fileName;
		}

		FtpUtil fu = new FtpUtil(addr, port, username, password);
		try {
			fu.connect();
			if (fu.upload(is, workPath, newFileName)) {
				return saveUrl + newFileName;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public byte[] downFtp(int attachmentId) throws BizException {
		try {
			final String addr = propertyService.queryPropValue(AppConstant.FTP_SERVER_ADDR);
			final int port = Integer.parseInt(propertyService.queryPropValue(AppConstant.FTP_SERVER_PORT));
			final String username = propertyService.queryPropValue(AppConstant.FTP_SERVER_USERNAME);
			final String password = propertyService.queryPropValue(AppConstant.FTP_SERVER_PASSWORD);
			final String ftpWebsite = propertyService.queryPropValue(AppConstant.FTP_SERVER_WEBSITE);

			Attachment attachment = attachmentDao.queryAttachment(attachmentId);
			FtpUtil fu = new FtpUtil(addr, port, username, password);
			ByteArrayOutputStream os = null;
			try {
				String serverPath = "";
				String url = attachment.getAttachmentUrl();
				serverPath = url.replace(ftpWebsite, "");
				if (serverPath.startsWith(":")) {
					serverPath = serverPath.replace(":" + String.valueOf(port), "");
				}
				fu.connect();
				os = fu.downloadFile(serverPath, attachment.getAttachmentServername());
				return os.toByteArray();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
	}
}