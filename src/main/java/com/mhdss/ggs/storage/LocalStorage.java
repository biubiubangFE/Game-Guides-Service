package com.mhdss.ggs.storage;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.mhdss.ggs.utils.PathUtil;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;

import static com.mhdss.ggs.utils.Asserts.assertStringNotBlank;

@Component
@ConfigurationProperties(prefix = "storage.local")
@ConditionalOnProperty(value = "mode", havingValue = "local")
public class LocalStorage implements InitializingBean, Storage {

    private String storageRoot;
    private String fileUrlBase;
    private String endpoint;

    @Override
    public void afterPropertiesSet() {
        assertStringNotBlank(storageRoot, fileUrlBase);
        assertStringNotBlank(endpoint);
    }

    @Override
    public InputStream getFile(String type, String filePath) throws Exception {
        String root = getFileTypeRoot(type);
        File file = new File(root, filePath);
        return FileUtils.openInputStream(file);
    }

    @Override
    public void getFile(String type, String filePath, File destFile) throws Exception {
        String root = getFileTypeRoot(type);
        File srcFile = new File(root, filePath);
        FileUtils.copyFile(srcFile, destFile);
    }

    @Override
    public void putFile(String type, File file, String destFilePath) throws Exception {
        String root = getFileTypeRoot(type);
        File destFile = new File(root, destFilePath);
        FileUtils.copyFile(file, destFile);
    }

    @Override
    public void putFileContent(String type, String content, String destFilePath) throws Exception {
        String root = getFileTypeRoot(type);
        File destFile = new File(root, destFilePath);
        FileUtils.writeStringToFile(destFile, content, Charset.defaultCharset());
    }

    @Override
    public void copyFile(String type, String srcFilePath, String destFilePath) throws Exception {
        String root = getFileTypeRoot(type);
        File srcFile = new File(root, srcFilePath);
        File destFile = new File(root, destFilePath);
        FileUtils.copyFile(srcFile, destFile);
    }

    @Override
    public void copyFile(String srcType, String srcFilePath, String destType, String destFilePath) throws Exception {
        String srcRoot = getFileTypeRoot(srcType);
        String destRoot = getFileTypeRoot(destType);
        File srcFile = new File(srcRoot, srcFilePath);
        File destFile = new File(destRoot, destFilePath);
        FileUtils.copyFile(srcFile, destFile);
    }

    @Override
    public boolean checkFileExists(String type, String filePath) throws Exception {
        String root = getFileTypeRoot(type);
        return new File(root, filePath).exists();
    }

    @Override
    public void deleteFile(String type, String filePath) throws Exception {
        String root = getFileTypeRoot(type);
        FileUtils.deleteQuietly(new File(root, filePath));
    }

    @Override
    public void putBytes(String type, byte[] bytes, String destFilePath) throws Exception {
        String root = getFileTypeRoot(type);
        File file = new File(root, destFilePath);
        FileUtils.writeByteArrayToFile(file, bytes);
    }

    @Override
    public byte[] getBytes(String type, String destFilePath) throws Exception {
        String root = getFileTypeRoot(type);
        File file = new File(root, destFilePath);
        return FileUtils.readFileToByteArray(file);
    }

    @Override
    public boolean isLocalMode() {
        return true;
    }

    @Override
    public String getFileUrl(String type, String filePath) {
        return PathUtil.concatPaths(endpoint, fileUrlBase, type, filePath);
    }

    private String getFileTypeRoot(String type) {
        return PathUtil.concatPaths(this.storageRoot, type);
    }

    public String getStorageRoot() {
        return storageRoot;
    }

    public void setStorageRoot(String storageRoot) {
        this.storageRoot = storageRoot;
    }

    public String getFileUrlBase() {
        return fileUrlBase;
    }

    public void setFileUrlBase(String fileUrlBase) {
        this.fileUrlBase = fileUrlBase;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
}
