package com.example.drttouristplanner;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Content_Camachin extends AppCompatActivity implements RecyclerViewInterface {

    ArrayList<ContentModel> fallContent = new ArrayList<>();
    ArrayList<ContentModel> infastructureContent = new ArrayList<>();
    ArrayList<ContentModel> defaultContent = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content);

        // =================================
        // =========== Dito yung data
        // CATTOS
        fallContent.add( new ContentModel(
                "Adarna Falls",
                "formerly known as 13 falls, Adarna got its name for it is once known to be the home of birds known by the locals as adarna.",
                new int[]{ R.drawable.adarnafalls, R.drawable.adarnafalls2, R.drawable.adarnafalls3}
        ));
        fallContent.add( new ContentModel(
                "Secret Falls",
                ", Secret Falls serves as one of the pioneer nature-based tourist destinations. Visitors enjoy the clear running water during September and February.",
                new int[]{ R.drawable.secretfalls, R.drawable.secretfalls2, R.drawable.secretfalls3}
        ));

        infastructureContent.add( new ContentModel(
                "Candle Monument",
                ". At the peak of Mt. Lumot, a candle-like monument was created to symbolize light.",
                new int[]{ R.drawable.candlemonument}
        ));
        // DEFAULT
        defaultContent.add( new ContentModel(
                "OOPS",
                "This page is empty",
                new int[]{ R.drawable.talonlukab }
        ));




        // =================================
        // =========== Eto yung recycler view
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            RecyclerView rvContacts = (RecyclerView) this.findViewById(R.id.rvContent);
            ContentAdapter adapter;
            String data = extras.getString("target_data");
            switch (data) {
                case("fall"):
                    adapter = new ContentAdapter(fallContent,this);
                    break;
                case("infastructure"):
                    adapter = new ContentAdapter(infastructureContent,this);
                    break;

                default:
                    //Default
                    adapter = new ContentAdapter(defaultContent,this);
            }
            rvContacts.setAdapter(adapter);
            rvContacts.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    @Override
    public void onItemClick(int position) {

        Intent intent = new Intent(Content_Camachin.this, CreateTrip.class);

        Bundle extras = getIntent().getExtras();
        String data = extras.getString("target_data");
        if (extras != null) {
            switch (data) {
                case("fall"):
                    intent.putExtra("Title", fallContent.get(position).getTitle());
                    startActivity(intent);
                    break;

                case ("infrastructure"):
                    intent.putExtra("Title", infastructureContent.get(position).getTitle());
                    startActivity(intent);
                    break;
            }
        }
    }
}