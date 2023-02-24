package com.synchrony.springboot.model;

public class ImgurData {
    private String id;
    private String title;
    private String description;
    private String type;
    private String link;
    private String deletehash;
    private int width;
    private int height;

    

    public ImgurData(String id, String title, String description, String type, String link, String deletehash,
            int width, int height) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.type = type;
        this.link = link;
        this.deletehash = deletehash;
        this.width = width;
        this.height = height;
    }

    public ImgurData() {
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
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
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public String getDeletehash() {
        return deletehash;
    }
    public void setDeletehash(String deletehash) {
        this.deletehash = deletehash;
    }
    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    @Override
    public String toString() {
        return "ImgurData [id=" + id + ", title=" + title + ", description=" + description + ", type=" + type
                + ", link=" + link + ", deletehash=" + deletehash + ", width=" + width + ", height=" + height + "]";
    }
}