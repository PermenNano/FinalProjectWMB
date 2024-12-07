package com.example.finalprojectwmb.Activity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import com.example.finalprojectwmb.R;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search); // Ensure this is the correct layout

        // Set up BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);

        // Set the default fragment to SearchFragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new SearchFragment()) // Replace with your container ID
                    .commit();
        }

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.home) {
                selectedFragment = new SearchFragment();
            } else if (item.getItemId() == R.id.history) {
                selectedFragment = new HistoryFragment(); // Replace with your HistoryFragment
            } else if (item.getItemId() == R.id.settings) {
                selectedFragment = new SettingFragment(); // Replace with your SettingFragment
            } else if (item.getItemId() == R.id.profile) {
                selectedFragment = new ProfileFragment(); // Replace with your ProfileFragment
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment) // Replace with your container ID
                        .addToBackStack(null) // Optional: add to back stack
                        .commit();
            }
            return true; // Return true to indicate the item was selected
        });
    }

}
