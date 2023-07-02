package com.example.drttouristplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.ArrayMap;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drttouristplanner.ContentAdapter;
import com.example.drttouristplanner.ContentModel;
import com.example.drttouristplanner.ContentModel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;

public class Content_Bayabas extends AppCompatActivity implements RecyclerViewInterface, Serializable{

    ArrayList<ContentModel> caveContent = new ArrayList<>();
    ArrayList<ContentModel> dogContent = new ArrayList<>();
    ArrayList<ContentModel> defaultContent = new ArrayList<>();

    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private FirebaseUser user;
    private String tourist_ID;
    private Boolean oke;

    private final static int LOCATION_REQUEST_CODE = 23;
    boolean locationPermission=false;

    Location myLocation=null;

    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    String latitude, longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton10);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(Content_Bayabas.this);
                builder.setMessage("1.Tap on View Route to show location in maps \n" +
                                "2.Tap Create Trip to proceed to create trip for the tourist spot.")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                // Create the AlertDialog object and return it
                androidx.appcompat.app.AlertDialog dialog = builder.create();
                dialog.show();
            }
        });



        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        tourist_ID = fAuth.getCurrentUser().getUid();

        caveContent.add( new ContentModel(
                "VvLL7YkuTZqcgIQ1WLKT",
                "14.956091357068864, 121.08980264086861",
                "bayabas",
                "caves",
                "Batong Palaka",
                "located in South Brgy. Silid, When viewed from atop Bayabas, Batong Palaka, the rock formation looks like a frog.",
                "Show Tourist Spot Information",
                "4.5",
                new int[]{ R.drawable.batongpalaka, R.drawable.batongpalaka2}
        ));

        caveContent.add( new ContentModel(
                "m5tzfo2Z0K7aRZKsNRFD",
                "14.962362984827053, 121.09206889673732",
                "bayabas",
                "caves",
                "Puning Cave",
                "In addition to being spectacular and well-preserved, the cave features a spring that flows from a mountain.",
                "Show Tourist Spot Information",
                "5.0",
                new int[]{ R.drawable.puningcave1, R.drawable.puningcave2, R.drawable.puningcave3}
        ));


        // DEFAULT
        defaultContent.add( new ContentModel(
                "0",
                "",
                "",
                "0",
                "OOPS",
                "This page is empty",
                "Show Tourist Spot Information",
                "0.0",
                new int[]{ R.drawable.talonlukab }
        ));

        // =================================
        // =========== Eto yung recycler view
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            RecyclerView rvContacts = (RecyclerView) this.findViewById(R.id.rvContent);
            ContentAdapter adapter;
            String data = extras.getString("target_data");
            switch (data) {
                case("caves"):
                    adapter = new ContentAdapter(caveContent,this);
                    break;
                default:
                    //Default
                    adapter = new ContentAdapter(defaultContent,this);
            }
            rvContacts.setAdapter(adapter);
            rvContacts.setLayoutManager(new LinearLayoutManager(this));
        }

    }



    public void initGetLocation(){
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            OnGPS();
        } else {
            getLocation();
        }
    }

    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new  DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                Content_Bayabas.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                Content_Bayabas.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                double lat = locationGPS.getLatitude();
                double longi = locationGPS.getLongitude();
                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);

                Toast.makeText(getApplicationContext(),"Latitude: "+latitude+" Longhitude:"+longitude+"", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onItemClick(int position) {



        Intent intent = new Intent(Content_Bayabas.this, CreateTrip.class);

        Bundle extras = getIntent().getExtras();
        String data = extras.getString("target_data");
        if (extras != null) {
            switch (data) {
                case ("caves"):

                    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        OnGPS();
                    } else {
                        getLocation();
                    }

                    intent.putExtra("Title", caveContent.get(position).getTitle());
                    intent.putExtra("place_id", caveContent.get(position).getPlaceID());
                    startActivity(intent);
                    break;
            }
        }
    }


}