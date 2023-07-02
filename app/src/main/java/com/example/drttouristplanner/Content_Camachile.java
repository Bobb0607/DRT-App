package com.example.drttouristplanner;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Content_Camachile extends AppCompatActivity implements RecyclerViewInterface{

    ArrayList<ContentModel> fallContent = new ArrayList<>();
    ArrayList<ContentModel> infrastructureContent = new ArrayList<>();
    ArrayList<ContentModel> mountainContent = new ArrayList<>();
    ArrayList<ContentModel> springContent = new ArrayList<>();
    ArrayList<ContentModel> defaultContent = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton10);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(Content_Camachile.this);
                builder.setMessage("1.Tap on View Route to show location in maps \n" +
                                "2.Tap Create Trip to proceed to create trip for the tourist spot.")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                // Create the AlertDialog object and return it
                androidx.appcompat.app.AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


        fallContent.add( new ContentModel(
                "bCAWDI2FRZwYONZuCY6P",
                "14.986410229194332, 121.08390061357753",
                "camachile",
                "fall",
                "Tung-tung Falls",
                "Tungtong Falls is a family-owned Resort with mesmerizing rock-formation fresh flowing water.",
                "Show Tourist Spot Information",
                "4.0",
                new int[]{ R.drawable.tungtungfalls, R.drawable.tungtungfalls2, R.drawable.tungtungfalls3}
        ));
        fallContent.add( new ContentModel(
                "m5tzfo2Z0K7aRZKsNRFD",
                "14.963882549748666, 121.09132734422751",
                "camachile",
                "fall",
                "Puning Cave",
                "Puning Cave is located in barangay bayabas, Doña Remedios Trinidad, Bulacan, Philippines. It is a popular Ecological tourist attraction and the most visited caves in Central Luzon. Experience the thrill of trekking mountain slopes leading to hidden caves in Doña Remedios Trinidad.",
                "Show Tourist Spot Information",
                "5.0",
                new int[]{ R.drawable.puningcave1, R.drawable.puningcave2, R.drawable.puningcave3}
        ));

        infrastructureContent.add( new ContentModel(
                "cKyLiW6rNlKfFEr9rMGe",
                "14.99153691755392, 121.0660989259427",
                "camachile",
                "infrastructure",
                "Simbahang Bato",
                "Simbahang bato is a rock formation resembling a church.",
                "Show Tourist Spot Information",
                "5.0",
                new int[]{ R.drawable.simbahangbato, R.drawable.simbahangbato2, R.drawable.simbahangbato3,R.drawable.simbahangbato4}
        ));

        mountainContent.add( new ContentModel(
                "BpDdTBHYDXCN0U6T0ogW",
                "15.148461449894073, 120.97790017151091",
                "camachile",
                "mountain",
                "Mt. Puting Bato",
                "Mt. Puting Bato is nestled in Baranggay Guilon of Samal boasting of its soaring height of 1,345 ft above sea altitude. For those who would like to experience what it's like to reach a mountain that can be accessed easily, Puting Bato is definitely the ideal target to experience such worthwhile climb",
                "Show Tourist Spot Information",
                "5.0",
                new int[]{ R.drawable.putingbato1, R.drawable.putingbato2, R.drawable.putingbato3}
        ));

       springContent.add( new ContentModel(
               "9szjXyp3rcOjz76ngH82",
               "15.005329299497376, 121.0602965205604",
               "camachile",
               "spring",
                "Malangaan Spring",
                "Malangaan Spring and Cave upon the creation of the Municipality of DRT in 1977, both tourist destinations became part of Barangay Camachile. Visitors can still enjoy the refreshing spring water, even during the summer.",
               "Show Tourist Spot Information",
               "5.0",
               new int[]{ R.drawable.malangaanspring,R.drawable.malangaan2,R.drawable.malangaan3,R.drawable.malangaan4}
        ));

        // DEFAULT
        defaultContent.add( new ContentModel(
                "0",
                "",
                "",
                "0",
                "OOPS",
                "This page is empty",
                "Show Tourist Spot Information",
                "1.0",
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
                case("infrastructure"):
                    adapter = new ContentAdapter(infrastructureContent,this);
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
                    intent.putExtra("place_id", fallContent.get(position).getPlaceID());
                    startActivity(intent);
                    break;
                case ("mountain"):
                    intent.putExtra("Title", mountainContent.get(position).getTitle());
                    intent.putExtra("place_id", mountainContent.get(position).getPlaceID());
                    startActivity(intent);
                    break;
                case ("infrastructure"):
                    intent.putExtra("Title", infrastructureContent.get(position).getTitle());
                    intent.putExtra("place_id", infrastructureContent.get(position).getPlaceID());
                    startActivity(intent);
                    break;
                case ("spring"):
                    intent.putExtra("Title", springContent.get(position).getTitle());
                    intent.putExtra("place_id", springContent.get(position).getPlaceID());
                    startActivity(intent);
                    break;


            }
        }
    }
}