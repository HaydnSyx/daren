package com.syx.taobao.util;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.ByteStreams;
import com.google.common.io.Files;
import com.syx.taobao.common.AppConstant;

public class FileIOUtil {
	
	private static Logger logger = LoggerFactory.getLogger(FileIOUtil.class);

	public static boolean writeFile(InputStream from, String fullPathName) {
		OutputStream to = null;
		try {
			File file = new File(fullPathName);
			Files.createParentDirs(file);
			to = new FileOutputStream(file);
			ByteStreams.copy(from, to);
			return true;
		} catch (IOException e) {
			logger.error("File write error, filename = " + fullPathName, e);
			return false;
		} finally {
			if (to != null) {
				try {
					to.close();
				} catch (IOException e) {
									}
			}
		}

	}

	/**
	 * 检测文件图片是否非法
	 * 
	 * @param fullPathName
	 * @return
	 */
	public static boolean isLegalImageFileExt(String fullPathName) {
		String ext = Files.getFileExtension(fullPathName);
		if (StringUtil.isNotNull(ext)) {
			ext = ext.toLowerCase();
			return AppConstant.IMAGE_FILE_EXT.contains(ext);
		} else {
			return false;
		}
	}
	
	/**
	 * 图片压缩
	 * @param is 输入流
	 * @param width 宽度
	 * @param height 高度
	 * @throws IOException
	 */
	public static InputStream imageZoomOut(InputStream is,int width ,int height) throws IOException {
		BufferedImage bi = ImageIO.read(is) ;
		ScaleImageUtil scaleImageUtil = ScaleImageUtil.getInstance();
		BufferedImage zoomBi = scaleImageUtil.imageZoomOut(bi, width, height);
        return bufferedImageToInoutStream(zoomBi);
	}
	
	/**
	 * 图片剪切
	 * @param is 输入流
	 * @param x 开始点的x坐标
	 * @param y 开始点的Y坐标
	 * @param width 截取的宽度
	 * @param height 截取的高度
	 * @param fileExtension 文件后缀
	 * @return
	 * @throws IOException 
	 */
	public static InputStream imageCrop(InputStream is,int x,int y , int width,int height,String fileExtension) throws IOException {
		// 文件裁剪
		BufferedImage bi = ImageUtil.readUsingImageReader(is, x, y, width, height, fileExtension);
		
		return bufferedImageToInoutStream(bi);
	}
	
	/**
	 * 将BufferedImage转化成InputStream
	 * @throws IOException
	 */
	public static InputStream bufferedImageToInoutStream(BufferedImage bufferedImage) throws IOException {
		InputStream is = null; 
		ByteArrayOutputStream bs = new ByteArrayOutputStream();  
        
        ImageOutputStream imOut = ImageIO.createImageOutputStream(bs); 
             
        ImageIO.write(bufferedImage, "png",imOut); 
             
        is= new ByteArrayInputStream(bs.toByteArray()); 
             
        return is; 

	}

	public static final int FILE_TYPE_DOC = 1;
	public static final int FILE_TYPE_XLS = 2;
	public static final int FILE_TYPE_PPT = 3;
	public static final int FILE_TYPE_TXT = 4;
	public static final int FILE_TYPE_WPS = 5;
	public static final int FILE_TYPE_ZIP = 6;
	public static final int FILE_TYPE_DEF = 7;
	public static final int FILE_TYPE_PDF = 8;
	public static final Map<String,Integer> fileTypes = new HashMap<String,Integer>();
	static {
		fileTypes.put("doc", FILE_TYPE_DOC);
		fileTypes.put("txt", FILE_TYPE_TXT);
		fileTypes.put("wps", FILE_TYPE_WPS);
		fileTypes.put("xls", FILE_TYPE_XLS);
		fileTypes.put("ppt", FILE_TYPE_PPT);
		fileTypes.put("rar", FILE_TYPE_ZIP);
		fileTypes.put("docx", FILE_TYPE_DOC);
		fileTypes.put("xlsx", FILE_TYPE_XLS);
		fileTypes.put("pptx", FILE_TYPE_PPT);
		fileTypes.put("tar", FILE_TYPE_ZIP);
		fileTypes.put("zip", FILE_TYPE_ZIP);
		fileTypes.put("jar", FILE_TYPE_ZIP);
		fileTypes.put("pdf", FILE_TYPE_PDF);
	}
	
	public static int getFileExt(File file) {
		String fileName = file.getName();
		String ext = getFileExt(fileName);
	    if(StringUtil.isNotNull(ext)) {
	    	ext = ext.toLowerCase();
	    	Integer type = fileTypes.get(ext);
	    	if(type != null && type > 0) {
	    		return type;
	    	}
	    }
		return FILE_TYPE_DEF;
	}

	public static String getFileExt(String fileName) {
		int dotIndex = fileName.lastIndexOf('.');
	    String ext = (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
		return ext;
	}
	
}