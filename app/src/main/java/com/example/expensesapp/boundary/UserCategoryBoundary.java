package com.example.expensesapp.boundary;

import com.example.expensesapp.entity.CategoryEntity;
import com.example.expensesapp.entity.UserCategory;

public class UserCategoryBoundary extends UserCategory {
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
