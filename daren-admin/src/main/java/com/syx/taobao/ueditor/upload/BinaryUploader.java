package com.syx.taobao.ueditor.upload;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.common.io.Files;
import com.syx.taobao.common.AppConstant;
import com.syx.taobao.exception.BizException;
import com.syx.taobao.service.PropertiesService;
import com.syx.taobao.ueditor.define.AppInfo;
import com.syx.taobao.ueditor.define.BaseState;
import com.syx.taobao.ueditor.define.FileType;
import com.syx.taobao.ueditor.define.State;
import com.syx.taobao.util.StringUtil;

public class BinaryUploader {

	public static final State save(HttpServletRequest request, Map<String, Object> conf,
			PropertiesService propertyService) throws BizException {
		FileItemStream fileStream = null;
		boolean isAjaxUpload = request.getHeader("X_Requested_With") != null;

		if (!ServletFileUpload.isMultipartContent(request)) {
			return new BaseState(false, AppInfo.NOT_MULTIPART_CONTENT);
		}

		ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());

		if (isAjaxUpload) {
			upload.setHeaderEncoding("UTF-8");
		}

		try {
			FileItemIterator iterator = upload.getItemIterator(request);

			while (iterator.hasNext()) {
				fileStream = iterator.next();

				if (!fileStream.isFormField())
					break;
				fileStream = null;
			}

			if (fileStream == null) {
				return new BaseState(false, AppInfo.NOTFOUND_UPLOAD_DATA);
			}
			String originFileName = fileStream.getName();
			String suffix = FileType.getSuffixByFilename(originFileName);
			long maxSize = ((Long) conf.get("maxSize")).longValue();
			if (!validType(suffix, (String[]) conf.get("allowFiles"))) {
				return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
			}

			final String ueditorSite = propertyService.queryPropValue(AppConstant.APACHE_SERVER_WEBSITE);
			final String rootPath = propertyService.queryPropValue(AppConstant.FILE_PATH);
			final String imgPath = propertyService.queryPropValue(AppConstant.FILE_IMG_PATH);
			final String realPath = propertyService.queryPropValue(AppConstant.APACHE_SERVER_REAL_PATH);

			// 文件保存真实路径
			String savePath = realPath + rootPath + imgPath;
			// 文件保存URL
			String saveUrl = ueditorSite + rootPath + imgPath;

			String rename = originFileName;
			String newFileName = "";
			String fileExt = "";
			if (StringUtil.isNotNull(rename)) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				String ymd = sdf.format(new Date());
				savePath += "/" + ymd + "/";
				saveUrl += "/" + ymd + "/";
				fileExt = Files.getFileExtension(rename).toLowerCase();
				if (StringUtil.isNull(fileExt)) {
					fileExt = "jpg";
				}
				SimpleDateFormat df = new SimpleDateFormat("ddHHmmss");
				newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
			}
			File uploadedFile = new File(savePath, newFileName);
			InputStream is = fileStream.openStream();
			State storageState = StorageManager.saveFileByInputStream(fileStream.openStream(),
					uploadedFile.getAbsolutePath(), maxSize);
			is.close();

			if (storageState.isSuccess()) {
				storageState.putInfo("url", saveUrl + newFileName);
				storageState.putInfo("type", suffix);
				storageState.putInfo("original", originFileName);
			}
			return storageState;
		} catch (FileUploadException e) {
			return new BaseState(false, AppInfo.PARSE_REQUEST_ERROR);
		} catch (IOException e) {
		}
		return new BaseState(false, AppInfo.IO_ERROR);
	}

	private static boolean validType(String type, String[] allowTypes) {
		List<String> list = Arrays.asList(allowTypes);

		return list.contains(type);
	}

	// 去掉路径中最后的一个"/"
	@SuppressWarnings("unused")
	private static String getPath(String path) {
		if (path.endsWith("/")) {
			return path.substring(0, path.length() - 1);
		}
		return path;
	}
}
