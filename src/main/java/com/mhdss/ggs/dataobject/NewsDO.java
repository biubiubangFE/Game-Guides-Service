package com.mhdss.ggs.dataobject;

public class NewsDO extends BaseDO {
    private String title;

    private String description;

    private String author;

    private String copyFrom;

    private Integer hitNum;

    private Integer postNum;

    private Integer likeNum;

    private String htmlPath;

    private String thumpPath;

    private Long timestamp;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCopyFrom() {
        return copyFrom;
    }

    public void setCopyFrom(String copyFrom) {
        this.copyFrom = copyFrom;
    }

    public Integer getHitNum() {
        return hitNum;
    }

    public void setHitNum(Integer hitNum) {
        this.hitNum = hitNum;
    }

    public Integer getPostNum() {
        return postNum;
    }

    public void setPostNum(Integer postNum) {
        this.postNum = postNum;
    }

    public Integer getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public String getHtmlPath() {
        return htmlPath;
    }

    public void setHtmlPath(String htmlPath) {
        this.htmlPath = htmlPath;
    }

    public String getThumpPath() {
        return thumpPath;
    }

    public void setThumpPath(String thumpPath) {
        this.thumpPath = thumpPath;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}