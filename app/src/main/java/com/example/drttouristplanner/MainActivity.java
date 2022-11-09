// THIS IS HOMEPAGE!!




package com.example.drttouristplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //VARIABLES

    TextView fullName, email, phone, verifyMsg, verified, textView8;
    FirebaseAuth fAuth;
    Button resendCode;
    Button startTour, startATourDrtBtn;
    DrawerLayout drawer_layout;
    NavigationView navigationView;
    Toolbar toolbar;
    FirebaseUser user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fAuth = FirebaseAuth.getInstance();

        //HOOKS
        resendCode = findViewById(R.id.sendVerificationCode);
        startTour = findViewById(R.id.continueBtn);
        startATourDrtBtn = findViewById(R.id.startTourDrt);
        verifyMsg = findViewById(R.id.verifyMsg);
        verified = findViewById(R.id.verified);

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
            Intent intent = new Intent(MainActivity.this, Barangay.class);
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_home:
                break;
            case R.id.nav_trips:
                Intent trip = new Intent(MainActivity.this,recyclerviewfinal.class);
                startActivity(trip);
                break;

            case R.id.nav_help:
                Intent help = new Intent(MainActivity.this,Help.class);
                startActivity(help);
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




