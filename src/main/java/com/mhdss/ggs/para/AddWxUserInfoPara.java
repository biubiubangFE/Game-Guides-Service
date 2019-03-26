package com.mhdss.ggs.para;

import org.apache.commons.lang3.StringUtils;

public class AddWxUserInfoPara {

    private String code;

    private String encryptedData;

    private String iv;

    public boolean check() {

        if (StringUtils.isNoneBlank(code, encryptedData, iv)) {
            return true;
        }
        return false;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }
}
