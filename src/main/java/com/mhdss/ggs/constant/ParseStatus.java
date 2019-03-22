package com.mhdss.ggs.constant;

public enum  ParseStatus {

    WAIT((byte) 0, "等待"),
    RUN((byte) 1, "执行"),
    SUCCESS((byte) 2, "成功"),
    ERROR((byte) 2, "失败");

    private final Byte status;
    private final String desc;

    ParseStatus(Byte status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public static ParseStatus getStatus(Byte status) {

        for (ParseStatus baseStatus : ParseStatus.values()) {
            if (status.equals(baseStatus.getStatus())) {
                return baseStatus;
            }
        }
        throw new IllegalArgumentException();
    }

    public Byte getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }
}
