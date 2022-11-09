package com.example.drttouristplanner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.denzcoskun.imageslider.ImageSlider;

public class itenirary_layout extends AppCompatActivity {
       TextView tripTitle, starttime, textView56;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itenirary_layout);

        ImageView imageView = findViewById(R.id.imageView10);
        TextView tripTitle = findViewById(R.id.textView60);
        TextView starttime = findViewById(R.id.textView61);
        TextView Endtime = findViewById(R.id.textView56);


        Intent intent = getIntent();


        String title = intent.getStringExtra("name");
        String startTime = intent.getStringExtra("start");
        String endTime =  intent.getStringExtra("end");

        tripTitle.setText(title);
        starttime.setText(startTime);
        Endtime.setText(endTime);



    }
}
