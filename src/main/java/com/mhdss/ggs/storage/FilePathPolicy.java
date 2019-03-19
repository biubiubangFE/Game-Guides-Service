package com.mhdss.ggs.storage;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.mhdss.ggs.utils.PathUtil;


public abstract class FilePathPolicy {

    public String generateFilePath(FileType type, File file) throws Exception {
        throw new UnsupportedOperationException();
    }

    protected String generateFilePath(FileType type) throws Exception {
        String rand = String.valueOf(Math.abs(new Random(System.currentTimeMillis()).nextInt()));

        String uuid = StringUtils.replaceAll(UUID.randomUUID().toString(), "-", "");

        String filename = StringUtils.joinWith("_", System.currentTimeMillis(), uuid, rand);

        filename = StringUtils.joinWith(".", filename, type.getName());

        String filePath = PathUtil.concatPaths(new SimpleDateFormat("yyyy/MM/dd").format(new Date()), filename);

        return filePath;
    }

    protected String generateFilePath(String filename) {
        String uuid = StringUtils.joinWith("_", System.currentTimeMillis(),
                StringUtils.replaceAll(UUID.randomUUID().toString(), "-", ""),
                String.valueOf(Math.abs(new Random(System.currentTimeMillis()).nextInt())));
        String filePath = PathUtil.concatPaths(new SimpleDateFormat("yyyy/MM/dd").format(new Date()), uuid, filename);
        return filePath;
    }
}
