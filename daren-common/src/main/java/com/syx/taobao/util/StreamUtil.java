package com.syx.taobao.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamUtil {
	
	public static ByteArrayOutputStream inputStreamCache(InputStream in) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len;
		while ((len = in.read(buffer)) > -1) {
			byteArrayOutputStream.write(buffer, 0, len);
		}
		byteArrayOutputStream.flush();
		return byteArrayOutputStream;
	}

	public static InputStream getInputStream(ByteArrayOutputStream byteArrayOutputStream) {
		if (byteArrayOutputStream == null) {
			return null;
		}
		return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
	}
}
