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

public class Content_Talbak extends AppCompatActivity implements RecyclerViewInterface{


    ArrayList<ContentModel> lakeContent = new ArrayList<>();
    ArrayList<ContentModel> mountainContent = new ArrayList<>();
    ArrayList<ContentModel> fallContent = new ArrayList<>();
    ArrayList<ContentModel> defaultContent = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton10);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(Content_Talbak.this);
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
        lakeContent.add( new ContentModel(
                "mEPnKlmFqYZSXwj29tJR",
                "14.973168155727075, 121.09338223660336",
                "talbak",
                "lake",
                "Palanguyan",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed lectus dui, rhoncus eu blandit in, sollicitudin sit amet eros. Aenean vitae neque leo. Interdum et malesuada fames ac ante ipsum primis in faucibus.",
                "Show Tourist Spot Information",
                "4.6",
                new int[]{ R.drawable.palanguyan, R.drawable.palanguyan2, R.drawable.palanguyan3,R.drawable.palanguyan4}
        ));
        fallContent.add( new ContentModel(
                "zKffkLDDRaPxt2pAQq9p",
                "15.099400128531592,121.11316825202768",
                "talbak",
                "fall",
                "Verdibia Falls",
                "It is also known for the majestic Verdibia Falls named after Verdibia, a Canadian national who discovered the area. It has long been a favorite destination for nature-loving visitors.",
                "Show Tourist Spot Information",
                "5.0",
                new int[]{ R.drawable.v1,R.drawable.v2,R.drawable.v4}
        ));

        mountainContent.add( new ContentModel(
                "bJjCuu3kUR9nBSOzTxE8",
                "15.131772490837788, 121.09622728347215",
                "talbak",
                "mountain",
                "Mt.Balistada",
                "It is reputed to be the highest point in the area from which to view the stunning Sierra Madre Mountain Ranges and other picturesque locations.",
                "Show Tourist Spot Information",
                "5.0",
                new int[]{ R.drawable.balistada, R.drawable.balistada2, R.drawable.balistada3}
        ));

        mountainContent.add( new ContentModel(
                "lUoHXM30Ew3fQHVTQIg6",
                "15.113837846615471, 121.09686924789591",
                "talbak",
                "mountain",
                "Mt.Mavio",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus ullamcorper egestas odio, eget fringilla risus pharetra et. Ut fermentum convallis diam dapibus malesuada. In sagittis auctor libero, non convallis tortor vehicula sit amet.",
                "Show Tourist Spot Information",
                "5.0",
                new int[]{ R.drawable.mtmavio, R.drawable.mtmavio2, R.drawable.mtmavio3}
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
                    intent.putExtra("place_id",  fallContent.get(position).getPlaceID());
                    startActivity(intent);
                    break;
                case ("mountain"):
                    intent.putExtra("Title", mountainContent.get(position).getTitle());
                    intent.putExtra("place_id", mountainContent.get(position).getPlaceID());
                    startActivity(intent);
                    break;
                case ("lake"):
                    intent.putExtra("Title", lakeContent.get(position).getTitle());
                    intent.putExtra("place_id", lakeContent.get(position).getPlaceID());
                    startActivity(intent);
                    break;

            }
        }
    }
}