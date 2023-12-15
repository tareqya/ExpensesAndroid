package com.example.expensesapp.entity;

import com.google.firebase.database.Exclude;

public class CategoryEntity extends FirebaseKey {
    public static final String CATEGORY_TABLE = "Categories";
    private String name;
    private String imagePath;
    private String imageUrl;

    public CategoryEntity(String name, String image) {
        this.name = name;
        this.imagePath = image;
    }

    public CategoryEntity(){}

    public String getName() {
        return name;
    }

    public CategoryEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getImagePath() {
        return imagePath;
    }

    public CategoryEntity setImagePath(String image) {
        this.imagePath = image;
        return this;
    }


    @Exclude
    public String getImageUrl(){
        return this.imageUrl;
    }


    public CategoryEntity setImageUrl(String imageUrl){
        this.imageUrl = imageUrl ;
        return this;
    }

    public String toString(){
        return this.name;
    }

}
