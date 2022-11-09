package com.example.drttouristplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;

public class BarangayAll extends AppCompatActivity {


    ImageSlider imageSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_barangay);

        imageSlider = findViewById(R.id.image_slider);

        ArrayList<SlideModel> images = new ArrayList<>();
        images.add(new SlideModel(R.drawable.talonnieba, ScaleTypes.FIT));
        images.add(new SlideModel(R.drawable.mtmanalmon, ScaleTypes.FIT));
        images.add(new SlideModel(R.drawable.palanguyan, ScaleTypes.FIT));
        images.add(new SlideModel(R.drawable.pantingancave, ScaleTypes.FIT));
        images.add(new SlideModel(R.drawable.simbahangbato, ScaleTypes.FIT));
        images.add(new SlideModel(R.drawable.malangaanspring, ScaleTypes.FIT));


        imageSlider.setImageList(images);
    }

    public void Falls(View v) {
        Intent intent = new Intent(BarangayAll.this, Content_All.class);
        intent.putExtra("target_data", "fall");
        startActivity(intent);

    }

    public void Rivers(View v) {
        Intent intent = new Intent(BarangayAll.this, Content_All.class);
        intent.putExtra("target_data", "river");
        startActivity(intent);

    }

    public void Mountains(View v) {
        Intent intent = new Intent(BarangayAll.this, Content_All.class);
        intent.putExtra("target_data", "mountain");
        startActivity(intent);

    }

    public void Caves(View v) {
        Intent intent = new Intent(BarangayAll.this, Content_All.class);
        intent.putExtra("target_data", "cave");
        startActivity(intent);

    }

    public void Hills(View v) {
        Intent intent = new Intent(BarangayAll.this, Content_All.class);
        intent.putExtra("target_data", "hill");
        startActivity(intent);

    }

    public void Springs(View v) {
        Intent intent = new Intent(BarangayAll.this, Content_All.class);
        intent.putExtra("target_data", "spring");
        startActivity(intent);
    }

    public void Lake(View v) {
        Intent intent = new Intent(BarangayAll.this, Content_All.class);
        intent.putExtra("target_data", "lake");
        startActivity(intent);
    }

    public void Infrastructure(View v) {
        Intent intent = new Intent(BarangayAll.this, Content_All.class);
        intent.putExtra("target_data", "infrastructure");
        startActivity(intent);
    }
}