package com.example.expensesapp.entity;

import java.util.Date;

public class CategoryItem {

    private String title;
    private double price;
    private Date date;

    public CategoryItem() {
        this.date = new Date();
    }
    public String getTitle() {
        return title;
    }

    public CategoryItem setTitle(String title) {
        this.title = title;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public CategoryItem setPrice(double price) {
        this.price = price;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public CategoryItem setDate(Date d){
        this.date = d;
        return this;
    }
}
