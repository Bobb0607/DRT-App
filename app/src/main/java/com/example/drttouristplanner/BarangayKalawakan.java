package com.example.drttouristplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;

public class BarangayKalawakan extends AppCompatActivity {
    ImageSlider imageSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barangay_kalawakan);


        imageSlider = findViewById(R.id.image_slider);

        ArrayList<SlideModel> images = new ArrayList<>();
        images.add(new SlideModel(R.drawable.balistada, ScaleTypes.FIT));
        images.add(new SlideModel(R.drawable.mtmanalmon, ScaleTypes.FIT));
        images.add(new SlideModel(R.drawable.balistada3, ScaleTypes.FIT));
        images.add(new SlideModel(R.drawable.pantingancave, ScaleTypes.FIT));
        images.add(new SlideModel(R.drawable.talonnipedro2,ScaleTypes.FIT));


        imageSlider.setImageList(images);
    }
    public void Falls(View v) {
        Intent intent = new Intent(BarangayKalawakan.this, Content_Kalawakan.class);
        intent.putExtra("target_data", "fall");
        startActivity(intent);

    }

    public void Rivers(View v) {
        Intent intent = new Intent(BarangayKalawakan.this, Content_Kalawakan.class);
        intent.putExtra("target_data", "river");
        startActivity(intent);

    }

    public void Mountains(View v) {
        Intent intent = new Intent(BarangayKalawakan.this, Content_Kalawakan.class);
        intent.putExtra("target_data", "mountain");
        startActivity(intent);

    }

    public void Caves(View v) {
        Intent intent = new Intent(BarangayKalawakan.this, Content_Kalawakan.class);
        intent.putExtra("target_data", "cave");
        startActivity(intent);

    }
    public void Hills(View v) {
        Intent intent = new Intent(BarangayKalawakan.this, Content_Kalawakan.class);
        intent.putExtra("target_data", "hill");
        startActivity(intent);

    }
}