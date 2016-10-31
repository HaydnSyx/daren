package com.syx.taobao.ueditor.hunter;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.syx.taobao.common.AppConstant;
import com.syx.taobao.exception.BizException;
import com.syx.taobao.service.PropertiesService;
import com.syx.taobao.ueditor.define.AppInfo;
import com.syx.taobao.ueditor.define.BaseState;
import com.syx.taobao.ueditor.define.MultiState;
import com.syx.taobao.ueditor.define.State;

public class FileManager {

	private String dir = null;
	private String rootPath = null;
	private String[] allowFiles = null;
	private int count = 0;

	private String savePath = null;
	private String saveUrl = null;

	public FileManager(Map<String, Object> conf, PropertiesService propertyService) throws BizException {

		this.rootPath = (String) conf.get("rootPath");
		this.dir = this.rootPath + (String) conf.get("dir");
		this.allowFiles = this.getAllowFiles(conf.get("allowFiles"));
		this.count = (Integer) conf.get("count");

		final String ueditorSite = propertyService.queryPropValue(AppConstant.APACHE_SERVER_WEBSITE);
		final String rootPath = propertyService.queryPropValue(AppConstant.FILE_PATH);
		final String commonPath = propertyService.queryPropValue(AppConstant.FILE_COMMON_PATH);
		final String realPath = propertyService.queryPropValue(AppConstant.APACHE_SERVER_REAL_PATH);

		// 文件保存真实路径
		savePath = realPath + rootPath + commonPath;
		// 文件保存URL
		saveUrl = ueditorSite + rootPath + commonPath;

		this.dir = savePath;

	}

	public State listFile(int index) {

		File dir = new File(this.dir);
		State state = null;

		if (!dir.exists()) {
			return new BaseState(false, AppInfo.NOT_EXIST);
		}

		if (!dir.isDirectory()) {
			return new BaseState(false, AppInfo.NOT_DIRECTORY);
		}

		Collection<File> list = FileUtils.listFiles(dir, this.allowFiles, true);

		if (index < 0 || index > list.size()) {
			state = new MultiState(true);
		} else {
			Object[] fileList = Arrays.copyOfRange(list.toArray(), index, index + this.count);
			state = this.getState(fileList);
		}

		state.putInfo("start", index);
		state.putInfo("total", list.size());

		return state;

	}

	private State getState(Object[] files) {

		MultiState state = new MultiState(true);
		BaseState fileState = null;

		File file = null;

		for (Object obj : files) {
			if (obj == null) {
				break;
			}
			file = (File) obj;
			fileState = new BaseState(true);
			// PathFormat.format( this.getPath( file ) )
			String url = getUrl(file);
			fileState.putInfo("url", url);
			state.addState(fileState);
		}

		return state;

	}

	@SuppressWarnings("unused")
	private String getPath(File file) {

		String path = file.getAbsolutePath();

		return path.replace(this.rootPath, "/");

	}

	private String[] getAllowFiles(Object fileExt) {

		String[] exts = null;
		String ext = null;

		if (fileExt == null) {
			return new String[0];
		}

		exts = (String[]) fileExt;

		for (int i = 0, len = exts.length; i < len; i++) {

			ext = exts[i];
			exts[i] = ext.replace(".", "");

		}

		return exts;

	}

	// 去掉路径中最后的一个"/"
	@SuppressWarnings("unused")
	private static String getPath(String path) {
		if (path.endsWith("/")) {
			return path.substring(0, path.length() - 1);
		}
		return path;
	}

	private String getUrl(File file) {

		String path = file.getAbsolutePath();

		String sp = path.replace("\\", "/");

		String fp = sp.replace(savePath, "");

		return saveUrl + fp;
	}

}
