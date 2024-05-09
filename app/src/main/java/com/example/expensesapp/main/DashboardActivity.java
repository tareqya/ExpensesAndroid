package com.example.expensesapp.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.expensesapp.R;
import com.example.expensesapp.adapters.CategoryAdapter;
import com.example.expensesapp.boundary.UserBoundary;
import com.example.expensesapp.boundary.UserCategoryBoundary;
import com.example.expensesapp.callback.CategoryCallBack;
import com.example.expensesapp.callback.OnClickCategory;
import com.example.expensesapp.callback.UserCallBack;
import com.example.expensesapp.controller.AuthController;
import com.example.expensesapp.controller.CategoryController;
import com.example.expensesapp.controller.UserController;
import com.example.expensesapp.entity.CategoryEntity;
import com.example.expensesapp.entity.UserCategory;
import com.example.expensesapp.entity.UserEntity;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {
    public static final String SELECTED_CATEGORY = "SELECTED_CATEGORY";
    private RecyclerView dashboard_RV_categories;
    private AuthController authController;
    private UserController userController;
    private CategoryController categoryController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        findViews();
        initVars();
    }

    private void initVars() {
        authController = new AuthController();
        userController = new UserController();
        categoryController = new CategoryController();
        userController.setUserCallBack(new UserCallBack() {
            @Override
            public void onUserSaveComplete(Task<Void> task) {

            }

            @Override
            public void onUserDataFetchComplete(UserBoundary userBoundary) {

                CategoryAdapter categoryAdapter = new CategoryAdapter(DashboardActivity.this, userBoundary.getUserCategoriesAsList());
                categoryAdapter.setOnClickCategory(new OnClickCategory() {
                    @Override
                    public void onClick(UserCategoryBoundary categoryBoundary, int position) {
                        Intent intent = new Intent(DashboardActivity.this, CompareCategoryActivity.class);
                        intent.putExtra(SELECTED_CATEGORY, categoryBoundary);
                        startActivity(intent);
                    }
                });
                dashboard_RV_categories.setLayoutManager(new LinearLayoutManager(DashboardActivity.this, LinearLayoutManager.VERTICAL, false));
                dashboard_RV_categories.setHasFixedSize(true);
                dashboard_RV_categories.setItemAnimator(new DefaultItemAnimator());
                dashboard_RV_categories.setAdapter(categoryAdapter);
            }

            @Override
            public void onUserInfoFetchComplete(UserEntity user) {

            }
        });
        categoryController.setCategoryCallBack(new CategoryCallBack() {
            @Override
            public void onFetchCategoriesComplete(ArrayList<CategoryEntity> categories) {
                String uid = authController.getCurrentUser().getUid();
                userController.getUserData(uid, categories);

            }

            @Override
            public void onUserCategoriesFetchComplete(ArrayList<UserCategory> userCategories) {

            }

            @Override
            public void onCategoryItemUpdateComplete(Task<Void> task) {

            }
        });

        categoryController.fetchAllCategories();
    }

    private void findViews() {
        dashboard_RV_categories = findViewById(R.id.dashboard_RV_categories);
    }
}