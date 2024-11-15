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

public class SearchActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private RecyclerView recyclerView;
    private CardAdapter cardAdapter;
    private List<Destination> destinationList;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        db = FirebaseFirestore.getInstance();

        // Initialize List and Adapter
        recyclerView = findViewById(R.id.recyclerView);
        destinationList = new ArrayList<>();
        cardAdapter = new CardAdapter(this, destinationList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(cardAdapter);

        loadDestinationsFromFirestore();

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
                overridePendingTransition(0, 0);
                return true;
            }
            return false;
        });
    }

    private void loadDestinationsFromFirestore() {
        db.collection("destinations")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String id = document.getId();
                            String name = document.getString("name");
                            String price = document.getString("price");
                            String imageUrl = document.getString("imageUrl");

                            // Log data to confirm retrieval
                            Log.d("FirestoreData", "Fetched Destination: " + name + ", Price: " + price + ", ImageURL: " + imageUrl);

                            destinationList.add(new Destination(id, name, price, imageUrl));
                        }

                        cardAdapter.notifyDataSetChanged();
                        Log.d("FirestoreData", "Data successfully loaded into adapter.");
                    } else {
                        Log.w("SearchActivity", "Error getting documents.", task.getException());
                    }
                });
    }
}