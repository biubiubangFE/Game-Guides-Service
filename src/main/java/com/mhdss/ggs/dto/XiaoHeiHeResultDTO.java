package com.mhdss.ggs.dto;

import java.util.List;

public class XiaoHeiHeResultDTO {

    //页面跳转目录
    private String share_url;

    private String description;

    private String tag;

    private String timestamp;

    private String title;

    //1.不显示，2 显示
    private byte content_type;

    private List<String> imgs;

    private String author;

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte getContent_type() {
        return content_type;
    }

    public void setContent_type(byte content_type) {
        this.content_type = content_type;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
