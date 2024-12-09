package com.example.finalprojectwmb.Activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.fragment.app.Fragment;

import com.example.finalprojectwmb.Adapter.PackageAdapter;
import com.example.finalprojectwmb.R;
import com.example.finalprojectwmb.TravelPackage;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class DestinationDetailActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PackageAdapter packageAdapter;
    private List<TravelPackage> packageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination_detail);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        packageList = new ArrayList<>();

        String destinationId = getIntent().getStringExtra("destinationId");

        loadPackages(destinationId);

        packageAdapter = new PackageAdapter(this, packageList);
        recyclerView.setAdapter(packageAdapter);

        setupBottomNavigation();
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = getSelectedFragment(item.getItemId());

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .addToBackStack(null)
                        .commit();
                return true;
            }

            return false;
        });
    }

    private Fragment getSelectedFragment(int itemId) {
        if (itemId == R.id.home) {
            return new SearchFragment();
        } else if (itemId == R.id.history) {
            return new HistoryFragment();
        } else if (itemId == R.id.settings) {
            return new SettingFragment();
        } else if (itemId == R.id.profile) {
            return new ProfileFragment();
        } else {
            return null;
        }
    }

    private void loadPackages(String destinationId) {
        if ("1".equals(destinationId)) {
            packageList.add(new TravelPackage("3 Day low cost package", "IDR 1,500,000", "Include Hotel, Food and Accommodations", R.drawable.westborneo1));
            packageList.add(new TravelPackage("Medium cost 4 Day Package", "IDR 3,500,000", "Include Hotel, Food and Accommodations", R.drawable.westborneo2));
            packageList.add(new TravelPackage("High cost 5 Day Package", "IDR 5,000,000", "Include Hotel, Food and Accommodations", R.drawable.westborneo3));
        } else if ("2".equals(destinationId)) {
            packageList.add(new TravelPackage("3 Day South Adventure", "IDR 1,800,000", "Explore the beautiful southern landscapes", R.drawable.southborneo1));
            packageList.add(new TravelPackage("Medium cost South Package", "IDR 3,200,000", "Experience the culture and cuisine of the south", R.drawable.southborneo2));
            packageList.add(new TravelPackage("High cost South Luxury", "IDR 5,500,000", "Luxury stay with all amenities included", R.drawable.southborneo3));
        } else if ("3".equals(destinationId)) {
            packageList.add(new TravelPackage("3 Day North Escape", "IDR 2,000,000", "Discover the northern wonders", R.drawable.northborneo1));
            packageList.add(new TravelPackage("Medium cost North Package", "IDR 3,800,000", "Adventure and exploration in the north", R.drawable.northborneo2));
            packageList.add(new TravelPackage("High cost North Experience", "IDR 6,000,000", "Exclusive tour with premium services", R.drawable.northborneo3));
        }
    }
}
