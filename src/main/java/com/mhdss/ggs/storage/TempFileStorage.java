package com.mhdss.ggs.storage;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mhdss.ggs.utils.PathUtil;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

@Component
public class TempFileStorage {

	@Value(value = "${storage.tempStorageRoot}")
	private String storageRoot;

	private String getRandomFilename() {
		String rand = String.valueOf(Math.abs(new Random(System.currentTimeMillis()).nextInt()));

		String uuid = StringUtils.replaceAll(UUID.randomUUID().toString(), "-", "");

		String filename = StringUtils.joinWith("_", System.currentTimeMillis(), uuid, rand);

		String filePath = PathUtil.concatPaths(this.storageRoot,
				new SimpleDateFormat("yyyy/MM/dd").format(new Date()), filename);

		return filePath;
	}

	public File touchFile() throws IOException {
		String filename = getRandomFilename();
		File file = new File(filename);
		FileUtils.touch(file);
		return file;
	}

	public File randomFile() {
		String filename = getRandomFilename();
		return new File(filename);
	}

	public File touchDir() throws IOException {
		String filename = getRandomFilename();
		File file = new File(filename);
		file.mkdirs();
		return file;
	}

	public void delete(File file) {
		FileUtils.deleteQuietly(file);
	}

	public void setStorageRoot(String storageRoot) {
		this.storageRoot = storageRoot;
	}
}
