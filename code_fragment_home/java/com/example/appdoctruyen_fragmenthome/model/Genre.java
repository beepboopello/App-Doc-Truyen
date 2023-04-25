package com.example.appdoctruyen_fragmenthome.model;

import java.io.Serializable;

public class Genre implements Serializable {
    String name;

    public Genre(String name) {
        this.name = name;
    }

    public Genre() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
