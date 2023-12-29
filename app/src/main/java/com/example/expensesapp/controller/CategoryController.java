package com.example.expensesapp.controller;

import androidx.annotation.NonNull;

import com.example.expensesapp.callback.CategoryCallBack;
import com.example.expensesapp.callback.StorageCallBack;
import com.example.expensesapp.entity.CategoryEntity;
import com.example.expensesapp.entity.UserCategory;
import com.example.expensesapp.entity.UserEntity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CategoryController {
    public static final String USER_CATEGORY_TABLE = "userCategories";
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


    public void updateCategoryItems(String uid, UserCategory userCategory){
        db.getReference().child(UserEntity.USERS_TABLE).child(uid).child(USER_CATEGORY_TABLE)
                .child(userCategory.getCategoryKey()).setValue(userCategory)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        categoryCallBack.onCategoryItemUpdateComplete(task);
                    }
                });
    }

    public void getUserCategoriesItems(String uid){
        db.getReference().child(UserEntity.USERS_TABLE).child(uid).child(USER_CATEGORY_TABLE)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        ArrayList<UserCategory> userCategories = new ArrayList<>();

                        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                            UserCategory userCategory = childSnapshot.getValue(UserCategory.class);
                            userCategories.add(userCategory);
                        }

                        categoryCallBack.onUserCategoriesFetchComplete(userCategories);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}
