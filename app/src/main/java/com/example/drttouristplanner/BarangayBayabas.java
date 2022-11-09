package com.example.drttouristplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;

public class BarangayBayabas extends AppCompatActivity {
    ImageSlider imageSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barangay_bayabas);

        imageSlider = findViewById(R.id.image_slider);

        ArrayList<SlideModel> images = new ArrayList<>();
        images.add(new SlideModel(R.drawable.batongpalaka, ScaleTypes.FIT));
        images.add(new SlideModel(R.drawable.batongpalaka2, ScaleTypes.FIT));
        images.add(new SlideModel(R.drawable.puningcave1, ScaleTypes.FIT));

        imageSlider.setImageList(images);
    }
    public void Caves(View v) {
        Intent intent = new Intent(BarangayBayabas.this, Content_Bayabas.class);
        intent.putExtra("target_data", "caves");
        startActivity(intent);

    }
}