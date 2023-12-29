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
import android.widget.Toast;

import com.example.expensesapp.R;
import com.example.expensesapp.callback.CategoryCallBack;
import com.example.expensesapp.controller.AuthController;
import com.example.expensesapp.controller.CategoryController;
import com.example.expensesapp.entity.CategoryEntity;
import com.example.expensesapp.entity.CategoryItem;
import com.example.expensesapp.entity.UserCategory;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;


public class UpdateFragment extends Fragment {

    private CategoryController categoryController;
    public static ArrayList<CategoryEntity> CATEGORIES = new ArrayList<>();
    public static ArrayList<UserCategory> USER_CATEGORIES = new ArrayList<>();
    private Activity activity;
    private TextInputLayout fUpdate_TF_title;
    private TextInputLayout fUpdate_TF_price;
    private Spinner fUpdate_SP_categoriesList;
    private Button fUpdate_BTN_add;
    private AuthController authController;

    public UpdateFragment(Activity activity) {
      this.activity = activity;
      authController = new AuthController();
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
        fetchUserCategoriesItem();
        fUpdate_BTN_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItemToCategory();
            }
        });
    }

    private void fetchUserCategoriesItem() {
        String uid = authController.getCurrentUser().getUid();
        categoryController.getUserCategoriesItems(uid);
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

            @Override
            public void onUserCategoriesFetchComplete(ArrayList<UserCategory> userCategories) {
                USER_CATEGORIES = userCategories;
            }

            @Override
            public void onCategoryItemUpdateComplete(Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(activity, "Your product added to the category", Toast.LENGTH_SHORT).show();
                    fUpdate_TF_price.getEditText().setText("");
                    fUpdate_TF_title.getEditText().setText("");
                }else{
                    String err = task.getException().getMessage().toString();
                    Toast.makeText(activity, err, Toast.LENGTH_SHORT).show();
                }
            }
        });

        categoryController.fetchAllCategories();
    }

    private void addItemToCategory(){

        String uid = authController.getCurrentUser().getUid();
        String title = fUpdate_TF_title.getEditText().getText().toString();
        double price = Double.parseDouble(fUpdate_TF_price.getEditText().getText().toString());
        CategoryEntity categoryEntity = (CategoryEntity) fUpdate_SP_categoriesList.getSelectedItem();

        CategoryItem categoryItem = new CategoryItem()
                .setPrice(price)
                .setTitle(title);

        boolean found = false;

        for(UserCategory userCategory : USER_CATEGORIES){
            // add item to existing category
            if(categoryEntity.getKey().equals(userCategory.getCategoryKey())){
                userCategory.addItem(categoryItem);
                categoryController.updateCategoryItems(uid, userCategory);
                found = true;
                break;
            }
        }
        // new category
        if(!found){
            UserCategory userCategory = new UserCategory();
            userCategory.setMaxPrice(1000);
            userCategory.setCategoryKey(categoryEntity.getKey());
            userCategory.addItem(categoryItem);
            categoryController.updateCategoryItems(uid, userCategory);
        }

    }

}