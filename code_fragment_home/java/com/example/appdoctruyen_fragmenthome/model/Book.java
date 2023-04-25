package com.example.appdoctruyen_fragmenthome.model;

import java.io.Serializable;

public class Book implements Serializable {
//    userid = models.BigIntegerField(default=0)
//    name=models.CharField(max_length=255)
//    author=models.CharField(max_length=255)
//    description=models.CharField(max_length=255)
//    fee=models.BooleanField()
//    created_at=models.DateTimeField()
//    updated_at=models.DateTimeField()
//    totalViews=models.IntegerField()
    private int id;
    private int userid;
    private String name;
    private String author;
    private String description;
    private boolean free;
    private String created_at;
    private String updated_at;
    private int totalViews;
    private int totalLikes;

    public Book(int id, int userid, String name, String author, String description, boolean free, String created_at, String updated_at, int totalViews, int totalLikes) {
        this.id = id;
        this.userid = userid;
        this.name = name;
        this.author = author;
        this.description = description;
        this.free = free;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.totalViews = totalViews;
        this.totalLikes = totalLikes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
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

    public boolean getFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
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

    public int getTotalViews() {
        return totalViews;
    }

    public void setTotalViews(int totalViews) {
        this.totalViews = totalViews;
    }

    public int getTotalLikes() {
        return totalLikes;
    }

    public void setTotalLikes(int totalLikes) {
        this.totalLikes = totalLikes;
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
