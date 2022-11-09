package com.example.drttouristplanner;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Content_Camachile extends AppCompatActivity implements RecyclerViewInterface{

    ArrayList<ContentModel> fallContent = new ArrayList<>();
    ArrayList<ContentModel> infastructureContent = new ArrayList<>();
    ArrayList<ContentModel> mountainContent = new ArrayList<>();
    ArrayList<ContentModel> springContent = new ArrayList<>();
    ArrayList<ContentModel> defaultContent = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content);

        // =================================
        // =========== Dito yung data
        // CATTOS
        fallContent.add( new ContentModel(
                "Tung-tung Falls",
                "Tungtong Falls is a family-owned Resort with mesmerizing rock-formation fresh flowing water.",
                new int[]{ R.drawable.tungtungfalls, R.drawable.tungtungfalls2, R.drawable.tungtungfalls3}
        ));
        fallContent.add( new ContentModel(
                "Puning Cave",
                "In addition to being spectacular and well-preserved, the cave features a spring that flows from a mountain.",
                new int[]{ R.drawable.puningcave1, R.drawable.puningcave2, R.drawable.puningcave3}
        ));

        infastructureContent.add( new ContentModel(
                "Simbahang Bato",
                "is a rock formation resembling a church.",
                new int[]{ R.drawable.simbahangbato, R.drawable.simbahangbato2, R.drawable.simbahangbato3}
        ));

        mountainContent.add( new ContentModel(
                "Mt. Puting Bato",
                "is a rock formation resembling a church.",
                new int[]{ R.drawable.putingbato1, R.drawable.putingbato2, R.drawable.putingbato3}
        ));

       springContent.add( new ContentModel(
                "Malangaan Spring",
                "Malangaan Spring and Cave upon the creation of the Municipality of DRT in 1977, both tourist destinations became part of Barangay Camachile. Visitors can still enjoy the refreshing spring water, even during the summer.",
                new int[]{ R.drawable.malangaanspring}
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
                case("mountain"):
                    adapter = new ContentAdapter(mountainContent,this);
                    break;
                case("spring"):
                    adapter = new ContentAdapter(springContent,this);
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

        Intent intent = new Intent(Content_Camachile.this, CreateTrip.class);

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
                case ("spring"):
                    intent.putExtra("Title", springContent.get(position).getTitle());
                    startActivity(intent);
                    break;


            }
        }
    }
}