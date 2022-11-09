package com.example.drttouristplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.ArrayMap;

import com.example.drttouristplanner.ContentAdapter;
import com.example.drttouristplanner.ContentModel;
import com.example.drttouristplanner.ContentModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;

public class Content_Bayabas extends AppCompatActivity implements RecyclerViewInterface, Serializable {

    ArrayList<ContentModel> caveContent = new ArrayList<>();
    ArrayList<ContentModel> dogContent = new ArrayList<>();
    ArrayList<ContentModel> defaultContent = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content);

        // =================================
        // =========== Dito yung data
        // CATTOS
        caveContent.add( new ContentModel(
                "Batong Palaka",
                "located in South. Brgy. Silid When viewed from atop Bayabas, Batong Palaka, the rock formation looks like a frog.",
                new int[]{ R.drawable.batongpalaka, R.drawable.batongpalaka2}
        ));
        caveContent.add( new ContentModel(
                "Puning Cave",
                "In addition to being spectacular and well-preserved, the cave features a spring that flows from a mountain.",
                new int[]{ R.drawable.puningcave1, R.drawable.puningcave2, R.drawable.puningcave3}
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
                case("caves"):
                    adapter = new ContentAdapter(caveContent,this);
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

        Intent intent = new Intent(Content_Bayabas.this, CreateTrip.class);

        Bundle extras = getIntent().getExtras();
        String data = extras.getString("target_data");
        if (extras != null) {
            switch (data) {
                case ("cave"):
                    intent.putExtra("Title", caveContent.get(position).getTitle());
                    startActivity(intent);
                    break;
            }
        }
    }
}