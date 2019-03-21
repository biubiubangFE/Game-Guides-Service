package com.mhdss.ggs.dataobject;

public class ResourceDO extends BaseDO {
    private String title;

    private String shareUrl;

    private String description;

    private String tag;

    private Long timestamp;

    private String imgs;

    private String author;

    private Byte parseStatus;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Byte getParseStatus() {
        return parseStatus;
    }

    public void setParseStatus(Byte parseStatus) {
        this.parseStatus = parseStatus;
    }
}