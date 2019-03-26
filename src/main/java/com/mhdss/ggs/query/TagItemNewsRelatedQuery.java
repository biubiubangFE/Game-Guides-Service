package com.mhdss.ggs.query;

import java.util.List;

public class TagItemNewsRelatedQuery extends AbstractQuery {

    private List<Long> tagItemIds;

    public List<Long> getTagItemIds() {
        return tagItemIds;
    }

    public void setTagItemIds(List<Long> tagItemIds) {
        this.tagItemIds = tagItemIds;
    }
}