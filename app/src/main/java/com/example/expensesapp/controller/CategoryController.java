package com.example.expensesapp.controller;

import androidx.annotation.NonNull;

import com.example.expensesapp.callback.CategoryCallBack;
import com.example.expensesapp.callback.StorageCallBack;
import com.example.expensesapp.entity.CategoryEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CategoryController {
    private FirebaseDatabase db ;

    private CategoryCallBack categoryCallBack;

    public CategoryController(){
        db = FirebaseDatabase.getInstance();
    }

    public void setCategoryCallBack(CategoryCallBack categoryCallBack){
        this.categoryCallBack = categoryCallBack;
    }
    public void fetchAllCategories(){
        db.getReference().child(CategoryEntity.CATEGORY_TABLE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                StorageController storageController = new StorageController();
                ArrayList<CategoryEntity> categories = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    CategoryEntity categoryEntity = childSnapshot.getValue(CategoryEntity.class);
                    categoryEntity.setKey(childSnapshot.getKey());
                    String imageUrl = storageController.downloadImageUrl(categoryEntity.getImagePath());
                    categoryEntity.setImageUrl(imageUrl);
                    categories.add(categoryEntity);
                }

                categoryCallBack.onFetchCategoriesComplete(categories);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
