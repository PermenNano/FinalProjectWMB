package com.example.finalprojectwmb;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
        TextView priceTextView = convertView.findViewById(R.id.card_price);
        ImageView imageView = convertView.findViewById(R.id.card_image);

        nameTextView.setText(destination.getName());
        priceTextView.setText(destination.getPrice());
        Picasso.get().load(destination.getImageUrl()).into(imageView);

        return convertView;
    }
}
