package com.example.appdoctruyen.model;

import java.io.Serializable;

public class Viewed implements Serializable {
    String name;
    String date;

    public Viewed(String name, String date) {
        this.name = name;
        this.date = date;
    }

    public Viewed() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
