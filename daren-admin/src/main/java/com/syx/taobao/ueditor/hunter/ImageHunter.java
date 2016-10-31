package com.syx.taobao.ueditor.hunter;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.common.io.Files;
import com.syx.taobao.common.AppConstant;
import com.syx.taobao.service.PropertiesService;
import com.syx.taobao.ueditor.PathFormat;
import com.syx.taobao.ueditor.define.AppInfo;
import com.syx.taobao.ueditor.define.BaseState;
import com.syx.taobao.ueditor.define.MIMEType;
import com.syx.taobao.ueditor.define.MultiState;
import com.syx.taobao.ueditor.define.State;
import com.syx.taobao.ueditor.upload.StorageManager;
import com.syx.taobao.util.StringUtil;

/**
 * 图片抓取器
 * 
 * @author hancong03@baidu.com
 *
 */
public class ImageHunter {

	private String filename = null;
	private String savePath = null;
	private String rootPath = null;
	private List<String> allowTypes = null;
	private long maxSize = -1;

	private List<String> filters = null;

	private PropertiesService propertyService;

	public ImageHunter(Map<String, Object> conf, PropertiesService propertyService) {

		this.propertyService = propertyService;
		this.filename = (String) conf.get("filename");
		this.savePath = (String) conf.get("savePath");
		this.rootPath = (String) conf.get("rootPath");
		this.maxSize = (Long) conf.get("maxSize");
		this.allowTypes = Arrays.asList((String[]) conf.get("allowFiles"));
		this.filters = Arrays.asList((String[]) conf.get("filter"));

	}

	public State capture(String[] list) {

		MultiState state = new MultiState(true);

		for (String source : list) {
			state.addState(captureRemoteData(source));
		}

		return state;

	}

	public State captureRemoteData(String urlStr) {

		HttpURLConnection connection = null;
		URL url = null;
		String suffix = null;

		try {
			url = new URL(urlStr);

			if (!validHost(url.getHost())) {
				return new BaseState(false, AppInfo.PREVENT_HOST);
			}

			connection = (HttpURLConnection) url.openConnection();

			connection.setInstanceFollowRedirects(true);
			connection.setUseCaches(true);

			if (!validContentState(connection.getResponseCode())) {
				return new BaseState(false, AppInfo.CONNECTION_ERROR);
			}

			suffix = MIMEType.getSuffix(connection.getContentType());

			if (!validFileType(suffix)) {
				return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
			}

			if (!validFileSize(connection.getContentLength())) {
				return new BaseState(false, AppInfo.MAX_SIZE);
			}

			String physicalPath;
			String sp;
			try {
				final String ueditorSite = propertyService.queryPropValue(AppConstant.APACHE_SERVER_WEBSITE);
				final String rootPath = propertyService.queryPropValue(AppConstant.FILE_PATH);
				final String imgPath = propertyService.queryPropValue(AppConstant.FILE_IMG_PATH);
				final String realPath = propertyService.queryPropValue(AppConstant.APACHE_SERVER_REAL_PATH);
				
				// 文件保存真实路径
				String savePath = realPath + rootPath + imgPath;
				// 文件保存URL
				String saveUrl = ueditorSite + rootPath + imgPath;

				String rename = urlStr;
				String newFileName = "";
				String fileExt = "";
				if (StringUtil.isNotNull(rename)) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
					String ymd = sdf.format(new Date());
					savePath += "/" + ymd + "/";
					saveUrl += "/" + ymd + "/";
					fileExt = Files.getFileExtension(rename).toLowerCase();
					if (StringUtil.isNull(fileExt)) {
						if (StringUtil.isNotNull(suffix)) {
							fileExt = suffix;
						} else {
							fileExt = ".jpg";
						}
					} else {
						fileExt = "." + fileExt;
					}
					SimpleDateFormat df = new SimpleDateFormat("ddHHmmss");
					newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + fileExt;
				}
				File uploadedFile = new File(savePath, newFileName);

				physicalPath = uploadedFile.getAbsolutePath();

				sp = saveUrl + newFileName;
			} catch (Exception e) {
				e.printStackTrace();
				sp = this.getPath(this.savePath, this.filename, suffix);
				physicalPath = this.rootPath + savePath;
			}

			State state = StorageManager.saveFileByInputStream(connection.getInputStream(), physicalPath);

			if (state.isSuccess()) {
				state.putInfo("url", PathFormat.format(sp));
				state.putInfo("source", urlStr);
			}

			return state;

		} catch (Exception e) {
			return new BaseState(false, AppInfo.REMOTE_FAIL);
		}

	}

	private String getPath(String savePath, String filename, String suffix) {

		return PathFormat.parse(savePath + suffix, filename);

	}

	private boolean validHost(String hostname) {

		return !filters.contains(hostname);

	}

	private boolean validContentState(int code) {

		return HttpURLConnection.HTTP_OK == code;

	}

	private boolean validFileType(String type) {

		return this.allowTypes.contains(type);

	}

	private boolean validFileSize(int size) {
		return size < this.maxSize;
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
