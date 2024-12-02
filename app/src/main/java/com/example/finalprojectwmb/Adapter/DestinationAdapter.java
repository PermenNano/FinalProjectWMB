package com.example.finalprojectwmb.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalprojectwmb.Destination;

import com.example.finalprojectwmb.R;

import java.util.List;

public class DestinationAdapter extends ArrayAdapter<Destination> {
    private Context context;
    private List<Destination> destinations;

    public DestinationAdapter(Context context, List<Destination> destinations) {
        super(context, R.layout.item_destination, destinations);
        this.context = context;
        this.destinations = destinations;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_destination, parent, false);
        }

        // Bind data to views
        Destination destination = destinations.get(position);

        TextView nameTextView = convertView.findViewById(R.id.card_title);
        ImageView imageView = convertView.findViewById(R.id.card_image);

        // Set text and drawable resource
        nameTextView.setText(destination.getName());
        imageView.setImageResource(destination.getImageResId()); // Load image from drawable

        return convertView;
    }
}
