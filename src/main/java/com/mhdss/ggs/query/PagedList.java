package com.mhdss.ggs.query;

import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class PagedList<T> {
    private int total;

    private int totalPage;

    private int page;

    private int pageSize;

    private List<T> source;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getSource() {
        return source;
    }

    public void setSource(List<T> source) {
        this.source = source;
    }

    @Override
    public String toString() {
        ToStringBuilder toStringBuilder = new ToStringBuilder(this);
        toStringBuilder.append("total", total);
        toStringBuilder.append("totalPage", totalPage);
        toStringBuilder.append("page", page);
        toStringBuilder.append("pageSize", pageSize);
        int sourceSize = (source != null) ? source.size() : 0;
        toStringBuilder.append("sourceSize", sourceSize);
        toStringBuilder.append("source", source, true);
        return toStringBuilder.toString();
    }
}