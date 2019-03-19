package com.mhdss.ggs.utils;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.zip.CRC32;

public class LKHash {
    public static String hash(String str) {
        CRC32 crc32 = new CRC32();
        crc32.update(str.getBytes());
        return concatDigests(DigestUtils.md5Hex(str),
                DigestUtils.sha1Hex(str),
                crc32.getValue());
    }

    public static String hash(File file) throws Exception {
        MessageDigest md5Digest = DigestUtils.getMd5Digest();
        MessageDigest sha1Digest = DigestUtils.getSha1Digest();
        CRC32 crc32 = new CRC32();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            byte[] bytes = new byte[1024];
            int len;
            while ((len = fis.read(bytes)) > 0) {
                md5Digest.update(bytes, 0, len);
                sha1Digest.update(bytes, 0, len);
                crc32.update(bytes, 0, len);
            }
        } finally {
            IOUtils.closeQuietly(fis);
        }
        return concatDigests(Hex.encodeHexString(md5Digest.digest()),
                Hex.encodeHexString(sha1Digest.digest()),
                crc32.getValue());
    }

    private static String concatDigests(String md5, String sha1, long crc32) {
        return new StringBuffer(md5)
                .append(sha1)
                .append(crc32ToHex(crc32))
                .toString();
    }

    private static String crc32ToHex(long crc32) {
        String hex = Long.toHexString(crc32).toUpperCase();
        int len = 16 - hex.length();
        if (len > 0) {
            char[] zeros = new char[len];
            Arrays.fill(zeros, '0');
            return new String(zeros) + hex;
        }
        return hex;
    }
}
