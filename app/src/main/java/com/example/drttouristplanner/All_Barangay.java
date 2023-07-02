package com.example.drttouristplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.denzcoskun.imageslider.ImageSlider;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

public class All_Barangay extends AppCompatActivity {


    ImageSlider imageSlider;
    FloatingActionButton floatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_barangay);

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.navbottomhome);

        FloatingActionButton floatingactionbutton = findViewById(R.id.floatingActionButton2);
        floatingactionbutton.bringToFront();
        floatingactionbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton2);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(All_Barangay.this, "Clicked", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(All_Barangay.this);
                builder.setMessage("Tap on the Corresponding Categories to view the tourist spot")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                // Create the AlertDialog object and return it
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
*/
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
                        return true;
                    case R.id.navbottomcreateTrip:
                        startActivity(new Intent(getApplicationContext(),Barangay.class));
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });
    }
//

    public void Falls(View v) {
        Intent intent = new Intent(All_Barangay.this, Content_All.class);
        intent.putExtra("target_data", "fall");
        startActivity(intent);

    }
//
    public void Rivers(View v) {
        Intent intent = new Intent(All_Barangay.this, Content_All.class);
        intent.putExtra("target_data", "river");
        startActivity(intent);

    }
//
    public void Mountains(View v) {
        Intent intent = new Intent(All_Barangay.this, Content_All.class);
        intent.putExtra("target_data", "mountain");
        startActivity(intent);

    }
//
    public void Caves(View v) {
        Intent intent = new Intent(All_Barangay.this, Content_All.class);
        intent.putExtra("target_data", "cave");
        startActivity(intent);

    }
//
    public void Hills(View v) {
        Intent intent = new Intent(All_Barangay.this, Content_All.class);
        intent.putExtra("target_data", "hill");
        startActivity(intent);

    }

    public void Springs(View v) {
        Intent intent = new Intent(All_Barangay.this, Content_All.class);
        intent.putExtra("target_data", "spring");
        startActivity(intent);
    }

    public void Lake(View v) {
        Intent intent = new Intent(All_Barangay.this, Content_All.class);
        intent.putExtra("target_data", "lake");
        startActivity(intent);
    }

    public void Infrastructure(View v) {
        Intent intent = new Intent(All_Barangay.this, Content_All.class);
        intent.putExtra("target_data", "infrastructure");
        startActivity(intent);
    }
}
