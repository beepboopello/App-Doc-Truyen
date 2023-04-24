package com.example.appdoctruyen.model;

public class Subscription {
    private int id,price, months;

    public Subscription(int id, int price, int months) {
        this.id = id;
        this.price = price;
        this.months = months;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getMonths() {
        return months;
    }

    public void setMonths(int months) {
        this.months = months;
    }
}
