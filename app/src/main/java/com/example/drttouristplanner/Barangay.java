package com.example.drttouristplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Barangay extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barangay);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Barangay.this);
                builder.setMessage("Tap on the Corresponding Barangay to view the tourist spots located in the barangay")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                // Create the AlertDialog object and return it
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.navbottomcreateTrip);

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.navbottomhome:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navbottomtrips:
                        startActivity(new Intent(getApplicationContext(),recyclerviewfinal.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navbottomprofile:
                        startActivity(new Intent(getApplicationContext(),PersonalInfo.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.navbottomcategories:
                        startActivity(new Intent(getApplicationContext(),Categories.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navbottomcreateTrip:
                        return true;

                }
                return false;
            }
        });


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
