package com.example.drttouristplanner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AutocompletePlace extends AppCompatActivity {

    EditText editTextPlaces;
    TextView textViewPlace, textViewPlace2;
    Button saveBtnAddress, cancelBtnAddress;
    FirebaseAuth fAuth;
    FirebaseFirestore fstrore;
    FirebaseUser user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autocomplete_place);



        //assign variable
        editTextPlaces = findViewById(R.id.edit_text_place);
        textViewPlace = findViewById(R.id.text_view_places);
        textViewPlace2 = findViewById(R.id.text_view_places2);
        saveBtnAddress = findViewById(R.id.save_address_btn);
        cancelBtnAddress = findViewById(R.id.cancel_address_btn);

        //Initialize firebase

        fAuth = FirebaseAuth.getInstance();
        fstrore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();



        //initialize places
        Places.initialize(getApplicationContext(),"AIzaSyCTFEhjwd3MS971CvMG85qIFLWXEYi-Q8s");
        editTextPlaces.setFocusable(false);
        editTextPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Initialize place field list
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS,Place.Field.LAT_LNG,Place.Field.NAME);

                //Create intent
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,
                        fieldList).build(AutocompletePlace.this);
                //Start activity result
                startActivityForResult(intent, 100);
            }
        });

        cancelBtnAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),edit_profile.class));
                finish();
            }
        });

        saveBtnAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String myAddress = textViewPlace.getText().toString().trim();
                if (TextUtils.isEmpty(myAddress)) {
                    textViewPlace.setError("Your first name is required!");
                    return;
                } else {
                    //UPDATE FULL NAME
                    DocumentReference docRef = fstrore.collection("users").document(user.getUid());
                    Map<String, Object> edited = new HashMap<>();
                    edited.put("user_address", textViewPlace.getText().toString());
                    docRef.update(edited);

                    Toast.makeText(AutocompletePlace.this, "Address successfully changed", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),edit_profile.class));
                    finish();
                }
            }


        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK){
            //  When success
            // Initialize place
            Place place = Autocomplete.getPlaceFromIntent(data);
            // Set address on EditText
            editTextPlaces.setText(place.getAddress());
            // Set locality name
            textViewPlace.setText(String.format(place.getAddress()));
            // Set Latitude & Longitude
            textViewPlace2.setText(String.valueOf(place.getLatLng()));

        }else if (resultCode == AutocompleteActivity.RESULT_ERROR){
            // When error
            // Initialize status
            Status status = Autocomplete.getStatusFromIntent(data);
            // Display toast
            Toast.makeText(getApplicationContext(),status.getStatusMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}