package com.mhdss.ggs.dto;

public class AuthAgent {

    private long wxUserId;

    private String ip;

    public long getWxUserId() {
        return wxUserId;
    }

    public void setWxUserId(long wxUserId) {
        this.wxUserId = wxUserId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
