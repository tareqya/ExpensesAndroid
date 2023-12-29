package com.example.expensesapp.callback;

import com.example.expensesapp.entity.CategoryEntity;
import com.example.expensesapp.entity.UserCategory;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public interface CategoryCallBack {
    void onFetchCategoriesComplete(ArrayList<CategoryEntity> categories);
    void onUserCategoriesFetchComplete(ArrayList<UserCategory> userCategories);
    void onCategoryItemUpdateComplete(Task<Void> task);

}
