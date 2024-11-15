package com.example.finalprojectwmb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    private Context context;
    private List<Destination> destinations;

    // Constructor for CardAdapter
    public CardAdapter(Context context, List<Destination> destinations) {
        this.context = context;
        this.destinations = destinations;
    }

    // This method creates and inflates the ViewHolder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_destination, parent, false);
        return new ViewHolder(view);
    }

    // This method binds data to the ViewHolder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Destination destination = destinations.get(position);
        holder.nameTextView.setText(destination.getName());
        holder.priceTextView.setText(destination.getPrice());
        Picasso.get().load(destination.getImageUrl()).into(holder.imageView);
    }

    // Returns the number of items in the list
    @Override
    public int getItemCount() {
        return destinations.size();
    }

    // ViewHolder class for holding the views of each item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView priceTextView;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.card_title);
            priceTextView = itemView.findViewById(R.id.card_price);
            imageView = itemView.findViewById(R.id.card_image);
        }
    }
}
