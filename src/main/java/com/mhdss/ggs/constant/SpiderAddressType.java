package com.mhdss.ggs.constant;

public enum SpiderAddressType {

    XIAOHEIHEZIZOUQI((byte) 0, "https://api.xiaoheihe.cn", "正常");

    private final Byte type;
    private final String address;
    private final String desc;

    SpiderAddressType(Byte type, String address, String desc) {
        this.type = type;
        this.address = address;
        this.desc = desc;
    }

    public static SpiderAddressType getAddress(String address) {

        for (SpiderAddressType type : SpiderAddressType.values()) {
            if (address.equals(type.getAddress())) {
                return type;
            }
        }
        throw new IllegalArgumentException();
    }

    public static SpiderAddressType getType(Byte type) {

        for (SpiderAddressType addressType : SpiderAddressType.values()) {
            if (type.equals(addressType.getType())) {
                return addressType;
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

    public String getAddress() {
        return address;
    }
}
