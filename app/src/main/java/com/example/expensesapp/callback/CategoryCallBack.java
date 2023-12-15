package com.example.expensesapp.callback;

import com.example.expensesapp.entity.CategoryEntity;

import java.util.ArrayList;

public interface CategoryCallBack {
    void onFetchCategoriesComplete(ArrayList<CategoryEntity> categories);
}
