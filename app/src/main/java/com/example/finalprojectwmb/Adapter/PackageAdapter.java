package com.example.finalprojectwmb.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.finalprojectwmb.R;
import com.example.finalprojectwmb.TravelPackage;
import com.example.finalprojectwmb.Activity.FormActivity;
import java.util.List;

public class PackageAdapter extends RecyclerView.Adapter<PackageAdapter.PackageViewHolder> {
    private List<TravelPackage> packageList;
    private Context context;

    public PackageAdapter(Context context, List<TravelPackage> packageList) {
        this.context = context;
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
        holder.descriptionTextView.setText(travelPackage.getDetails());
        holder.imageView.setImageResource(travelPackage.getImageResource());

        holder.applyButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, FormActivity.class);
            context.startActivity(intent);
        });
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
        Button applyButton;

        public PackageViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.packageTitle);
            priceTextView = itemView.findViewById(R.id.packagePrice);
            descriptionTextView = itemView.findViewById(R.id.packageDetails);
            imageView = itemView.findViewById(R.id.packageImage);
            applyButton = itemView.findViewById(R.id.applyButton);
        }
    }
}
