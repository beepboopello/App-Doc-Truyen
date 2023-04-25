package com.example.appdoctruyen_fragmenthome.model;

import java.io.Serializable;

public class Chap implements Serializable {
    String name;

    public Chap(String name) {
        this.name = name;
    }

    public Chap() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
