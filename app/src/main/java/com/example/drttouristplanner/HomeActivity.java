package com.example.drttouristplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.drttouristplanner.databinding.ActivityHomeBinding;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Arrays;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;
    PlacesClient placesClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (!Places.isInitialized()){
            Places.initialize(getApplicationContext(),"AIzaSyCTFEhjwd3MS971CvMG85qIFLWXEYi-Q8s");

        }

        placesClient = Places.createClient(this);

        final AutocompleteSupportFragment autocompleteSupportFragment =
                (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ID,Place.Field.LAT_LNG, Place.Field.NAME));
        autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onError(@NonNull Status status) {

            }

            @Override
            public void onPlaceSelected(@NonNull Place place) {


                final LatLng latLng = place.getLatLng();
                Toast.makeText(HomeActivity.this, ""+latLng.latitude +" "+ latLng.longitude, Toast.LENGTH_SHORT).show();
                Log.i("PlacesApi", "onPlaceSelected: "+latLng.latitude+"\n"+latLng.longitude);

            }
        });

        ReplaceFragment(new MapsFragment());

        binding.navBottomBar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.homefragment:
                        ReplaceFragment(new MapsFragment());
                        break;
                    case R.id.notificationfragment:
                        ReplaceFragment(new NotificationFragment());
                        break;
                    case R.id.profilefragment:
                        ReplaceFragment(new ProfileFragment());
                        break;

                }
                return true;
            }
        });
    }

    private void  ReplaceFragment(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }
}