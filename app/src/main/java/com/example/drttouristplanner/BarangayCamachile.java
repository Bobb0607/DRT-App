package com.example.drttouristplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;

public class BarangayCamachile extends AppCompatActivity {
    ImageSlider imageSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barangay_camachile);


        imageSlider = findViewById(R.id.image_slider);

        ArrayList<SlideModel> images = new ArrayList<>();
        images.add(new SlideModel(R.drawable.malangaanspring, ScaleTypes.FIT));
        images.add(new SlideModel(R.drawable.simbahangbato, ScaleTypes.FIT));
        images.add(new SlideModel(R.drawable.putingbato1, ScaleTypes.FIT));
        images.add(new SlideModel(R.drawable.tungtungfalls, ScaleTypes.FIT));

        imageSlider.setImageList(images);

    }
    public void Falls(View v) {
        Intent intent = new Intent(BarangayCamachile.this, Content_Camachile.class);
        intent.putExtra("target_data", "fall");
        startActivity(intent);

    }

    public void Infastructures(View v) {
        Intent intent = new Intent(BarangayCamachile.this, Content_Camachile.class);
        intent.putExtra("target_data", "infastructure");
        startActivity(intent);

    }

    public void Mountains(View v) {
        Intent intent = new Intent(BarangayCamachile.this, Content_Camachile.class);
        intent.putExtra("target_data", "mountain");
        startActivity(intent);

    }

    public void Springs(View v) {
        Intent intent = new Intent(BarangayCamachile.this, Content_Camachile.class);
        intent.putExtra("target_data", "spring");
        startActivity(intent);

    }
}
