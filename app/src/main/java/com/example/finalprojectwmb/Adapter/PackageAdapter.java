package com.example.finalprojectwmb.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalprojectwmb.R;
import com.example.finalprojectwmb.TravelPackage; // Ensure you import the correct class

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PackageAdapter extends RecyclerView.Adapter<PackageAdapter.PackageViewHolder> {

    private List<TravelPackage> packageList; // Corrected variable name

    public PackageAdapter(List<TravelPackage> packageList) { // Updated constructor parameter type
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
        TravelPackage pkg = packageList.get(position);
        holder.packageName.setText(pkg.getName());
        holder.packagePrice.setText(pkg.getPrice());
        holder.packageDetails.setText(pkg.getDetails());
        holder.imageView.setImageResource(pkg.getImageResource()); // Set the image resource
    }


    @Override
    public int getItemCount() {
        return packageList.size();
    }

    public static class PackageViewHolder extends RecyclerView.ViewHolder {
        TextView packageName, packagePrice, packageDetails;
        Button applyButton;
        ImageView imageView;

        public PackageViewHolder(@NonNull View itemView) {
            super(itemView);
            packageName = itemView.findViewById(R.id.packageName);
            packagePrice = itemView.findViewById(R.id.packagePrice);
            packageDetails = itemView.findViewById(R.id.packageDetails);
            applyButton = itemView.findViewById(R.id.applyButton);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
