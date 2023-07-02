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

public class Content_Camachin extends AppCompatActivity implements RecyclerViewInterface {

    ArrayList<ContentModel> fallContent = new ArrayList<>();
    ArrayList<ContentModel> infrastructureContent = new ArrayList<>();
    ArrayList<ContentModel> defaultContent = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton10);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(Content_Camachin.this);
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
                "byZPLhQ9KWPWoI7f9fTw",
                "15.092696155312364, 121.13292650233358",
                "camachin",
                "fall",
                "Adarna Falls",
                "Formerly known as 13 falls, Adarna got its name for it is once known to be the home of birds known by the locals as adarna.",
                "Show Tourist Spot Information",
                "5.0",
                new int[]{ R.drawable.adarnafalls, R.drawable.adarnafalls2, R.drawable.adarnafalls3}
        ));
        fallContent.add( new ContentModel(
                "osTATY3gSlbywzsw7Qp9",
                "14.985858759377104, 121.09569085137673",
                "camachin",
                "fall",
                "Secret Falls",
                "Secret Falls serves as one of the pioneer nature-based tourist destinations. Visitors enjoy the clear running water during September and February.",
                "Show Tourist Spot Information",
                "5.0",
                new int[]{R.drawable.secret9, R.drawable.secret7,R.drawable.secret1}
        ));

        infrastructureContent.add( new ContentModel(
                "EZnpXaGHu8nUaIrXFmtA",
                "15.06331948295018, 121.10681230579127",
                "camachin",
                "infrastructure",
                "Candle Monument",
                "At the peak of Mt. Lumot, a candle-like monument was created to symbolize light.",
                "Show Tourist Spot Information",
                "5.0",
                new int[]{ R.drawable.candlemonument,R.drawable.candlemonument3,R.drawable.candlemonument2}
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
                "5.0",
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
                    intent.putExtra("place_id", fallContent.get(position).getPlaceID());
                    startActivity(intent);
                    break;

                case ("infrastructure"):
                    intent.putExtra("Title", infrastructureContent.get(position).getTitle());
                    intent.putExtra("place_id", infrastructureContent.get(position).getPlaceID());
                    startActivity(intent);
                    break;
            }
        }
    }
}