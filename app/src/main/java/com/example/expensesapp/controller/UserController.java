package com.example.expensesapp.controller;

import androidx.annotation.NonNull;

import com.example.expensesapp.callback.UserCallBack;
import com.example.expensesapp.entity.UserEntity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

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
}
