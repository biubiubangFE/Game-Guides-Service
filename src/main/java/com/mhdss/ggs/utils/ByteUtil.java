package com.mhdss.ggs.utils;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class ByteUtil {

	public static byte[] zipBytes(byte[] bytes) throws Exception {
		GZIPOutputStream gos = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			gos = new GZIPOutputStream(baos);
			gos.write(bytes);
			gos.flush();
			gos.finish();
			return baos.toByteArray();
		} finally {
			IOUtils.closeQuietly(gos);
		}
	}

	public static byte[] unzipBytes(InputStream is) throws Exception {
		GZIPInputStream gis = null;
		try {
			gis = new GZIPInputStream(is);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] buf = new byte[512];
			int len;
			while ((len = gis.read(buf)) > 0) {
				bos.write(buf, 0, len);
			}
			return bos.toByteArray();
		} finally {
			IOUtils.closeQuietly(gis);
		}
	}


	public static byte[] inputStreamToByte(InputStream inputStream) throws Exception {
		ByteArrayOutputStream bytestream = null;
		try {
			bytestream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int ch;
			while ((ch = inputStream.read(buffer)) != -1) {
				bytestream.write(buffer, 0, ch);
			}
			return bytestream.toByteArray();
		} finally {
			IOUtils.closeQuietly(bytestream);
		}
	}
}
