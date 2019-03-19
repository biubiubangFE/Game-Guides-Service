package com.mhdss.ggs.constant;

public enum BaseStatus {

    NORMAL((byte) 0, "正常"),
    DELETE((byte) 1, "删除"),
    INVALID((byte) 2, "失效");

    private final Byte status;
    private final String desc;

    BaseStatus(Byte status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public static BaseStatus getStatus(Byte status) {

        for (BaseStatus baseStatus : BaseStatus.values()) {
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
