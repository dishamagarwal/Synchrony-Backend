package com.synchrony.springboot.model;

import jakarta.persistence.*;

@Entity
@Table(name = "images")
public class Image {
    @Id
    private String id;
    
    @Column(name = "url")
    private String url;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Image() {
    }

    public Image(String id, String url, User user) {
        this.id = id;
        this.url = url;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}