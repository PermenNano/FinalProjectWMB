package com.example.finalprojectwmb.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.finalprojectwmb.Destination;

import com.example.finalprojectwmb.R;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private Context context;
    private List<Destination> destinationList;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(Destination destination);
    }

    public CardAdapter(Context context, List<Destination> destinationList, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.destinationList = destinationList;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_destination, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Destination destination = destinationList.get(position);
        holder.bind(destination, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return destinationList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.card_image);
            textView = itemView.findViewById(R.id.card_title);
        }

        public void bind(final Destination destination, final OnItemClickListener listener) {
            imageView.setImageResource(destination.getImageResId());
            textView.setText(destination.getName());
            itemView.setOnClickListener(v -> listener.onItemClick(destination));
        }
    }
}