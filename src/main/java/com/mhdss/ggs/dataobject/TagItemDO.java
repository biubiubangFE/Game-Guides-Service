package com.mhdss.ggs.dataobject;

public class TagItemDO extends BaseDO {
    private Long tagId;

    private String itemName;

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}