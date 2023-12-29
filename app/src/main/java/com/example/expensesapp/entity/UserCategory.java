package com.example.expensesapp.entity;

import java.util.ArrayList;

public class UserCategory {
    private String categoryKey;
    private double maxPrice;
    private ArrayList<CategoryItem> categoryItems;
    
    public UserCategory() {
        this.maxPrice = 1000;
        categoryItems = new ArrayList<>();
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public String getCategoryKey() {
        return categoryKey;
    }

    public UserCategory setCategoryKey(String categoryKey) {
        this.categoryKey = categoryKey;
        return this;
    }

    public UserCategory setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
        return this;
    }
    
    public void addItem(CategoryItem categoryItem){
        this.categoryItems.add(categoryItem);
    }
    
    public void removeItem(CategoryItem categoryItem){
        this.categoryItems.remove(categoryItem);
    }

    public UserCategory setCategoryItems(ArrayList<CategoryItem> categoryItems) {
        this.categoryItems = categoryItems;
        return this;
    }

    public ArrayList<CategoryItem> getCategoryItems() {
        return categoryItems;
    }
}
