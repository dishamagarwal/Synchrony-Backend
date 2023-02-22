package com.synchrony.backend.user;

public class User {
    // using Long to make it an object so it can be nullable
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String username;
    private String password;

    public User() {
    }
    
    // public User(String username, String password) {
    //     this.username = username;
    //     this.password = password;
    // }

    public User(String firstName, String lastName, String phoneNumber, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.password = password;
    }

    public User(Long id, String firstName, String lastName, String phoneNumber, String username, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User [" + 
        "id=" + id + 
        ", firstName=" + firstName + 
        ", lastName=" + lastName + 
        ", phoneNumber=" + phoneNumber + 
        ", username=" + username + 
        ", password=" + password + 
        "]";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
}
