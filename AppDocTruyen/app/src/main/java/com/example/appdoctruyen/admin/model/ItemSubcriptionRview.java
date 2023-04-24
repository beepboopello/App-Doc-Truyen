package com.example.appdoctruyen.admin.model;

import java.io.Serializable;

public class ItemSubcriptionRview implements Serializable {
    private String month,price,id;

    public ItemSubcriptionRview(String id, String month, String price ) {
        this.id = id;
        this.month = month;
        this.price = price;
    }

    public String getMonth() {
        return month;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
