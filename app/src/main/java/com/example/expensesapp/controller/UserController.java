package com.example.expensesapp.controller;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.expensesapp.boundary.UserBoundary;
import com.example.expensesapp.boundary.UserCategoryBoundary;
import com.example.expensesapp.callback.UserCallBack;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserController {
    private FirebaseDatabase db ;
    private UserCallBack userCallBack;

    public UserController(){
        this.db = FirebaseDatabase.getInstance();
    }

    public void setUserCallBack(UserCallBack userCallBack){
        this.userCallBack = userCallBack;
    }

    public void saveUser(UserEntity user){
        user.saveUser(db).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                userCallBack.onUserSaveComplete(task);
            }
        });
    }

    public void updateUserData(Activity activity, UserEntity user){
        Map<String, Object> userFields = new HashMap<>();
        userFields.put("firstName", user.getFirstName());
        userFields.put("lastName", user.getLastName());
        userFields.put("imagePath", user.getImagePath());
        this.db.getReference(UserEntity.USERS_TABLE).child(user.getKey()).updateChildren(userFields)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(activity, "Profile update successfully", Toast.LENGTH_SHORT).show();
                        activity.finish();
                    }
                });
    }

    public void getUserInfo(String uid){
        db.getReference().child(UserEntity.USERS_TABLE).child(uid).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserEntity user = snapshot.getValue(UserEntity.class);
                StorageController storageController = new StorageController();
                if(user.getImagePath() != null){
                    String imageUrl = storageController.downloadImageUrl(user.getImagePath());
                    user.setImageUrl(imageUrl);
                }
                userCallBack.onUserInfoFetchComplete(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void getUserData(String uid, ArrayList<CategoryEntity> categoryEntities){
        db.getReference().child(UserEntity.USERS_TABLE).child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserBoundary userBoundary = snapshot.getValue(UserBoundary.class);

                for(int i = 0 ; i < categoryEntities.size(); i++) {
                    String key = categoryEntities.get(i).getKey();
                    for(Map.Entry<String, UserCategoryBoundary> userCategory:userBoundary.getUserCategories().entrySet()){

                        if(key.equals(userCategory.getValue().getCategoryKey())){
                            userCategory.getValue().setCategoryEntity(categoryEntities.get(i));
                        }
                    }
                }
                userCallBack.onUserDataFetchComplete(userBoundary);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void updateCategoryMaxPrice(String uid, String categoryKey, double maxPrice){
        Map<String, Object> maxPriceField = new HashMap<>();
        maxPriceField.put("maxPrice", maxPrice);
        this.db.getReference(UserEntity.USERS_TABLE).child(uid).child("userCategories")
                .child(categoryKey).updateChildren(maxPriceField);
    }
}
