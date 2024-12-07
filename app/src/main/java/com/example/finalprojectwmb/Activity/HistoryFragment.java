package com.example.finalprojectwmb.Activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalprojectwmb.Adapter.ApplicationAdapter;
import com.example.finalprojectwmb.R;
import com.example.finalprojectwmb.TravelApplication;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {
    private RecyclerView recyclerView;
    private ApplicationAdapter applicationAdapter;
    private List<TravelApplication> applicationList;
    private FirebaseFirestore db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize application list
        applicationList = new ArrayList<>();

        // Load applications from Firestore
        loadApplications();

        return view;
    }

    private void loadApplications() {
        db.collection("applications")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            TravelApplication application = document.toObject(TravelApplication.class);
                            applicationList.add(application);
                        }
                        applicationAdapter = new ApplicationAdapter(applicationList);
                        recyclerView.setAdapter(applicationAdapter);
                    } else {
                        Toast.makeText(getContext(), "Error getting applications", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
