package com.example.finalprojectwmb.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalprojectwmb.Adapter.CardAdapter;
import com.example.finalprojectwmb.Destination;
import com.example.finalprojectwmb.R;

import java.util.ArrayList;
import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SearchFragment extends Fragment {

    private RecyclerView recyclerView;
    private CardAdapter cardAdapter;
    private List<Destination> destinationList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        // Initialize RecyclerView and Adapter
        recyclerView = view.findViewById(R.id.recyclerView);
        destinationList = new ArrayList<>();
        cardAdapter = new CardAdapter(getContext(), destinationList, this::onDestinationClick);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(cardAdapter);

        loadLocalDestinations();

        return view;
    }

    private void onDestinationClick(Destination destination) {
        Intent intent = new Intent(getActivity(), DestinationDetailActivity.class);
        intent.putExtra("destinationId", destination.getId());
        startActivity(intent);
    }

    private void loadLocalDestinations() {
        destinationList.add(new Destination("1", "West Borneo", R.drawable.westborneo));
        destinationList.add(new Destination("2", "South Borneo", R.drawable.southborneo));
        destinationList.add(new Destination("3", "North Borneo", R.drawable.northborneo));
        cardAdapter.notifyDataSetChanged();
    }
}
