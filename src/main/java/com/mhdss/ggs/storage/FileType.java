package com.mhdss.ggs.storage;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

public enum FileType {

    JPEG((byte) 1, "jpeg", "jpeg"),
    PNG((byte) 2, "png", "png"),
    JPG((byte) 3, "jpg", "jpg"),
    GIF((byte) 4, "gif", "gif"),
    TMP((byte) 5, "tmp", ""),
    JSON((byte) 6, "json", "json"),
    MGJSON((byte) 7, "mgjson", "mgjson"),
    ZIP((byte) 8, "zip", "zip"),
    OTHER((byte) 0, "oth", ""),


    ERROR((byte) -1, "", "");

    private final byte code;
    private final String name;
    private final String ext;

    FileType(final byte code, final String name, final String ext) {
        this.code = code;
        this.name = name;
        this.ext = ext;
    }

    public static FileType resolveJSONType(FileType type, String filePath) {
        if (JSON == type || MGJSON == type) {
            return type;
        } else {
            FileType resolvedType = FileType.getTypeByExt(FilenameUtils.getExtension(filePath));
            if (JSON == resolvedType || MGJSON == resolvedType) {
                return resolvedType;
            } else {
                throw new StorageException("file type error");
            }
        }
    }

    /**
     * GET时检查
     */
    public static boolean checkTypeIsJSONType(FileType type, String filePath) {
        return checkTypeIsJSONType(type) || checkTypeIsJSONType(FileType.getTypeByExt(FilenameUtils.getExtension(filePath)));
    }

    /**
     * PUT时检查
     */
    public static boolean checkTypeIsJSONType(FileType type) {
        return type != null
                && (type == FileType.JSON || type == FileType.MGJSON);
    }

    public static FileType getType(byte c) {
        for (FileType t : FileType.values()) {
            if (t.code == c) {
                return t;
            }
        }
        return FileType.OTHER;
    }

    public static FileType getType(String name) {
        for (FileType t : FileType.values()) {
            if (StringUtils.equals(t.name, name)) {
                return t;
            }
        }
        return FileType.OTHER;
    }

    public static FileType getTypeByExt(String ext) {
        for (FileType t : FileType.values()) {
            if (StringUtils.equalsIgnoreCase(t.ext, ext)) {
                return t;
            }
        }
        return FileType.OTHER;
    }

    public byte getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getExt() {
        return ext;
    }
}
