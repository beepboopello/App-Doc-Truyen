package com.example.appdoctruyen.admin.model;

import java.io.Serializable;

public class ItemStatisticRview implements Serializable {
    private String date,price,month,year;
    private String userid;

    public ItemStatisticRview(String date, String price) {
        this.date = date;
        this.price = price;
        this.month=" ";
        this.year=" ";
        this.userid=" ";
    }

    public ItemStatisticRview(String userid, String date, String price) {
        this.date = date;
        this.price = price;
        this.userid = userid;
        this.month=" ";
        this.year=" ";
    }

    public ItemStatisticRview( String date,String month, String year, String price) {
        this.date = date;
        this.price = price;
        this.month = month;
        this.year = year;
        this.userid=" ";
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}