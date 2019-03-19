package com.mhdss.ggs.storage;

import com.alibaba.fastjson.JSON;
import com.mhdss.ggs.utils.Asserts;
import com.mhdss.ggs.utils.ByteUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;

import static com.mhdss.ggs.utils.Asserts.assertNotNull;
import static com.mhdss.ggs.utils.Asserts.assertStringNotBlank;

@Component
public class FileStorage implements InitializingBean {

    private FilePathPolicy filePathPolicy = new DefaultFilePathPolicy();

    @Value(value = "${storage.local.endpoint:''}")
    private String localStorageEndpoint;
    @Autowired(required = false)
    private Storage storage;
    @Autowired
    private TempFileStorage tempFileStorage;

    @Override
    public void afterPropertiesSet() {
        Asserts.assertNotNull(tempFileStorage);
    }

    public InputStream getFile(FileType type, String filePath) {
        assertNotNull(type, filePath);
        try {
            return storage.getFile(type.getName(), filePath);
        } catch (Exception e) {
            throw new StorageException(e);
        }
    }

    public void getFile(FileType type, String filePath, File file) {
        assertNotNull(type, filePath, file);
        if (!file.exists() || file.isDirectory()) {
            throw new StorageException("file error");
        }
        try {
            storage.getFile(type.getName(), filePath, file);
        } catch (Exception e) {
            throw new StorageException(e);
        }
    }

    public String putFile(FileType type, File file, FilePathPolicy filePathPolicy) {
        assertNotNull(type, file);
        if (filePathPolicy == null) {
            filePathPolicy = this.filePathPolicy;
        }
        if (!file.exists() || file.isDirectory()) {
            throw new StorageException("file error");
        }
        try {
            String filePath = filePathPolicy.generateFilePath(type, file);
            storage.putFile(type.getName(), file, filePath);
            return filePath;
        } catch (Exception e) {
            throw new StorageException(e);
        }
    }

    public String putFile(FileType type, File file) {
        return putFile(type, file, filePathPolicy);
    }

    public String putFileContent(FileType type, String content) {
        assertNotNull(type, content);
        if (StringUtils.isEmpty(content)) {
            throw new StorageException("file content error");
        }
        try {
            String filePath = filePathPolicy.generateFilePath(type);
            storage.putFileContent(type.getName(), content, filePath);
            return filePath;
        } catch (Exception e) {
            throw new StorageException(e);
        }
    }

    public String copyFile(FileType type, String filePath) {
        assertNotNull(type, filePath);
        if (!checkFileExists(type, filePath)) {
            throw new StorageException("filePath error");
        }
        try {
            String newFilePath = filePathPolicy.generateFilePath(type);
            storage.copyFile(type.getName(), filePath, newFilePath);
            return newFilePath;
        } catch (Exception e) {
            throw new StorageException(e);
        }
    }

    public String copyFile(FileType srcType, String srcFilePath, FileType destType) {
        assertNotNull(srcType, srcFilePath, destType);
        if (!checkFileExists(srcType, srcFilePath)) {
            throw new StorageException("filePath error");
        }
        try {
            String newFilePath = filePathPolicy.generateFilePath(srcType);
            storage.copyFile(srcType.getName(), srcFilePath, destType.getName(), newFilePath);
            return newFilePath;
        } catch (Exception e) {
            throw new StorageException(e);
        }
    }

    public String getFileUrl(FileType type, String filePath) {
        assertNotNull(type, filePath);
        return storage.getFileUrl(type.getName(), filePath);
    }

    public boolean checkFileExists(FileType type, String filePath) {
        assertNotNull(type, filePath);
        try {
            return storage.checkFileExists(type.getName(), filePath);
        } catch (Exception e) {
            throw new StorageException(e);
        }
    }

    public void deleteFile(FileType type, String filePath) {
        assertNotNull(type, filePath);
        try {
            storage.deleteFile(type.getName(), filePath);
        } catch (Exception e) {
            throw new StorageException(e);
        }
    }

    private String putInputStreamAsJSONBytes(FileType type, InputStream inputStream) {
        try {
            byte bytes[] = null;
            if (type == FileType.JSON) {
                bytes = ByteUtil.inputStreamToByte(inputStream);
            } else if (type == FileType.MGJSON) {
                bytes = ByteUtil.zipBytes(ByteUtil.inputStreamToByte(inputStream));
            }
            return putBytes(type, bytes);
        } catch (Exception e) {
            throw new StorageException(e);
        }
    }

    public String putObjectAsJSONBytes(FileType type, Object object) {
        assertNotNull(type, object);
        if (!FileType.checkTypeIsJSONType(type)) {
            throw new StorageException("file type error");
        }
        if (object instanceof InputStream) {
            return putInputStreamAsJSONBytes(type, (InputStream) object);
        }
        try {
            byte bytes[] = null;
            if (type == FileType.JSON) {
                bytes = JSON.toJSONBytes(object);
            } else if (type == FileType.MGJSON) {
                bytes = ByteUtil.zipBytes(JSON.toJSONBytes(object));
            }
            return putBytes(type, bytes);
        } catch (Exception e) {
            throw new StorageException(e);
        }
    }

    public byte[] getObjectJSONBytes(FileType type, String filePath) {
        assertNotNull(type, filePath);
        if (!FileType.checkTypeIsJSONType(type, filePath)) {
            throw new StorageException("file type error");
        }
        try {

            FileType resolvedType = FileType.resolveJSONType(type, filePath);

            byte[] bytes = null;
            if (FileType.JSON == resolvedType) {
                bytes = storage.getBytes(type.getName(), filePath);
            } else if (FileType.MGJSON == resolvedType) {
                bytes = ByteUtil.unzipBytes(storage.getFile(type.getName(), filePath));
            }
            return bytes;
        } catch (Exception e) {
            throw new StorageException(e);
        }
    }

    public String putBytes(FileType type, byte bytes[]) {
        assertNotNull(type, bytes);
        try {
            String filePath = filePathPolicy.generateFilePath(type);
            storage.putBytes(type.getName(), bytes, filePath);
            return filePath;
        } catch (Exception e) {
            throw new StorageException(e);
        }
    }

    public String putFile(FileType type, File file, String filename) {
        assertNotNull(type, file, filename);
        assertStringNotBlank(filename);
        if (!file.exists() || file.isDirectory()) {
            throw new StorageException("file error");
        }
        try {
            String filePath = filePathPolicy.generateFilePath(filename);
            storage.putFile(type.getName(), file, filePath);
            return filePath;
        } catch (Exception e) {
            throw new StorageException(e);
        }
    }

    public boolean isLocalMode() {
        return storage.isLocalMode();
    }

    public TempFileStorage getTempFileStorage() {
        return tempFileStorage;
    }

    public String getLocalStorageEndpoint() {
        return localStorageEndpoint;
    }

}
