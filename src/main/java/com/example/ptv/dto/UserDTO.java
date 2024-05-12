package com.example.ptv.dto;

public class UserDTO {
    private int userId;
    private String username;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
/**
     *
     */

    /**
     *
     */
    private String password;

    private int role;
    private String contactInfo;
    private String realIdentity;

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getRealIdentity() {
        return realIdentity;
    }

    public void setRealIdentity(String realIdentity) {
        this.realIdentity = realIdentity;
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

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
