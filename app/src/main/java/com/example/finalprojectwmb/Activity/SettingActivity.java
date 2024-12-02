package com.example.finalprojectwmb.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalprojectwmb.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting); // Change this to the layout of each respective activity

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.settings);


        // Set up navigation item selection listener
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Intent intent = null;

            if (item.getItemId() == R.id.home) {
                // Navigate to SearchActivity (as Home)
                intent = new Intent(SettingActivity.this, SearchActivity.class);
            } else if (item.getItemId() == R.id.history) {
                intent = new Intent(SettingActivity.this, HistoryActivity.class);
            } else if (item.getItemId() == R.id.settings) {
                intent = new Intent(SettingActivity.this, SettingActivity.class);
            } else if (item.getItemId() == R.id.profile) {
                intent = new Intent(SettingActivity.this, ProfileActivity.class);
            }

            if (intent != null) {
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                overridePendingTransition(0, 0);
                return true;
            }
            return false;
        });
    }
}