package com.synchrony.springboot.model;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import jakarta.persistence.*;

@Entity
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "token")
    // @GeneratedValue(strategy = GenerationType.UUID)
    private String token;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    @PrePersist
    public void setExpirationDate() {
        this.expirationDate = LocalDateTime.now(ZoneOffset.UTC).plusDays(60);
    }
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Session() {
        this.token = UUID.randomUUID().toString();
        this.expirationDate = LocalDateTime.now(ZoneOffset.UTC).plusDays(60);
    }
    
    public Session(User user) {
        this.user = user;
        this.token = UUID.randomUUID().toString();
        this.expirationDate = LocalDateTime.now(ZoneOffset.UTC).plusDays(60);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public String getToken() {
        return token;
    }
}
