package com.example.expensesapp.main;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.expensesapp.R;
import com.example.expensesapp.callback.CategoryCallBack;
import com.example.expensesapp.controller.CategoryController;
import com.example.expensesapp.entity.CategoryEntity;
import com.example.expensesapp.entity.CategoryItem;
import com.example.expensesapp.entity.UserCategory;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;


public class UpdateFragment extends Fragment {

    private CategoryController categoryController;
    private static ArrayList<CategoryEntity> CATEGORIES;
    private Activity activity;
    private TextInputLayout fUpdate_TF_title;
    private TextInputLayout fUpdate_TF_price;
    private Spinner fUpdate_SP_categoriesList;
    private Button fUpdate_BTN_add;


    public UpdateFragment(Activity activity) {
      this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update, container, false);
        findViews(view);
        initVars();
        return view;
    }

    private void findViews(View view) {
        fUpdate_TF_title = view.findViewById(R.id.fUpdate_TF_title);
        fUpdate_TF_price = view.findViewById(R.id.fUpdate_TF_price);
        fUpdate_SP_categoriesList = view.findViewById(R.id.fUpdate_SP_categoriesList);
        fUpdate_BTN_add = view.findViewById(R.id.fUpdate_BTN_add);
    }

    private void initVars() {
        fetchCategoriesData();
        fUpdate_BTN_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void fetchCategoriesData() {
        categoryController = new CategoryController();
        categoryController.setCategoryCallBack(new CategoryCallBack() {
            @Override
            public void onFetchCategoriesComplete(ArrayList<CategoryEntity> categories) {
                CATEGORIES = categories;
                ArrayAdapter<CategoryEntity> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, categories);
                fUpdate_SP_categoriesList.setAdapter(adapter);
            }
        });

        categoryController.fetchAllCategories();
    }

    private void addItemToCategory(){

        String title = fUpdate_TF_title.getEditText().getText().toString();
        double price = Double.parseDouble(fUpdate_TF_price.getEditText().getText().toString());
        UserCategory userCategory = new UserCategory();

        CategoryEntity categoryEntity = (CategoryEntity) fUpdate_SP_categoriesList.getSelectedItem();
        userCategory.setCategoryKey(categoryEntity.getKey());

        CategoryItem categoryItem = new CategoryItem();
        categoryItem.setTitle(title);
        categoryItem.setPrice(price);

        userCategory.addItem(categoryItem);
        userCategory.update(); // TODO update
    }

}