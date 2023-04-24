package com.example.appdoctruyen.model;

import java.io.Serializable;

public class Genre implements Serializable {
    String id,name,description, created_at, update_at;

    public Genre(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }


    public Genre(String name) {
        this.name = name;
    }
    public Genre() {
    }

    public Genre(String id, String name, String description, String created_at, String update_at) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.created_at = created_at;
        this.update_at = update_at;
    }

    public Genre(String name, String description, String created_at, String update_at) {
        this.name = name;
        this.description = description;
        this.created_at = created_at;
        this.update_at = update_at;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(String update_at) {
        this.update_at = update_at;
    }
}
