package com.example.expensesapp.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.expensesapp.R;
import com.example.expensesapp.adapters.ProductAdapter;
import com.example.expensesapp.boundary.UserCategoryBoundary;
import com.example.expensesapp.controller.AuthController;
import com.example.expensesapp.controller.UserController;
import com.example.expensesapp.entity.CategoryItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompareCategoryActivity extends AppCompatActivity {

    private ImageView compareCategory_IMG_image;
    private TextView compareCategory_TV_name;
    private EditText compareCategory_ET_price;
    private RecyclerView compareCategory_RV_productsMonth;
    private UserController userController;
    private AuthController authController;
    private UserCategoryBoundary userCategoryBoundary;
    private FloatingActionButton compareCategory_FBTN_updatePrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_category);
        Intent intent = getIntent();
        userCategoryBoundary = (UserCategoryBoundary) intent.getSerializableExtra(DashboardActivity.SELECTED_CATEGORY);

        findViews();
        initVars();
        displayData(userCategoryBoundary);
    }

    private void initVars() {
        userController = new UserController();
        authController = new AuthController();

        compareCategory_FBTN_updatePrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String uid = authController.getCurrentUser().getUid();
                    String categoryKey = userCategoryBoundary.getCategoryKey();
                    double price = Double.parseDouble(compareCategory_ET_price.getText().toString());
                    userController.updateCategoryMaxPrice(uid, categoryKey, price);
                }catch (Exception e){
                    Toast.makeText(CompareCategoryActivity.this, "Failed to update price", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void findViews() {
        compareCategory_IMG_image = findViewById(R.id.compareCategory_IMG_image);
        compareCategory_TV_name = findViewById(R.id.compareCategory_TV_name);
        compareCategory_ET_price = findViewById(R.id.compareCategory_ET_price);
        compareCategory_RV_productsMonth = findViewById(R.id.compareCategory_RV_productsMonth);
        compareCategory_FBTN_updatePrice = findViewById(R.id.compareCategory_FBTN_updatePrice);

    }

    public List<CategoryItem> splitProductsPerMonth(ArrayList<CategoryItem> products){
        CategoryItem[] monthsProducts = new CategoryItem[12];
        for(int i = 0; i < 12; i++){
            double totalMonthPrice = 0;
            for(CategoryItem categoryItem : products){
                if(categoryItem.getDate().getMonth() == i){
                    totalMonthPrice += categoryItem.getPrice();
                }
            }

            monthsProducts[i] = new CategoryItem()
                    .setPrice(totalMonthPrice)
                    .setTitle("Month " + (i + 1));
        }

        return Arrays.asList(monthsProducts);
    }
    private void displayData(UserCategoryBoundary userCategoryBoundary) {
        Glide.with(this)
                .load(userCategoryBoundary.getCategoryEntity().getImageUrl())
                .into(compareCategory_IMG_image);

        compareCategory_TV_name.setText(userCategoryBoundary.getCategoryEntity().getName());

        compareCategory_ET_price.setText(userCategoryBoundary.getMaxPrice() + "");

        ProductAdapter productAdapter = new ProductAdapter(this, splitProductsPerMonth(userCategoryBoundary.getCategoryItems()));
        compareCategory_RV_productsMonth.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        compareCategory_RV_productsMonth.setHasFixedSize(true);
        compareCategory_RV_productsMonth.setItemAnimator(new DefaultItemAnimator());
        compareCategory_RV_productsMonth.setAdapter(productAdapter);
    }
}