package com.example.appdoctruyen.admin.model;

import java.io.Serializable;

public class ItemSubcriptionRview implements Serializable {
    private String month,price;

    public ItemSubcriptionRview(String month, String price) {
        this.month = month;
        this.price = price;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
