package com.example.drttouristplanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class iteniraryAdapter extends RecyclerView.Adapter<iteniraryAdapter.ViewHolder>{

    private ArrayList<iteniraryModel> iteniraryModels;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name, start, end;
        public ImageView image;
        public CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.textView60);
            start = itemView.findViewById(R.id.textView61);
            end = itemView.findViewById(R.id.textView56);
            image = itemView.findViewById(R.id.imageView10);
            cardView = itemView.findViewById(R.id.cardview);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contentView = inflater.inflate(R.layout.itenirary_layout, parent, false);

        iteniraryAdapter.ViewHolder ViewHolder = new iteniraryAdapter.ViewHolder(contentView);
        return ViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        iteniraryModel currentItem = iteniraryModels.get(position);

        holder.name.setText(currentItem.getName());
        holder.start.setText(currentItem.getStart());
        holder.end.setText(currentItem.getEnd());
        holder.image.setImageResource(currentItem.getImage());

    }

    @Override
    public int getItemCount() {
        return iteniraryModels.size();
    }

}
