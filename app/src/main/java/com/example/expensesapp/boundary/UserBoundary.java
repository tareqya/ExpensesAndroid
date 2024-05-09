package com.example.expensesapp.boundary;

import com.example.expensesapp.entity.UserEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserBoundary extends UserEntity implements Serializable {

    private HashMap<String, UserCategoryBoundary> userCategories;

    public UserBoundary() {
        userCategories = new HashMap<>();
    }

    public UserBoundary setUserCategories(HashMap<String, UserCategoryBoundary> userCategories) {
        this.userCategories = userCategories;
        return this;
    }

    public HashMap<String, UserCategoryBoundary> getUserCategories() {
        return userCategories;
    }

    public ArrayList<UserCategoryBoundary> getUserCategoriesAsList(){
        ArrayList<UserCategoryBoundary> userCategoryBoundaries = new ArrayList<>();
        // Iterate over the entries of the HashMap
        for (Map.Entry<String, UserCategoryBoundary> entry : this.userCategories.entrySet()) {
            // Get the value (UserCategoryBoundary object) from the entry
            UserCategoryBoundary userCategoryBoundary = entry.getValue();

            // Add the UserCategoryBoundary object to the ArrayList
            userCategoryBoundaries.add(userCategoryBoundary);
        }

        return userCategoryBoundaries;
    }
}
