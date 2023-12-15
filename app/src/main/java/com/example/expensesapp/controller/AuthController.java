package com.example.expensesapp.controller;

import androidx.annotation.NonNull;

import com.example.expensesapp.auth.AuthUser;
import com.example.expensesapp.callback.AuthCallBack;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthController {
    private FirebaseAuth mAuth;
    private AuthCallBack authCallBack;

    public  AuthController() {
        this.mAuth = FirebaseAuth.getInstance();
    }

    public void setAuthCallBack(AuthCallBack authCallBack){
        this.authCallBack = authCallBack;
    }

    public void login(AuthUser authUser){
        this.mAuth.signInWithEmailAndPassword(authUser.getEmail(), authUser.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                authCallBack.onLoginComplete(task);
            }
        });
    }

    public void createAccount(AuthUser authUser){
        this.mAuth.createUserWithEmailAndPassword(authUser.getEmail(), authUser.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       authCallBack.onCreateAccountComplete(task);
                    }
                });
    }

    public FirebaseUser getCurrentUser(){
        return mAuth.getCurrentUser();
    }

    public void logout(){
        mAuth.signOut();
    }


    public void sendResetPasswordLink(String email){
        mAuth.sendPasswordResetEmail(email);
    }
}
