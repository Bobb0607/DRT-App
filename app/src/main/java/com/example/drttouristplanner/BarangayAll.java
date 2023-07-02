package com.example.drttouristplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class BarangayAll extends AppCompatActivity {


    ImageSlider imageSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_barangay);

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.navbottomhome:
                        return true;
                    case R.id.navbottomtrips:
                        startActivity(new Intent(getApplicationContext(),recyclerviewfinal.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navbottomcategories:
                        startActivity(new Intent(getApplicationContext(),All_Barangay.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navbottomcreateTrip:
                        startActivity(new Intent(getApplicationContext(),Barangay.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navbottomprofile:
                        startActivity(new Intent(getApplicationContext(),edit_profile.class));
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });
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

    public void Infrastructures(View v) {
        Intent intent = new Intent(BarangayAll.this, Content_All.class);
        intent.putExtra("target_data", "infrastructure");
        startActivity(intent);
    }
}