package com.mhdss.ggs.query;

public class TagItemQuery extends AbstractQuery {

    private String itemName;

    private String searchTag;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getSearchTag() {
        return searchTag;
    }

    public void setSearchTag(String searchTag) {
        this.searchTag = searchTag;
    }
}