package com.example.drttouristplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Barangay extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barangay);


    }
    public void All(View v) {
        Intent intent = new Intent(Barangay.this, BarangayAll.class);
        startActivity(intent);
    }
    public void Bayabas(View v) {
        Intent intent = new Intent(Barangay.this, BarangayBayabas.class);
        startActivity(intent);
    }
    public void Camachile(View v) {

        startActivity(new Intent(getApplicationContext(),BarangayCamachile.class));

    }

    public void Camachin(View v) {

        startActivity(new Intent(getApplicationContext(),BarangayCamachin.class));

    }

    public void Kalawakan(View v) {

        startActivity(new Intent(getApplicationContext(),BarangayKalawakan.class));

    }

    public void Talbak(View v) {

        startActivity(new Intent(getApplicationContext(),BarangayTalbak.class));

    }
}
