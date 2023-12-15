package com.example.expensesapp.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.expensesapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity {

    private FrameLayout home_FL_home;
    private FrameLayout home_FL_update;
    private FrameLayout home_FL_profile;
    private BottomNavigationView home_BN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        findViews();
        initVars();
    }

    private void findViews() {
        home_FL_home = findViewById(R.id.home_FL_home);
        home_FL_update = findViewById(R.id.home_FL_update);
        home_FL_profile = findViewById(R.id.home_FL_profile);
        home_BN = findViewById(R.id.home_BN);
    }

    private void initVars() {
//        FragmentManager fragmentManager = getSupportFragmentManager();

        HomeFragment homeFragment = new HomeFragment(this);
        getSupportFragmentManager().beginTransaction().add(R.id.home_FL_home, homeFragment).commit();

        ProfileFragment profileFragment = new ProfileFragment(this);
        getSupportFragmentManager().beginTransaction().add(R.id.home_FL_profile, profileFragment).commit();

        UpdateFragment updateFragment = new UpdateFragment(this);
        getSupportFragmentManager().beginTransaction().add(R.id.home_FL_update, updateFragment).commit();

        home_FL_profile.setVisibility(View.INVISIBLE);
        home_FL_update.setVisibility(View.INVISIBLE);


        home_BN.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.getItemId() == R.id.home){
                    // home
                    home_FL_profile.setVisibility(View.INVISIBLE);
                    home_FL_update.setVisibility(View.INVISIBLE);
                    home_FL_home.setVisibility(View.VISIBLE);
                }else if (item.getItemId() == R.id.profile){
                    //profile
                    home_FL_profile.setVisibility(View.VISIBLE);
                    home_FL_update.setVisibility(View.INVISIBLE);
                    home_FL_home.setVisibility(View.INVISIBLE);
                }else {
                    // update
                    home_FL_profile.setVisibility(View.INVISIBLE);
                    home_FL_update.setVisibility(View.VISIBLE);
                    home_FL_home.setVisibility(View.INVISIBLE);
                }

                return true;
            }
        });

    }

}