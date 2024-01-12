package com.example.expensesapp.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    public static final String SELECTED_CATEGORY_KEY = "SELECTED_CATEGORY_KEY";
    private Activity activity;
    private TextView fHome_TV_name;
    private RecyclerView fHome_RV_categories;
    private UserController userController;
    private AuthController authController;
    private CategoryController categoryController;
    private CircularProgressIndicator fHome_PB_loading;

    public static UserBoundary user;

    public HomeFragment(Activity activity) {
        this.activity = activity;
        userController = new UserController();
        authController = new AuthController();
        categoryController = new CategoryController();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        findViews(view);
        initVars();
        return view;
    }

    private void findViews(View view) {
        fHome_TV_name = view.findViewById(R.id.fHome_TV_name);
        fHome_RV_categories = view.findViewById(R.id.fHome_RV_categories);
        fHome_PB_loading = view.findViewById(R.id.fHome_PB_loading);

    }

    private void initVars() {
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
        userController.setUserCallBack(new UserCallBack() {
            @Override
            public void onUserSaveComplete(Task<Void> task) {

            }

            @Override
            public void onUserDataFetchComplete(UserBoundary userBoundary) {
                user = userBoundary;
                fHome_TV_name.setText("Hello " + user.getFirstName());
                fHome_PB_loading.setVisibility(View.INVISIBLE);

                CategoryAdapter categoryAdapter = new CategoryAdapter(activity, userBoundary.getUserCategories());
                categoryAdapter.setOnClickCategory(new OnClickCategory() {
                    @Override
                    public void onClick(UserCategoryBoundary categoryBoundary, int position) {
                        Intent intent = new Intent(activity, CategoryActivity.class);
                        intent.putExtra(SELECTED_CATEGORY_KEY, categoryBoundary);
                        startActivity(intent);

                    }
                });


                fHome_RV_categories.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
                fHome_RV_categories.setHasFixedSize(true);
                fHome_RV_categories.setItemAnimator(new DefaultItemAnimator());
                fHome_RV_categories.setAdapter(categoryAdapter);

            }

            @Override
            public void onUserInfoFetchComplete(UserEntity user) {

            }
        });
        categoryController.fetchAllCategories();

    }
}