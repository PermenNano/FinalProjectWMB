package com.example.finalprojectwmb;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import com.example.finalprojectwmb.R;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history); // Change this to the layout of each respective activity

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.history);


        // Set up navigation item selection listener
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Intent intent = null;

            if (item.getItemId() == R.id.home) {
                // Navigate to SearchActivity (as Home)
                intent = new Intent(HistoryActivity.this, SearchActivity.class);
            } else if (item.getItemId() == R.id.history) {
                intent = new Intent(HistoryActivity.this, HistoryActivity.class);
            } else if (item.getItemId() == R.id.settings) {
                intent = new Intent(HistoryActivity.this, SettingActivity.class);
            } else if (item.getItemId() == R.id.profile) {
                intent = new Intent(HistoryActivity.this, ProfileActivity.class);
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
