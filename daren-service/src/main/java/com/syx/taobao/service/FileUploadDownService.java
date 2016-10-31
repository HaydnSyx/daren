package com.syx.taobao.service;

import java.io.InputStream;

import com.syx.taobao.exception.BizException;

public interface FileUploadDownService {

	public String uploadFtp(InputStream is, String fileName, Integer productType, String muserPath, boolean rename)
			throws BizException;

	public byte[] downFtp(int attachmentId) throws BizException;

	/**
	 * 普通文件上传
	 * 
	 * @param is
	 *            文件输入流
	 * @param fileName
	 *            文件名
	 * @param defaultRootPath
	 *            默认的文件存放跟路径
	 * @param filePath
	 *            自定义的文件目录 当为null时默认为images
	 * @param rename
	 *            是否修改文件名 为true时将修改为不重复的文件名 FALSE时采用传人的文件名
	 * @return 上传后的文件url
	 * @throws BizException
	 */
	public String upload(InputStream is, String fileName, String contextUrl, String defaultRootPath, String filePath,
			boolean rename) throws BizException;

	/**
	 * 图片压缩上传
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
	 * @return
	 * @throws BizException
	 */
	public String imgZoomUpload(InputStream is, String fileName, String contextUrl, String defaultRootPath,
			String filePath, boolean rename, int zoomWidth, int zoomHeight) throws BizException;

	/**
	 * 图片剪切上传
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
	 * @return
	 * @throws BizException
	 */
	public String imgCropUpload(InputStream is, String fileName, String contextUrl, String defaultRootPath,
			String filePath, boolean rename, int cropX, int cropY, int cropWidth, int cropHeight, String fileExtension)
			throws BizException;

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
	 *            压缩后的宽度 (文件为图片时有效)
	 * @param zoomHeight
	 *            压缩后的高度(文件为图片时有效)
	 * @param cropX
	 *            剪切的X坐标(文件为图片时有效)
	 * @param cropY
	 *            剪切的Y坐标(文件为图片时有效)
	 * @param cropWidth
	 *            剪切的宽度(文件为图片时有效)
	 * @param cropHeight
	 *            剪切的高度(文件为图片时有效)
	 * @param fileExtension
	 *            文件后缀名(文件为图片时有效)
	 * @param zoomPriority
	 *            true 压缩优先 false 裁剪优先 （压缩和剪切同时存在时有效)
	 * @return
	 * @throws BizException
	 */
	public String upload(InputStream is, String fileName, String contextUrl, String defaultRootPath, String filePath,
			boolean rename, int zoomWidth, int zoomHeight, int cropX, int cropY, int cropWidth, int cropHeight,
			String fileExtension, boolean zoomPriority) throws BizException;
}