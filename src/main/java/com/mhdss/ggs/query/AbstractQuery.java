package com.mhdss.ggs.query;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public abstract class AbstractQuery {
    private static final int QUERY_MAX_LIMIT = 200;

    private int page;

    private int pageSize;

    private int start;

    private int range;

    public void checkValidity() {
        
    }

    public final void setPagination(int page, int pageSize) {
        Preconditions.checkArgument(page > 0, "page");
        Preconditions.checkArgument(pageSize > 0 && pageSize <= QUERY_MAX_LIMIT, "pageSize");
        
        this.page = page;
        this.pageSize = pageSize;
        
        this.start = (page - 1) * pageSize;
        this.range = pageSize;
    }

    public final int getPage() {
        return page;
    }

    public final int getPageSize() {
        return pageSize;
    }

    public final int getStart() {
        return start;
    }

    public final int getRange() {
        return range;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
    }
}