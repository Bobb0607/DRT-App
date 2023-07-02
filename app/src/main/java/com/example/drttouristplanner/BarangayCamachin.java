package com.example.drttouristplanner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class BarangayCamachin extends AppCompatActivity {
    ImageSlider imageSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barangay_camachin);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton5);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(BarangayCamachin.this);
                builder.setMessage("Tap on the buttons to view the tourist spot according to their categories")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                // Create the AlertDialog object and return it
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        imageSlider = findViewById(R.id.image_slider);

        ArrayList<SlideModel> images = new ArrayList<>();
        images.add(new SlideModel(R.drawable.adarnafalls2, ScaleTypes.FIT));
        images.add(new SlideModel(R.drawable.secretfalls, ScaleTypes.FIT));
        images.add(new SlideModel(R.drawable.candlemonument, ScaleTypes.FIT));

        imageSlider.setImageList(images);

    }
    public void Falls(View v) {
        Intent intent = new Intent(BarangayCamachin.this, Content_Camachin.class);
        intent.putExtra("target_data", "fall");
        startActivity(intent);

    }
    public void Infastructures(View v) {
        Intent intent = new Intent(BarangayCamachin.this, Content_Camachin.class);
        intent.putExtra("target_data", "infrastructure");
        startActivity(intent);
    }
}