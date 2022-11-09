package com.example.drttouristplanner;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.Serializable;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.drttouristplanner.R;

import java.util.ArrayList;
import java.util.List;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ViewHolder>{
    private final RecyclerViewInterface recyclerViewInterface;
    private List<ContentModel> mContents;

    public void setFilteredList(List<ContentModel> filteredList) {
        this.mContents = filteredList;
        notifyDataSetChanged();

    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView titleTextView;
        public TextView descriptionTextView;
        public ImageSlider displayImage;
        public CardView cardView;
        public Button button;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView, RecyclerViewInterface recyclerViewInterface) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            titleTextView = (TextView) itemView.findViewById(R.id.tvTitle);
            descriptionTextView = (TextView) itemView.findViewById(R.id.tvDescription);
            displayImage = (ImageSlider) itemView.findViewById(R.id.ivBanner);
            cardView = (CardView) itemView.findViewById(R.id.cardview);
            button = (Button) itemView.findViewById(R.id.create_trip_button);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerViewInterface != null){
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });

        }
    }


    // Pass in the contact array into the constructor
    public ContentAdapter(List<ContentModel> content,
    RecyclerViewInterface recyclerViewInterface) {
        mContents = content;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contentView = inflater.inflate(R.layout.custom_content_layout, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contentView, recyclerViewInterface);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContentAdapter.ViewHolder holder, int position) {
        ContentModel model = mContents.get(position);


        // Set item views based on your views and data model
        holder.titleTextView.setText(model.getTitle());
        holder.descriptionTextView.setText(model.getDetails());
        holder.displayImage.setImageList(model.getImage());
        holder.cardView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.card_anim));
    }

    @Override
    public int getItemCount() {
        return mContents.size();
    }

}
