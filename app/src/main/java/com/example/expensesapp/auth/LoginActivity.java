package com.example.expensesapp.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.expensesapp.R;
import com.example.expensesapp.callback.AuthCallBack;
import com.example.expensesapp.controller.AuthController;
import com.example.expensesapp.main.HomeActivity;
import com.google.android.gms.tasks.Task;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout login_TF_email;
    private  TextInputLayout login_TF_password;
    private Button login_BTN_login;
    private Button login_BTN_Signup;
    private CircularProgressIndicator login_PB_loading;
    private AuthController authController;
    private Button login_BTN_forgetPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViews();
        initVars();
    }

    private void findViews() {
        login_TF_email = findViewById(R.id.login_TF_email);
        login_TF_password = findViewById(R.id.login_TF_password);
        login_BTN_login = findViewById(R.id.login_BTN_login);
        login_BTN_Signup = findViewById(R.id.login_BTN_Signup);
        login_PB_loading = findViewById(R.id.login_PB_loading);
        login_BTN_forgetPassword = findViewById(R.id.login_BTN_forgetPassword);
    }

    private void initVars() {
        authController = new AuthController();

        authController.setAuthCallBack(new AuthCallBack() {
            @Override
            public void onCreateAccountComplete(Task<AuthResult> task) {

            }

            @Override
            public void onLoginComplete(Task<AuthResult> task) {
                login_PB_loading.setVisibility(View.INVISIBLE);
                if(task.isSuccessful()){
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    String error = task.getException().getMessage().toString();
                    Toast.makeText(LoginActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            }
        });
        login_BTN_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = login_TF_email.getEditText().getText().toString();
                String password = login_TF_password.getEditText().getText().toString();

                AuthUser authUser = new AuthUser(email, password);
                authController.login(authUser);
                login_PB_loading.setVisibility(View.VISIBLE);
            }
        });

        login_BTN_Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        login_BTN_forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = login_TF_email.getEditText().getText().toString();
                authController.sendResetPasswordLink(email);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        AuthController authController = new AuthController();
        if(authController.getCurrentUser() != null){
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }
}