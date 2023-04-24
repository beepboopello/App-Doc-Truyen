package com.example.appdoctruyen.model;

import java.io.Serializable;

public class Title implements Serializable {
    private String id;
    private String name, author, des, created_at, updated_at;
    private boolean fee;
    private int totalView;

    public Title() {
    }
    public Title(String id, String name, String author, String des, boolean fee) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.des = des;
        this.fee = fee;

    }
    public Title(String id, String name, String author, String des, String created_at, String updated_at, boolean fee, int totalView) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.des = des;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.fee = fee;
        this.totalView = totalView;
    }

    public Title(String name, String author, String des, String created_at, String updated_at, boolean fee, int totalView) {
        this.name = name;
        this.author = author;
        this.des = des;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.fee = fee;
        this.totalView = totalView;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public boolean isFee() {
        return fee;
    }

    public void setFee(boolean fee) {
        this.fee = fee;
    }

    public int getTotalView() {
        return totalView;
    }

    public void setTotalView(int totalView) {
        this.totalView = totalView;
    }
}
