package com.example.appdoctruyen.model;

import java.io.Serializable;

public class Book implements Serializable {
    private String id,name,author,description,free;
    private int viewed,liked;

    public int getLiked() {
        return liked;
    }

    public void setLiked(int liked) {
        this.liked = liked;
    }

    public Book(String id, String name, String author, String description, String free, int viewed, int liked) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.description = description;
        this.free = free;
        this.viewed = viewed;
        this.liked = liked;
    }

    public Book(String id, String name, String author, String description, String free, int viewed) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.description = description;
        this.free = free;
        this.viewed = viewed;
    }

    public Book() {
    }

    public void setFree(String free) {
        this.free = free;
    }

    public int getViewed() {
        return viewed;
    }

    public void setViewed(int viewed) {
        this.viewed = viewed;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                ", free='" + free + '\'' +
                '}';
    }

    public Book(String id, String name, String author, String description, String free) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.description = description;
        this.free = free;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFree() {
        return free;
    }

    public void setFee(String fee) {
        this.free = fee;
    }

    public Book(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
