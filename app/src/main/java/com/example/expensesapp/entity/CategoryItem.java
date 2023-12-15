package com.example.expensesapp.entity;

public class CategoryItem extends FirebaseKey {

    private String title;
    private double price;

    public CategoryItem() {

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

}
