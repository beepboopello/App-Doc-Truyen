package com.example.appdoctruyen.model;

import java.io.Serializable;

public class Chap implements Serializable {
    int id;
    String name, views, number;

    public Chap() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Chap(int id, String name, String views, String number) {
        this.id = id;
        this.name = name;
        this.views = views;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
