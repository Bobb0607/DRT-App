package com.example.drttouristplanner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class BarangayBayabas extends AppCompatActivity {
    ImageSlider imageSlider;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barangay_bayabas);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton3);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(BarangayBayabas.this);
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