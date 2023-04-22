package com.example.appdoctruyen.admin.model;

import java.io.Serializable;

public class ItemStatisticRview implements Serializable {
    private String date,price;

    public ItemStatisticRview(String date, String price) {
        this.date = date;
        this.price = price;
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

    public void setPrice(String price) {
        this.price = price;
    }
}
