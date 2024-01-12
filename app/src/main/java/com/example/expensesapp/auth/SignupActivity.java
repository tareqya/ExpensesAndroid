package com.example.expensesapp.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.expensesapp.R;
import com.example.expensesapp.boundary.UserBoundary;
import com.example.expensesapp.callback.AuthCallBack;
import com.example.expensesapp.callback.UserCallBack;
import com.example.expensesapp.controller.AuthController;
import com.example.expensesapp.controller.UserController;
import com.example.expensesapp.entity.UserEntity;
import com.google.android.gms.tasks.Task;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;

public class SignupActivity extends AppCompatActivity {

    private TextInputLayout signup_TF_firstName;
    private TextInputLayout signup_TF_lastName;
    private TextInputLayout signup_TF_email;
    private TextInputLayout signup_TF_password;
    private TextInputLayout signup_TF_confirmPassword;
    private Button signup_BTN_login;
    private CircularProgressIndicator signup_PB_loading;
    private AuthController authController;
    private UserController userController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        findViews();
        initVars();
    }

    private void initVars() {
        authController = new AuthController();
        userController = new UserController();
        authController.setAuthCallBack(new AuthCallBack() {
            @Override
            public void onCreateAccountComplete(Task<AuthResult> task) {

                if(task.isSuccessful()){
                    String email = signup_TF_email.getEditText().getText().toString();
                    String firstName = signup_TF_firstName.getEditText().getText().toString();
                    String lastName = signup_TF_lastName.getEditText().getText().toString();

                    UserEntity user = new UserEntity()
                                    .setEmail(email)
                                    .setFirstName(firstName)
                                    .setLastName(lastName);
                    user.setKey(authController.getCurrentUser().getUid());
                    userController.saveUser(user);
                }else {
                    String error = task.getException().getMessage().toString();
                    Toast.makeText(SignupActivity.this, error, Toast.LENGTH_SHORT).show();
                    signup_PB_loading.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onLoginComplete(Task<AuthResult> task) {

            }
        });

        userController.setUserCallBack(new UserCallBack() {
            @Override
            public void onUserSaveComplete(Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignupActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                    authController.logout();
                    finish();
                }else{
                    String error = task.getException().getMessage().toString();
                    Toast.makeText(SignupActivity.this, error, Toast.LENGTH_SHORT).show();
                }
                signup_PB_loading.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onUserDataFetchComplete(UserBoundary userBoundary) {

            }

            @Override
            public void onUserInfoFetchComplete(UserEntity user) {

            }
        });

        signup_BTN_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = signup_TF_email.getEditText().getText().toString();
                String password = signup_TF_password.getEditText().getText().toString();

                if(!checkInput()){
                    Toast.makeText(SignupActivity.this, "Please check your inputs!", Toast.LENGTH_SHORT).show();
                    return;
                }

                AuthUser authUser = new AuthUser(email, password);
                authController.createAccount(authUser);
                signup_PB_loading.setVisibility(View.VISIBLE);

            }
        });

    }

    private void findViews() {
        signup_TF_firstName = findViewById(R.id.signup_TF_firstName);
        signup_TF_lastName = findViewById(R.id.signup_TF_lastName);
        signup_TF_email = findViewById(R.id.signup_TF_email);
        signup_TF_password = findViewById(R.id.signup_TF_password);
        signup_TF_confirmPassword = findViewById(R.id.signup_TF_confirmPassword);
        signup_BTN_login = findViewById(R.id.signup_BTN_login);
        signup_PB_loading = findViewById(R.id.signup_PB_loading);

    }


    private boolean checkInput(){
        String email = signup_TF_email.getEditText().getText().toString();
        String password = signup_TF_password.getEditText().getText().toString();
        String passwordConfirm = signup_TF_confirmPassword.getEditText().getText().toString();
        String firstName = signup_TF_firstName.getEditText().getText().toString();
        String lastName = signup_TF_lastName.getEditText().getText().toString();

        if(email.length() == 0){
            return false;
        }

        if(password.length() < 6){
            Toast.makeText(SignupActivity.this, "Please check your password!", Toast.LENGTH_SHORT).show();
            return  false;
        }

        if(!password.equals(passwordConfirm)){
            return false;
        }


        if(firstName.length() == 0) {
            return false;
        }


        if(lastName.length() == 0){
            return false;
        }


        return true;
    }
}