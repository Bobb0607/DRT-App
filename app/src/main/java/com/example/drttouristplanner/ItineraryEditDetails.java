package com.example.drttouristplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ItineraryEditDetails extends AppCompatActivity {



    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_place_rating);



    }


    private void updateItinerary(String ReviewID, String rating_value, String review_value, String user_id, String place_id, String date_time_rating, View v) {


        Map<String,Object> map_updated = new HashMap<>();
        map_updated.put("date_time_rating", ""+date_time_rating+"");
        map_updated.put("place_id", ""+place_id+"");
        map_updated.put("user_id", ""+user_id+"");
        map_updated.put("ratings", ""+rating_value+"");
        map_updated.put("review", ""+review_value+"");

        fStore = FirebaseFirestore.getInstance();
        fStore.collection("reviews").
                        document(ReviewID).
                        set(map_updated).
                        addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(v.getContext(),"Rating and Reviews\nSuccessfully Changed!",Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            // inside on failure method we are
            // displaying a failure message.
            @Override
            public void onFailure(@NonNull Exception e) {
                //Toast.makeText(UpdateCourse.this, "Fail to update the data..", Toast.LENGTH_SHORT).show();
            }
        });
    }
}