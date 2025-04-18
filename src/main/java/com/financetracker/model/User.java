package com.financetracker.model;

import java.sql.Timestamp;

public class User {
    private int userId;
    private String username;
    private String email;
    private String password;
    private Timestamp createdAt;
    private String salt;

    public User(){
        this.userId = 0;
        this.username = "";
        this.email = "";
        this.password = "";
        this.salt = "";
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    public User(String username, String email, String password,String salt) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.salt = salt;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    } 
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getSalt(){
        return salt;
    }
    public void setSalt(String salt){
        this.salt = salt;
    }
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

}
