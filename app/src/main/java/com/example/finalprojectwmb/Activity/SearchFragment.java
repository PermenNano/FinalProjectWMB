package com.example.finalprojectwmb.Activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalprojectwmb.Destination;
import com.example.finalprojectwmb.Adapter.CardAdapter;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.MapView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import org.osmdroid.config.Configuration;

import java.util.ArrayList;
import java.util.List;
import com.example.finalprojectwmb.R;

public class SearchFragment extends Fragment {

    private MapView mapView;
    private FusedLocationProviderClient fusedLocationClient;
    private List<Destination> destinationList;
    private CardAdapter cardAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        Configuration.getInstance().load(getContext(), requireActivity().getPreferences(Context.MODE_PRIVATE));

        mapView = view.findViewById(R.id.map);
        mapView.setTileSource(org.osmdroid.tileprovider.tilesource.TileSourceFactory.MAPNIK);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        // Initialize destination list and adapter
        destinationList = new ArrayList<>();
        cardAdapter = new CardAdapter(getContext(), destinationList, this::onDestinationClick);

        // Set up RecyclerView for horizontal scrolling
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(cardAdapter);

        loadLocalDestinations();
        getLastLocation();

        return view;
    }

    private void getLastLocation() {
        if (requireActivity().checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(requireActivity(), location -> {
                        if (location != null) {
                            GeoPoint userLocation = new GeoPoint(location.getLatitude(), location.getLongitude());
                            mapView.getController().setCenter(userLocation);
                            mapView.getController().setZoom(15);
                            Marker marker = new Marker(mapView);
                            marker.setPosition(userLocation);
                            marker.setTitle("You are here");
                            mapView.getOverlays().add(marker);
                        }
                    });
        } else {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
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

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDetach();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }
}
