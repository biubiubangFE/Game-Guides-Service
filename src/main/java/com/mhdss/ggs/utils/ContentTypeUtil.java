package com.mhdss.ggs.utils;

import org.apache.commons.lang3.StringUtils;

import com.mhdss.ggs.storage.FileType;

import java.util.HashMap;
import java.util.Map;

public class ContentTypeUtil {

    private static final Map<String, FileType> types = new HashMap<>();

    static {
        types.put("image/jpeg", FileType.JPEG);
        types.put("image/jpeg", FileType.JPG);
        types.put("image/gif", FileType.GIF);
        types.put("image/png", FileType.PNG);
    }

    public static FileType getFileType(String contentType, FileType def) {
        if (StringUtils.isBlank(contentType)) {
            return def;
        }

        return types.getOrDefault(StringUtils.lowerCase(contentType), def);
    }
}