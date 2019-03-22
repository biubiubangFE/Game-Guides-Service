package com.mhdss.ggs.dto;

import com.mhdss.ggs.constant.SpiderAddressType;

public class SpiderResultDTO {

    //页面跳转目录
    private String shareUrl;

    private String description;

    private String tag;

    private Long timestamp;

    private String title;

    private String img;

    private String author;

    private SpiderAddressType spiderType;

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public SpiderAddressType getSpiderType() {
        return spiderType;
    }

    public void setSpiderType(SpiderAddressType spiderType) {
        this.spiderType = spiderType;
    }
}
