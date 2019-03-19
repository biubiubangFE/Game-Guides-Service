package com.mhdss.ggs.dataobject;

public class TagDO extends BaseDO {
    private String name;

    private Byte tagType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getTagType() {
        return tagType;
    }

    public void setTagType(Byte tagType) {
        this.tagType = tagType;
    }
}