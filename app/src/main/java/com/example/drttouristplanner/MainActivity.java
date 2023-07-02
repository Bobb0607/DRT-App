// THIS IS HOMEPAGE!!




package com.example.drttouristplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //VARIABLES

    TextView fullName, email, phone, verifyMsg, verified, textView8;
    ImageSlider imageSlider1, imageSlider2,imageSlider3, imageSlider4;
    ;
    FirebaseAuth fAuth;
    Button resendCode;
    Button startTour, startATourDrtBtn;
    DrawerLayout drawer_layout;
    NavigationView navigationView;
    Toolbar toolbar;
    FirebaseUser user;
    FloatingActionButton floatingActionButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fAuth = FirebaseAuth.getInstance();
        imageSlider1 = findViewById(R.id.imageSlider1);
        imageSlider2 = findViewById(R.id.imageSlider2);
        imageSlider3 = findViewById(R.id.imageSlider3);
        imageSlider4 = findViewById(R.id.imageSlider4);



        //HOOKS
        resendCode = findViewById(R.id.sendVerificationCode);
        startTour = findViewById(R.id.continueBtn);
        startATourDrtBtn = findViewById(R.id.startTourDrt);
        verifyMsg = findViewById(R.id.verifyMsg);
        verified = findViewById(R.id.verified);


        ArrayList<SlideModel> images = new ArrayList<>();
        images.add(new SlideModel(R.drawable.batongpalaka, ScaleTypes.FIT));
        images.add(new SlideModel(R.drawable.batongpalaka2, ScaleTypes.FIT));
        images.add(new SlideModel(R.drawable.puningcave1, ScaleTypes.FIT));
        images.add(new SlideModel(R.drawable.secretfalls, ScaleTypes.FIT));

        imageSlider1.setImageList(images);


        ArrayList<SlideModel> images2 = new ArrayList<>();
        images2.add(new SlideModel(R.drawable.adarnafalls, ScaleTypes.FIT));
        images2.add(new SlideModel(R.drawable.balistada2, ScaleTypes.FIT));
        images2.add(new SlideModel(R.drawable.tangke, ScaleTypes.FIT));

        imageSlider2.setImageList(images2);

        ArrayList<SlideModel> images3 = new ArrayList<>();
        images3.add(new SlideModel(R.drawable.malangaanspring, ScaleTypes.FIT));
        images3.add(new SlideModel(R.drawable.tangke, ScaleTypes.FIT));
        images3.add(new SlideModel(R.drawable.secretfalls2, ScaleTypes.FIT));
        images3.add(new SlideModel(R.drawable.tangke, ScaleTypes.FIT));

        imageSlider3.setImageList(images3);

        ArrayList<SlideModel> images4 = new ArrayList<>();
        images4.add(new SlideModel(R.drawable.mtmanalmon, ScaleTypes.FIT));
        images4.add(new SlideModel(R.drawable.mtmavio, ScaleTypes.FIT));
        images4.add(new SlideModel(R.drawable.tilapilonhills, ScaleTypes.FIT));

        imageSlider4.setImageList(images4);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton2);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Tap on the Items above or in the navigation bar go to Categories or Barangay to start browsing tourist spots")
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

        bottomNavigationView.setSelectedItemId(R.id.navbottomhome);

        // Perform item selected listener
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
                        startActivity(new Intent(getApplicationContext(),Categories.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navbottomcreateTrip:
                        startActivity(new Intent(getApplicationContext(),Barangay.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navbottomprofile:
                        startActivity(new Intent(getApplicationContext(),PersonalInfo.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });


        drawer_layout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

                getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        //TOOLBAR

        setSupportActionBar(toolbar);

        //NAVIGATION DRAWER MENU
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer_layout, toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer_layout.addDrawerListener(toggle);
        toggle.syncState();

        //

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_home);

        startATourDrtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,CreateTrip.class);
                startActivity(intent);


            }
        });


        user = fAuth.getCurrentUser();
        if (!user.isEmailVerified()) {
            verifyMsg.setVisibility(View.VISIBLE);
            startTour.setVisibility(View.GONE);
            resendCode.setVisibility(View.VISIBLE);
            verified.setVisibility(View.GONE);




            resendCode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    fAuth.getCurrentUser().reload();


                    user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            fAuth.getCurrentUser().reload();
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                            builder1.setMessage("A verification link is sent to your E-mail!.");
                            builder1.setCancelable(true);

                            builder1.setPositiveButton(
                                    "Confirm",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            startActivity(new Intent(getApplicationContext(), Login.class));
                                        }
                                    });

                            AlertDialog alert11 = builder1.create();
                            alert11.show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("tag", "onFailure: verification link is not sent. " + e.getMessage());
                            Toast.makeText(MainActivity.this, "A verification link is already sent to your E-mail!", Toast.LENGTH_SHORT).show();

                        }
                    });

                }
            });


        }
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut(); //log out user
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }



    public void Continue(View view) {
        FirebaseUser user = fAuth.getCurrentUser();

        if (fAuth.getCurrentUser().isEmailVerified()) {
            Intent intent = new Intent(MainActivity.this, All_Barangay.class);
            startActivity(intent);
        } else {
            Toast.makeText(MainActivity.this, "Your E-mail is unverified!", Toast.LENGTH_SHORT).show();
        }


    }



    @Override
    public void onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)){
            drawer_layout.closeDrawer(GravityCompat.START);
        }
        else {
        }
    }


    public void Featured(View v) {
        Intent intent = new Intent(MainActivity.this, Tourist_Spots.class);
        intent.putExtra("target_data", "featured");
        startActivity(intent);

    }
    //
    public void Family(View v) {
        Intent intent = new Intent(MainActivity.this, Tourist_Spots.class);
        intent.putExtra("target_data", "family");
        startActivity(intent);

    }
    //
    public void Swimming(View v) {
        Intent intent = new Intent(MainActivity.this, Tourist_Spots.class);
        intent.putExtra("target_data", "swimming");
        startActivity(intent);

    }
    //
    public void Hiking(View v) {
        Intent intent = new Intent(MainActivity.this, Tourist_Spots.class);
        intent.putExtra("target_data", "hiking");
        startActivity(intent);

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_home:
                break;
            case R.id.nav_trips:
                Intent trip = new Intent(MainActivity.this,recyclerviewfinal.class);
                startActivity(trip);
                break;
            case R.id.nav_profile:
                Intent profile = new Intent(MainActivity.this,PersonalInfo.class);
                startActivity(profile);
                break;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut(); //log out user
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
                break;
            case R.id.nav_about_us:
                Intent aboutUs = new Intent(MainActivity.this,Developers.class);
                startActivity(aboutUs);
                break;
            case R.id.nav_rate_us:
                Toast.makeText(this, "(To be added...)", Toast.LENGTH_SHORT).show();


                break;
            case R.id.nav_contact_info:
                Intent contact_info = new Intent(MainActivity.this,ContactInfo.class);
                startActivity(contact_info);
                break;


        }
        drawer_layout.closeDrawer(GravityCompat.START);

        return true;
    }

}




