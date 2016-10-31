package com.syx.taobao.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FtpUtil {

	private Logger logger = LoggerFactory.getLogger(FtpUtil.class);

	private FTPClient ftp;

	private String server;

	private int port;

	private String username;

	private String password;

	public FtpUtil(String server, int port, String username, String password) {
		this.server = server;
		if (port > 0) {
			this.port = port;
		} else {
			this.port = 21;
		}
		this.username = username;
		this.password = password;
		ftp = new FTPClient();
	}

	public FTPClient connect() throws Exception {
		try {
			ftp.connect(this.server, this.port);
			if (!ftp.login(this.username, this.password)) {
				ftp.logout();
				ftp = null;
				return ftp;
			}
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftp.setControlEncoding("UTF-8");
			ftp.setConnectTimeout(2000);
			ftp.setBufferSize(2048);
			int replyCode = ftp.getReplyCode();
			if ((!FTPReply.isPositiveCompletion(replyCode))) {
				closeFTPClient();
				// 释放空间
				ftp = null;
				throw new Exception("登录FTP服务器失败,请检查![Server:" + this.username + "、" + "User:" + this.username + "、"
						+ "Password:" + this.password);
			} else {
				return ftp;
			}
		} catch (Exception e) {
			ftp.disconnect();
			ftp = null;
			throw e;
		}
	}

	public boolean upload(InputStream is, String workDirPath, String fileName) {
		boolean success;
		try {
			if (!isDirectoryExists(workDirPath)) {
				ftp.makeDirectory(workDirPath);
			}
			ftp.changeWorkingDirectory(workDirPath);
			logger.info(fileName + "开始上传.....");
			success = ftp.storeFile(fileName, is);
			if (success) {
				logger.info(fileName + "上传成功..... [datetime is " + new Date().toString() + "]");
			} else {
				logger.info(fileName + "上传失败..... [datetime is " + new Date().toString() + "]");
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
				closeFTPClient(); // 退出连接
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return false;
	}

	public ByteArrayOutputStream downloadFile(String remoteDownLoadPath, String serverFileName) throws Exception {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		try {
			ftp.retrieveFile(remoteDownLoadPath, output);
		} catch (Exception e) {
		} finally {
			try {
				output.close();
				closeFTPClient(); // 退出连接
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return output;
	}

	/**
	 * 
	 * 查看目录是否存在
	 * 
	 * @param path
	 * 
	 * @return boolean
	 * 
	 * @throws IOException
	 * 
	 */
	public boolean isDirectoryExists(String path) throws IOException {
		boolean flag = false;
		FTPFile[] ftpFileArr = ftp.listFiles("/");
		for (FTPFile ftpFile : ftpFileArr) {
			if (ftpFile.isDirectory() && ftpFile.getName().equalsIgnoreCase(path)) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	/**
	 * 
	 * 关闭FTP连接
	 * 
	 * @throws Exception
	 * 
	 */
	public void closeFTPClient() throws Exception {
		this.closeFTPClient(this.ftp);
	}

	/**
	 * 
	 * 关闭FTP连接
	 * 
	 * @param ftp
	 * 
	 * @throws Exception
	 * 
	 */
	public void closeFTPClient(FTPClient ftp) throws Exception {
		try {
			if (ftp.isConnected()) {
				ftp.logout();
			}
			ftp.disconnect();
		} catch (Exception e) {
			throw new Exception("关闭FTP服务出错!");
		}
	}
}
