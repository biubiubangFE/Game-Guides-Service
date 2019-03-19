package com.mhdss.ggs.storage;

import java.io.File;
import java.io.InputStream;

public interface Storage {

	InputStream getFile(String type, String filePath) throws Exception;

	void getFile(String type, String filePath, File destFile) throws Exception;

	void putFile(String type, File file, String destFilePath) throws Exception;

	void putFileContent(String type, String content, String destFilePath) throws Exception;

	void copyFile(String type, String srcFilePath, String destFilePath) throws Exception;

	void copyFile(String srcType, String srcFilePath, String destType, String destFilePath) throws Exception;

	boolean checkFileExists(String type, String filePath) throws Exception;

	void deleteFile(String type, String filePath) throws Exception;

	String getFileUrl(String type, String filePath);

	void putBytes(String type, byte[] bytes, String destFilePath) throws Exception;

	byte[] getBytes(String type, String destFilePath) throws Exception;

	boolean isLocalMode();

}
