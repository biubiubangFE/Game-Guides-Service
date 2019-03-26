package com.mhdss.ggs.constant;

public enum NewsShowType {

    HTML((byte) 0, "html文件"),
    CONTENT((byte) 1, "内容");

    private final Byte type;
    private final String desc;

    NewsShowType(Byte type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static NewsShowType getType(Byte status) {

        for (NewsShowType baseStatus : NewsShowType.values()) {
            if (status.equals(baseStatus.getType())) {
                return baseStatus;
            }
        }
        throw new IllegalArgumentException();
    }

    public Byte getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
