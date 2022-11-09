package com.example.drttouristplanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class tripRVAdapter extends RecyclerView.Adapter<tripRVAdapter.ViewHolder> {
    int lastPos = -1;
    private ArrayList<TripRVModal> tripRVModalArrayList;
    private Context context;
    private TripClickInterface tripClickInterface;

    public tripRVAdapter(ArrayList<TripRVModal> tripRVModalArrayList, Context context, TripClickInterface tripClickInterface) {
        this.tripRVModalArrayList = tripRVModalArrayList;
        this.context = context;
        this.tripClickInterface = tripClickInterface;
    }

    @NonNull
    @Override
    public tripRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.trip_rv_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull tripRVAdapter.ViewHolder holder, int position) {
        TripRVModal tripRVModal = tripRVModalArrayList.get(position);
        holder.triptitleTV.setText(tripRVModal.getTripTitle());
        holder.tripstartTV.setText(tripRVModal.getTripStartDate() +" - "+ tripRVModal.getTripEndDate());

        //image
        //Picasso.get().load(tripRVModal.getTripIMG()).into(holder.tripIV);
        setAnimation(holder.itemView,position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tripClickInterface.onTripClick(position);
            }
        });


    }

    private void setAnimation (View itemView, int position) {
        if (position > lastPos){
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastPos = position;
        }
    }
    @Override
    public int getItemCount() {
        return tripRVModalArrayList.size();

    }

    public interface TripClickInterface{
        void onTripClick(int position);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView triptitleTV,tripstartTV,tripendTV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            triptitleTV = itemView.findViewById(R.id.idTVtripname);
            tripstartTV = itemView.findViewById(R.id.idTVstartDate);
            tripendTV = itemView.findViewById(R.id.idTVendDate);

        }
    }

}
