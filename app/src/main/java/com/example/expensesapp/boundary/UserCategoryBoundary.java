package com.example.expensesapp.boundary;

import com.example.expensesapp.entity.CategoryEntity;
import com.example.expensesapp.entity.UserCategory;

import java.io.Serializable;

public class UserCategoryBoundary extends UserCategory implements Serializable {
    private CategoryEntity categoryEntity;

    public UserCategoryBoundary() {}
    public CategoryEntity getCategoryEntity() {
        return categoryEntity;
    }

    public UserCategoryBoundary setCategoryEntity(CategoryEntity categoryEntity) {
        this.categoryEntity = categoryEntity;
        return this;
    }
}
