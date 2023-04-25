package com.example.appdoctruyen_fragmenthome.model;

public class User {
    private int id, admin;
    private String username, email, token;

    public User(int id, int admin, String username, String email, String token) {
        this.id = id;
        this.admin = admin;
        this.username = username;
        this.email = email;
        this.token = token;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString(){
        return String.valueOf(id) + " " + (admin==0 ? "Is not admin " : "Is admin ") +username + " " + email + " " + token;
    }
}
