package com.example.expensesapp.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.expensesapp.R;
import com.example.expensesapp.auth.LoginActivity;
import com.example.expensesapp.boundary.UserBoundary;
import com.example.expensesapp.callback.UserCallBack;
import com.example.expensesapp.controller.AuthController;
import com.example.expensesapp.controller.UserController;
import com.example.expensesapp.entity.UserEntity;
import com.google.android.gms.tasks.Task;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    public static final String CURRENT_USER_KEY = "CURRENT_USER";
    private CircleImageView profile_image;
    private TextView profile_TV_name;
    private TextView profile_TV_email;
    private CardView profile_CV_editDetails;
    private CardView profile_CV_dashboard;
    private CardView profile_CV_logout;
    private Activity activity;

    private UserController userController;
    private AuthController authController ;
    private UserEntity currentUser;

    public ProfileFragment(Activity activity) {
       this.activity = activity;
       this.userController = new UserController();
       this.authController = new AuthController();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        findViews(view);
        initVars();
        return view;
    }

    private void initVars() {
        this.userController.setUserCallBack(new UserCallBack() {
            @Override
            public void onUserSaveComplete(Task<Void> task) {

            }

            @Override
            public void onUserDataFetchComplete(UserBoundary userBoundary) {

            }

            @Override
            public void onUserInfoFetchComplete(UserEntity user) {
                currentUser = user;
                if(user.getImageUrl() != null){
                    Glide.with(activity)
                            .load(user.getImageUrl())
                            .into(profile_image);
                }

                profile_TV_name.setText(user.getFullName());
                profile_TV_email.setText(user.getEmail());

            }
        });

        String uid = this.authController.getCurrentUser().getUid();
        this.userController.getUserInfo(uid);
        profile_CV_editDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, UpdateProfileActivity.class);
                intent.putExtra(CURRENT_USER_KEY, currentUser);
                startActivity(intent);
            }
        });

        profile_CV_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, DashboardActivity.class);
                startActivity(intent);
            }
        });

        profile_CV_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authController.logout();
                Intent intent = new Intent(activity, LoginActivity.class);
                startActivity(intent);
                activity.finish();
            }
        });
    }

    private void findViews(View view) {
        profile_image = view.findViewById(R.id.profile_image);
        profile_TV_name = view.findViewById(R.id.profile_TV_name);
        profile_TV_email = view.findViewById(R.id.profile_TV_email);
        profile_CV_editDetails = view.findViewById(R.id.profile_CV_editDetails);
        profile_CV_dashboard = view.findViewById(R.id.profile_CV_dashboard);
        profile_CV_logout = view.findViewById(R.id.profile_CV_logout);
    }

}