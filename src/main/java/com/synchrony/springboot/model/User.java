package com.synchrony.springboot.model;

import jakarta.persistence.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "TBL_USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "firstName", nullable=true, length=45)
    String firstName;

    @Column(name = "lastName", nullable=true, length=45)
    String lastName;

    @Column(name = "phone", nullable=true, length=10, unique=true)
    String phone;
    
    @Column(name="username", nullable=false, length=45, unique=true)
    String username;

    @Column(name = "password", nullable=false, length=45)
    String password;

    @Column(name = "sessionID", nullable=true, length=50)
    String sessionID;

    @Column(name = "sessionExpiary", nullable=true, length=20)
    long sessionExpiary;

    public User() {

    }
    
    public User(String firstName, String lastName, String phone, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.phone = phone;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", username=" + username
                + ", password=" + password + "]";
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return firstName;
    }

    public void setName(String name) {
        this.firstName = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public long getSessionExpiary() {
        return sessionExpiary;
    }

    public void setSessionExpiary() {
        // expires in 10 mins
        this.sessionExpiary = System.currentTimeMillis() + 600000;
    }
}
