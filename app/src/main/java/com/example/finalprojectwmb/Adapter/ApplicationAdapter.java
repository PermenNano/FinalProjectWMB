package com.example.finalprojectwmb.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.finalprojectwmb.R;
import com.example.finalprojectwmb.TravelApplication;

import java.util.List;

public class ApplicationAdapter extends RecyclerView.Adapter<ApplicationAdapter.ApplicationViewHolder> {

    private List<TravelApplication> applicationList;

    public ApplicationAdapter(List<TravelApplication> applicationList) {
        this.applicationList = applicationList;
    }

    @NonNull
    @Override
    public ApplicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.application_item, parent, false);
        return new ApplicationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApplicationViewHolder holder, int position) {
        TravelApplication application = applicationList.get(position);
        holder.nameTextView.setText(application.getName());
        holder.emailTextView.setText(application.getEmail());
        holder.phoneTextView.setText(application.getPhone());
    }

    @Override
    public int getItemCount() {
        return applicationList.size();
    }

    public static class ApplicationViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, emailTextView, phoneTextView;

        public ApplicationViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            emailTextView = itemView.findViewById(R.id.emailTextView);
            phoneTextView = itemView.findViewById(R.id.phoneTextView);
        }
    }
}
