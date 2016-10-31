package com.syx.taobao.ueditor.upload;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;

import com.google.common.io.Files;
import com.syx.taobao.common.AppConstant;
import com.syx.taobao.exception.BizException;
import com.syx.taobao.service.PropertiesService;
import com.syx.taobao.ueditor.define.AppInfo;
import com.syx.taobao.ueditor.define.BaseState;
import com.syx.taobao.ueditor.define.State;
import com.syx.taobao.util.StringUtil;

public final class Base64Uploader {

	public static State save(String content, Map<String, Object> conf, PropertiesService propertyService,
			String filePath) throws BizException, FileNotFoundException, IOException {
		byte[] data = decode(content);

		long maxSize = ((Long) conf.get("maxSize")).longValue();

		if (!validSize(data, maxSize)) {
			return new BaseState(false, AppInfo.MAX_SIZE);
		}

		final String ueditorSite = propertyService.queryPropValue(AppConstant.APACHE_SERVER_WEBSITE);
		final String rootPath = propertyService.queryPropValue(AppConstant.FILE_PATH);
		final String commonPath = propertyService.queryPropValue(AppConstant.FILE_COMMON_PATH);
		final String realPath = propertyService.queryPropValue(AppConstant.APACHE_SERVER_REAL_PATH);

		// 文件保存真实路径
		String savePath = realPath + rootPath + commonPath;
		// 文件保存URL
		String saveUrl = ueditorSite + rootPath + commonPath;

		String rename = (String) conf.get("filename");
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
		State storageState = StorageManager.saveBinaryFile(data, uploadedFile.getAbsolutePath());
		if (storageState.isSuccess()) {
			storageState.putInfo("url", saveUrl + newFileName);
			storageState.putInfo("type", fileExt);
			storageState.putInfo("original", rename);
		}

		return storageState;
	}

	private static byte[] decode(String content) {
		return Base64.decodeBase64(content);
	}

	private static boolean validSize(byte[] data, long length) {
		return data.length <= length;
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