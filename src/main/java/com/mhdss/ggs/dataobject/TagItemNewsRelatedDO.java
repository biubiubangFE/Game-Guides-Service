package com.mhdss.ggs.dataobject;

public class TagItemNewsRelatedDO extends BaseDO {
    private Long tagItemId;

    private Long newsId;

    public Long getTagItemId() {
        return tagItemId;
    }

    public void setTagItemId(Long tagItemId) {
        this.tagItemId = tagItemId;
    }

    public Long getNewsId() {
        return newsId;
    }

    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }
}