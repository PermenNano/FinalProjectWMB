package com.example.finalprojectwmb.Activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.example.finalprojectwmb.Adapter.PackageAdapter;
import com.example.finalprojectwmb.R;
import com.example.finalprojectwmb.TravelPackage;

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

        // West Borneo Packages
        packageList.add(new TravelPackage("3 Day low cost package", "IDR 1,500,000", "Include Hotel, Food and Accommodations", R.drawable.westborneo1));
        packageList.add(new TravelPackage("Medium cost 4 Day Package", "IDR 3,500,000", "Include Hotel, Food and Accommodations", R.drawable.westborneo2));
        packageList.add(new TravelPackage("High cost 5 Day Package", "IDR 5,000,000", "Include Hotel, Food and Accommodations", R.drawable.westborneo3));

        // South Region Packages
        packageList.add(new TravelPackage("3 Day South Adventure", "IDR 1,800,000", "Explore the beautiful southern landscapes", R.drawable.southborneo1));
        packageList.add(new TravelPackage("Medium cost South Package", "IDR 3,200,000", "Experience the culture and cuisine of the south", R.drawable.southborneo2));
        packageList.add(new TravelPackage("High cost South Luxury", "IDR 5,500,000", "Luxury stay with all amenities included", R.drawable.southborneo3));

        // North Region Packages
        packageList.add(new TravelPackage("3 Day North Escape", "IDR 2,000,000", "Discover the northern wonders", R.drawable.northborneo1));
        packageList.add(new TravelPackage("Medium cost North Package", "IDR 3,800,000", "Adventure and exploration in the north", R.drawable.northborneo2));
        packageList.add(new TravelPackage("High cost North Experience", "IDR 6,000,000", "Exclusive tour with premium services", R.drawable.northborneo3));

        packageAdapter = new PackageAdapter(packageList);
        recyclerView.setAdapter(packageAdapter);
    }
}
