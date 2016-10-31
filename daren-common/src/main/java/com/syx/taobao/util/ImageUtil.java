package com.syx.taobao.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class ImageUtil {

	private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);

	public static final String SMAIL_DIAGRAM_X = "smail_diagram_x";
	public static final String SMAIL_DIAGRAM_Y = "smail_diagram_y";
	public static final String SMAIL_DIAGRAM_W = "smail_diagram_w";
	public static final String SMAIL_DIAGRAM_H = "smail_diagram_h";

	/**
	 * 获得图片的字符数据
	 * 
	 * @throws IOException
	 */
	public static String getImageString(String url) throws IOException {

		if (url == null || url.equals("")) {
			return null;
		}
		String resultStr = null;
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod();
		try {
			URI base = new URI(url, false);
			method.setURI(base);
			int result = client.executeMethod(method);
			if (result == 200) {
				InputStream in = method.getResponseBodyAsStream();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = in.read(buffer)) != -1) {
					baos.write(buffer, 0, len);
				}
				BASE64Encoder encoder = new BASE64Encoder();
				resultStr = encoder.encode(baos.toByteArray());
			} else {
				throw new Exception("HTTP ERROR Status: " + method.getStatusCode() + ":" + method.getStatusText());
			}
		} catch (Exception e) {
			logger.error("imageUtil get Image String error! url= " + url, e);
		} finally {
			method.releaseConnection();
		}

		return resultStr;
	}

	/**
	 * 根据图片的字符数据创建图片
	 */
	public static String createImageByString(String str, File outFile) throws IOException {
		FileOutputStream fos = null;
		try {
			BASE64Decoder decoder = new BASE64Decoder();
			byte[] bytes = decoder.decodeBuffer(str);
			for (int i = 0; i < bytes.length; ++i) {
				if (bytes[i] < 0) {// 调整异常数据
					bytes[i] += 256;
				}
			}
			fos = new FileOutputStream(outFile);
			fos.write(bytes);
			fos.flush();
		} catch (Exception e) {
			logger.error("imageUtil create Image By String error!", e);
			throw new IOException(e);
		} finally {
			if (fos != null) {
				fos.close();
			}
		}
		return outFile.getAbsolutePath();
	}

	/**
	 * 图像文件裁剪
	 * 
	 * @param srcInputStream
	 *            源文件输入流
	 * @param descOutputStream
	 *            裁剪后的输出流
	 * @param x
	 *            裁剪的起始点的x坐标
	 * @param y
	 *            裁剪的起始点的y坐标
	 * @param weight
	 *            裁剪的宽度
	 * @param high
	 *            裁剪的高度
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	public static BufferedImage readUsingImageReader(InputStream srcInputStream, int x, int y, int weight, int high, String hz) throws IOException {

		// 取得图片读入器

		Iterator readers = ImageIO.getImageReadersByFormatName(hz);

		// System.out.println(readers);

		ImageReader reader = (ImageReader) readers.next();

		// System.out.println(reader);

		// 取得图片读入流

		InputStream source = srcInputStream;

		ImageInputStream iis = ImageIO.createImageInputStream(source);

		reader.setInput(iis, true);

		// 图片参数

		ImageReadParam param = reader.getDefaultReadParam();

		// int imageIndex = 0;

		// int half_width = reader.getWidth(imageIndex) / 2;

		// int half_height = reader.getHeight(imageIndex) / 2;

		// Rectangle rect = new Rectangle(60, 60, half_width, half_height);

		Rectangle rect = new Rectangle(x, y, weight, high);

		param.setSourceRegion(rect);

		BufferedImage bi = reader.read(0, param);

		// ImageIO.write(bi, "jpg", descOutputStream);
		return bi;

	}

	/**
	 * 图片剪切
	 * 
	 * @param input
	 *            source file
	 * @param targetFileName
	 *            full path file name
	 * @param x
	 *            retangle start x
	 * @param y
	 *            retangle start y
	 * @param width
	 * @param height
	 * @param def
	 *            是否使用默认的高按宽的1.5截取
	 * @return if true, process fail otherwise fail.
	 */
	public static boolean cutCompanyPicture(InputStream input, String targetFileName, int x, int y, int width, int height, boolean def) {
		FileOutputStream out = null;
		ByteArrayInputStream bais = null;
		try {

			/*
			 * byte[] pImageData = IOUtils.toByteArray(input); int
			 * imageDataLength = pImageData.length;
			 * if(pImageData[imageDataLength-2] != (byte)0xFF &&
			 * pImageData[imageDataLength-1] != (byte)0xD9){ byte[] aux = new
			 * byte[imageDataLength + 2]; System.arraycopy(pImageData, 0, aux,
			 * 0, imageDataLength); aux[imageDataLength] = (byte)0xFF;
			 * aux[imageDataLength + 1] = (byte)0xD9; pImageData = aux; } bais =
			 * new ByteArrayInputStream(pImageData);
			 */

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte bts[] = new byte[1024];
			int len = -1;
			while ((len = input.read(bts)) != -1) {
				bos.write(bts, 0, len);
			}

			input = new ByteArrayInputStream(bos.toByteArray());

			BufferedImage srcBufferImage = ImageIO.read(input);
			// System.out.println(" w " + srcBufferImage.getWidth() + " h " +
			// srcBufferImage.getHeight());
			BufferedImage scaledImage;
			ScaleImageUtil scaleImage = ScaleImageUtil.getInstance();
			int sWidth = srcBufferImage.getWidth();
			int sHeight = srcBufferImage.getHeight();

			if (width == 0) {
				width = 210;
			}

			if (height == 0) {
				height = 315;
			}
			int tw = (width >= sWidth) ? sWidth : width;
			int th = (height >= sHeight) ? sHeight : height;
			File file = new File(targetFileName);
			if (file.exists()) {
				boolean b = file.delete();
				if (!b) {
					// return false;
				}
			}
			double resizeTimes = (tw * 1.0) / (sWidth * 1.0);
			int w = (int) (sHeight * resizeTimes);
			int subH = w;
			if (th < subH && def) {
				subH = th;
			}
			scaledImage = scaleImage.imageZoomOut(srcBufferImage, tw, w);
			BufferedImage image = scaledImage.getSubimage(x, y, tw, subH);
			out = new FileOutputStream(file);
			ImageIO.write(image, "png", out);
			return true;
		} catch (IOException e) {
			logger.error("cut image error", e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (input != null) {
					input.close();
				}
				if (bais != null) {
					bais.close();
				}
			} catch (IOException e) {
				logger.error("cut image error", e);
			}
		}
		return false;
	}

	/**
	 * 给图片添加水印、可设置水印图片旋转角度
	 * 
	 * @param iconPath
	 *            水印图片路径
	 * @param srcImgIs
	 *            源图片
	 * @param targerPath
	 *            目标图片路径
	 * @param degree
	 *            水印图片旋转角度
	 * @param width
	 *            宽度(与左相比)
	 * @param height
	 *            高度(与顶相比)
	 * @param clarity
	 *            透明度(小于1的数)越接近0越透明
	 * @throws IOException
	 */
	public static void waterMarkImageByIcon(String iconPath, Image srcImg, FileOutputStream targerOs, Integer degree, Integer width, Integer height, float clarity) throws IOException {
		OutputStream os = null;
		try {
			int srcWidth = srcImg.getWidth(null);
			int srcHeight = srcImg.getHeight(null);

			BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null), srcImg.getHeight(null), BufferedImage.TYPE_INT_ARGB);
			// 得到画笔对象
			// Graphics g= buffImg.getGraphics();
			Graphics2D g = buffImg.createGraphics();

			// 设置对线段的锯齿状边缘处理
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);
			if (null != degree) {
				// 设置水印旋转
				g.rotate(Math.toRadians(degree), (double) buffImg.getWidth() / 2, (double) buffImg.getHeight() / 2);
			}

			// 水印图象的路径 水印一般为gif或者png的，这样可设置透明度
			ImageIcon imgIcon = new ImageIcon(iconPath);
			int iconWidth = imgIcon.getIconWidth();
			int iconHeight = imgIcon.getIconHeight();
			// 得到Image对象。
			Image img = imgIcon.getImage();
			float alpha = clarity; // 透明度
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));

			// 表示水印图片的位置
			if (width == -1 || height == -1) {
				height = srcHeight - iconHeight;
				width = srcWidth - iconWidth;
			}
			g.drawImage(img, width, height, null);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
			g.dispose();
			os = targerOs;
			// 生成图片
			ImageIO.write(buffImg, "PNG", os);
			// System.out.println("添加水印图片完成!");
		} catch (IOException e) {
			logger.error("waterMarkImageByIcon error", e);
			throw e;
		} finally {
			try {
				if (null != os)
					os.close();
			} catch (IOException e) {
				logger.error("waterMarkImageByIcon error", e);
				throw e;
			}
		}
	}

	/**
	 * 给图片添加水印、可设置水印图片旋转角度
	 * 
	 * @param logoText
	 *            水印文字
	 * @param srcImgPath
	 *            源图片路径
	 * @param targerPath
	 *            目标图片路径
	 * @param degree
	 *            水印图片旋转角度
	 * @param width
	 *            宽度(与左相比)
	 * @param height
	 *            高度(与顶相比)
	 * @param clarity
	 *            透明度(小于1的数)越接近0越透明
	 * @throws Exception
	 */
	public static void waterMarkByText(String logoText, String srcImgPath, String targerPath, Integer degree, Integer width, Integer height, Float clarity) throws Exception {
		// 主图片的路径
		InputStream is = null;
		OutputStream os = null;
		try {
			Image srcImg = ImageIO.read(new File(srcImgPath));

			BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null), srcImg.getHeight(null), BufferedImage.TYPE_INT_ARGB);

			// 得到画笔对象
			// Graphics g= buffImg.getGraphics();
			Graphics2D g = buffImg.createGraphics();

			// 设置对线段的锯齿状边缘处理
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

			g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);

			if (null != degree) {
				// 设置水印旋转
				g.rotate(Math.toRadians(degree), (double) buffImg.getWidth() / 2, (double) buffImg.getHeight() / 2);
			}

			// 设置颜色
			g.setColor(Color.red);

			// 设置 Font
			g.setFont(new Font("宋体", Font.BOLD, 30));

			float alpha = clarity;
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));

			// 第一参数->设置的内容，后面两个参数->文字在图片上的坐标位置(x,y) .
			g.drawString(logoText, width, height);

			g.dispose();

			os = new FileOutputStream(targerPath);

			// 生成图片
			ImageIO.write(buffImg, "JPG", os);

			System.out.println("添加水印文字完成!");
		} catch (Exception e) {
			logger.error("imageUtil water Mark By Text error!", e);
		} finally {
			try {
				if (null != is)
					is.close();
			} catch (IOException e) {
				logger.error("waterMarkImageByIcon error", e);
				throw e;
			}
			try {
				if (null != os)
					os.close();
			} catch (IOException e) {
				logger.error("waterMarkImageByIcon error", e);
				throw e;
			}
		}
	}

	/**
	 * 将背景为黑色不透明的图片转化为背景透明的图片
	 * 
	 * @param image
	 *            背景为黑色不透明的图片（用555格式转化后的都是黑色不透明的）
	 * @return 转化后的图片
	 */
	public static BufferedImage getConvertedImage(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();
		BufferedImage convertedImage = null;
		Graphics2D g2D = null;
		// 采用带1 字节alpha的TYPE_4BYTE_ABGR，可以修改像素的布尔透明
		convertedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		g2D = (Graphics2D) convertedImage.getGraphics();
		g2D.drawImage(image, 0, 0, null);
		// 像素替换，直接把背景颜色的像素替换成0
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int rgb = convertedImage.getRGB(i, j);
				if (isBackPixel(rgb)) {
					convertedImage.setRGB(i, j, 0);
				}
			}
		}
		g2D.drawImage(convertedImage, 0, 0, null);
		return convertedImage;
	}

	/**
	 * 判断当前像素是否为黑色不透明的像素（-16777216）
	 * 
	 * @param pixel
	 *            要判断的像素
	 * @return 是背景像素返回true，否则返回false
	 */
	private static boolean isBackPixel(int pixel) {
		int back[] = { -16777216 };
		for (int i = 0; i < back.length; i++) {
			if (back[i] == pixel)
				return true;
		}
		return false;
	}

}
