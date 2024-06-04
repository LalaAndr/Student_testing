package com.mirea.studenttesting.screen;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.mirea.studenttesting.R;

public class MainActivity extends AppCompatActivity {
    private NavHostFragment navHostFragment;
    private NavController navController;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupNavigation();
    }

    private void setupNavigation() {
         navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_container);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();
    }
}