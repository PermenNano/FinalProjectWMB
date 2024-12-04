package com.example.finalprojectwmb.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.finalprojectwmb.R;
import com.example.finalprojectwmb.TravelPackage;
import java.util.List;

public class PackageAdapter extends RecyclerView.Adapter<PackageAdapter.PackageViewHolder> {
    private List<TravelPackage> packageList;

    public PackageAdapter(List<TravelPackage> packageList) {
        this.packageList = packageList;
    }

    @NonNull
    @Override
    public PackageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_package, parent, false);
        return new PackageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PackageViewHolder holder, int position) {
        TravelPackage travelPackage = packageList.get(position);
        holder.titleTextView.setText(travelPackage.getTitle());
        holder.priceTextView.setText(travelPackage.getPrice());
        holder.descriptionTextView.setText(travelPackage.getDetails()); // Ensure this matches your TravelPackage class
        holder.imageView.setImageResource(travelPackage.getImageResource());
    }

    @Override
    public int getItemCount() {
        return packageList.size();
    }

    public static class PackageViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView priceTextView;
        TextView descriptionTextView;
        ImageView imageView;

        public PackageViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.packageTitle); // Ensure this ID matches your layout
            priceTextView = itemView.findViewById(R.id.packagePrice); // Ensure this ID matches your layout
            descriptionTextView = itemView.findViewById(R.id.packageDetails); // Updated to match your layout
            imageView = itemView.findViewById(R.id.packageImage); // Ensure this ID matches your layout
        }
    }
}
