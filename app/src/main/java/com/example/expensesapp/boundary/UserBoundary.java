package com.example.expensesapp.boundary;

import com.example.expensesapp.entity.UserEntity;

import java.io.Serializable;
import java.util.ArrayList;

public class UserBoundary extends UserEntity implements Serializable {

    private ArrayList<UserCategoryBoundary> userCategories;

    public UserBoundary() {}

    public UserBoundary setUserCategories(ArrayList<UserCategoryBoundary> userCategories) {
        this.userCategories = userCategories;
        return this;
    }

    public ArrayList<UserCategoryBoundary> getUserCategories() {
        return userCategories;
    }

}
