package com.example.finalprojectwmb.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalprojectwmb.Adapter.CardAdapter;
import com.example.finalprojectwmb.Destination;
import com.example.finalprojectwmb.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

// SearchActivity.java
public class SearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CardAdapter cardAdapter;
    private List<Destination> destinationList;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Initialize RecyclerView and Adapter
        recyclerView = findViewById(R.id.recyclerView);
        destinationList = new ArrayList<>();
        cardAdapter = new CardAdapter(this, destinationList, this::onDestinationClick);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(cardAdapter);

        // Load destinations from local data
        loadLocalDestinations();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);

        // Set up navigation item selection listener
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Intent intent = null;

            if (item.getItemId() == R.id.home) {
                // Navigate to SearchActivity (as Home)
                intent = new Intent(SearchActivity.this, SearchActivity.class);
            } else if (item.getItemId() == R.id.history) {
                intent = new Intent(SearchActivity.this, HistoryActivity.class);
            } else if (item.getItemId() == R.id.settings) {
                intent = new Intent(SearchActivity.this, SettingActivity.class);
            } else if (item.getItemId() == R.id.profile) {
                intent = new Intent(SearchActivity.this, ProfileActivity.class);
            }

            if (intent != null) {
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                overridePendingTransition( 0, 0);
                return true;
            }
            return false;
        });
    }

    private void loadLocalDestinations() {
        // Hardcoded destinations list (using drawable resources)
        destinationList.add(new Destination("1", "West Borneo", R.drawable.westborneo));
        destinationList.add(new Destination("2", "South Borneo", R.drawable.southborneo));
        destinationList.add(new Destination("3", "North Borneo", R.drawable.northborneo));

        // Notify adapter about the new data
        cardAdapter.notifyDataSetChanged();
    }

    private void onDestinationClick(Destination destination) {
        Intent intent = new Intent(this, DestinationDetailActivity.class);
        intent.putExtra("destinationId", destination.getId());
        startActivity(intent);
    }
}
