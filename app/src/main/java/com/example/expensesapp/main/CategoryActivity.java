package com.example.expensesapp.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.expensesapp.R;
import com.example.expensesapp.adapters.ProductAdapter;
import com.example.expensesapp.boundary.UserCategoryBoundary;
import com.google.android.material.progressindicator.LinearProgressIndicator;


public class CategoryActivity extends AppCompatActivity {
    private ImageView aCategory_IMG_image;
    private TextView aCategory_TV_name;
    private LinearProgressIndicator aCategory_PI_progress;
    private TextView aCategory_TV_price;
    private RecyclerView aCategory_RV_products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Intent intent = getIntent();

        UserCategoryBoundary userCategoryBoundary = (UserCategoryBoundary) intent.getSerializableExtra(HomeFragment.SELECTED_CATEGORY_KEY);
        
        findViews();
        initVars();
        displayData(userCategoryBoundary);
    }

    private void displayData(UserCategoryBoundary userCategoryBoundary) {
        Glide.with(this)
                .load(userCategoryBoundary.getCategoryEntity().getImageUrl())
                .into(aCategory_IMG_image);

        aCategory_TV_name.setText(userCategoryBoundary.getCategoryEntity().getName());
        double percent = userCategoryBoundary.getTotalPrice() / userCategoryBoundary.getMaxPrice();

        if (percent > 1){
            aCategory_PI_progress.setProgress(100);
        }else{
            // percent * 100
            int value = (int) Math.round(percent * 100);
            aCategory_PI_progress.setProgress(value);
        }

        aCategory_TV_price.setText(userCategoryBoundary.getTotalPrice() + "  ₪");

        ProductAdapter productAdapter = new ProductAdapter(this, userCategoryBoundary.getCategoryItems());
        aCategory_RV_products.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        aCategory_RV_products.setHasFixedSize(true);
        aCategory_RV_products.setItemAnimator(new DefaultItemAnimator());
        aCategory_RV_products.setAdapter(productAdapter);
    }

    private void initVars() {

    }

    private void findViews() {
        aCategory_IMG_image = findViewById(R.id.aCategory_IMG_image);
        aCategory_TV_name = findViewById(R.id.aCategory_TV_name);
        aCategory_PI_progress = findViewById(R.id.aCategory_PI_progress);
        aCategory_TV_price = findViewById(R.id.aCategory_TV_price);
        aCategory_RV_products = findViewById(R.id.aCategory_RV_products);

    }
}