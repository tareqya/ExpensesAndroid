package com.example.expensesapp.main;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.expensesapp.R;
import com.example.expensesapp.boundary.UserBoundary;
import com.example.expensesapp.callback.CategoryCallBack;
import com.example.expensesapp.callback.UserCallBack;
import com.example.expensesapp.controller.AuthController;
import com.example.expensesapp.controller.CategoryController;
import com.example.expensesapp.controller.UserController;
import com.example.expensesapp.entity.CategoryEntity;
import com.example.expensesapp.entity.UserCategory;
import com.google.android.gms.tasks.Task;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

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
            }
        });
        categoryController.fetchAllCategories();

    }
}