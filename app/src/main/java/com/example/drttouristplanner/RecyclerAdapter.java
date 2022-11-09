package com.example.drttouristplanner;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {
    private Context mContext;
    private List<TripModel> trips;
    private OnItemClickListener mListener;

    public RecyclerAdapter(Context context, List<TripModel> uploads) {
        mContext = context;
        trips = uploads;

    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.trip_rv_item, parent, false);
        return new RecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        //
        TripModel currentTrip = trips.get(position);
        holder.spotName.setText(currentTrip.getName());
        holder.tripName.setText(currentTrip.getName());
        holder.startDate.setText(currentTrip.getName());
        holder.endDate.setText(currentTrip.getName());
        holder.description.setText(currentTrip.getName());

        Picasso.get()
                .load(currentTrip.getImageURL())
                .placeholder(R.drawable.ic_profile)
                .fit()
                .centerCrop()
                .into(holder.tripImageView);

    }

    @Override
    public int getItemCount() {
        return trips.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener, com.example.drttouristplanner.RecyclerViewHolder {

        public TextView spotName, tripName, startDate, endDate, description;
        public ImageView tripImageView;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            spotName = itemView.findViewById(R.id.idTVspotname);
            tripName = itemView.findViewById(R.id.idTVtripname);
            startDate = itemView.findViewById(R.id.idTVstartDate);
            endDate = itemView.findViewById(R.id.idTVendDate);
            description = itemView.findViewById(R.id.idTVdescription);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position);
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select Action");
            MenuItem showItem = menu.add(Menu.NONE, 1, 1, "Show");
            MenuItem deleteItem = menu.add(Menu.NONE, 1, 1, "Delete");

            showItem.setOnMenuItemClickListener(this);
            deleteItem.setOnMenuItemClickListener(this);


        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {

                    switch (item.getItemId()) {
                        case 1:
                            mListener.onShowItemClick(position);
                            return true;
                        case 2:
                            mListener.onDeleteItemClick(position);
                            return true;
                    }
                }
            }
            return false;
        }


    }

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onShowItemClick(int position);

        void onDeleteItemClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
    }


