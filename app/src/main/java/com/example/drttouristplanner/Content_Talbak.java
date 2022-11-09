package com.example.drttouristplanner;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Content_Talbak extends AppCompatActivity implements RecyclerViewInterface{


    ArrayList<ContentModel> lakeContent = new ArrayList<>();
    ArrayList<ContentModel> mountainContent = new ArrayList<>();
    ArrayList<ContentModel> fallContent = new ArrayList<>();
    ArrayList<ContentModel> defaultContent = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content);

        lakeContent.add( new ContentModel(
                "Palanguyan",
                "At the peak of Mt. Lumot, a candle-like monument was created to symbolize light.",
                new int[]{ R.drawable.palanguyan, R.drawable.palanguyan2, R.drawable.palanguyan3,R.drawable.palanguyan4}
        ));
        fallContent.add( new ContentModel(
                "Verdibia Falls",
                "A hidden beauty with a serene splendour.",
                new int[]{ R.drawable.verdibia}
        ));

        mountainContent.add( new ContentModel(
                "Mt.Balistada",
                "It is reputed to be the highest point in the area from which to view the stunning Sierra Madre Mountain Ranges and other picturesque locations.",
                new int[]{ R.drawable.balistada, R.drawable.balistada2, R.drawable.balistada3}
        ));

        mountainContent.add( new ContentModel(
                "Mt.Mavio",
                ". At the peak of Mt. Lumot, a candle-like monument was created to symbolize light.",
                new int[]{ R.drawable.mtmavio, R.drawable.mtmavio2, R.drawable.mtmavio3}
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
                case("mountain"):
                    adapter = new ContentAdapter(mountainContent,this);
                    break;
                case("fall"):
                    adapter = new ContentAdapter(fallContent,this);
                    break;
                case("lake"):
                    adapter = new ContentAdapter(lakeContent,this);
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
        Intent intent = new Intent(Content_Talbak.this, CreateTrip.class);

        Bundle extras = getIntent().getExtras();
        String data = extras.getString("target_data");
        if (extras != null) {
            switch (data) {
                case("fall"):
                    intent.putExtra("Title", fallContent.get(position).getTitle());
                    startActivity(intent);
                    break;
                case ("mountain"):
                    intent.putExtra("Title", mountainContent.get(position).getTitle());
                    startActivity(intent);
                    break;
                case ("lake"):
                    intent.putExtra("Title", lakeContent.get(position).getTitle());
                    startActivity(intent);
                    break;

            }
        }
    }
}