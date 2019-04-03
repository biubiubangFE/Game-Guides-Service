package com.mhdss.ggs.query;

import java.util.List;

public class NewsQuery extends AbstractQuery {

    private List<Long> newsIds;
    private Byte gameType;
    private Byte sortType;
    private String keyword;

    public List<Long> getNewsIds() {
        return newsIds;
    }

    public void setNewsIds(List<Long> newsIds) {
        this.newsIds = newsIds;
    }

    public Byte getGameType() {
        return gameType;
    }

    public void setGameType(Byte gameType) {
        this.gameType = gameType;
    }

    public Byte getSortType() {
        return sortType;
    }

    public void setSortType(Byte sortType) {
        this.sortType = sortType;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}