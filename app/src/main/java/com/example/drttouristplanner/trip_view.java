package com.example.drttouristplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class trip_view extends AppCompatActivity{

    RecyclerView recyclerView;
    iteniraryAdapter iteniraryAdapter;
    ArrayList<iteniraryModel> iteniraryModels = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_view);
        recyclerView = findViewById(R.id.recyclerView);

        Intent intent = getIntent();


        String title = intent.getStringExtra("name");
        String startTime = intent.getStringExtra("start");
        String endTime =  intent.getStringExtra("end");




       //iteniraryModels.add(new iteniraryModel(title,startTime,endTime));

        iteniraryModels.add(new iteniraryModel(title,startTime,endTime, R.drawable.baggage));

        iteniraryAdapter = new iteniraryAdapter(iteniraryModels, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(iteniraryAdapter);


    }
}