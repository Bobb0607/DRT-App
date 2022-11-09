package com.example.drttouristplanner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class view_trip extends AppCompatActivity implements tripRVAdapter.TripClickInterface {
    private RecyclerView tripRV;
    private ProgressBar progressBar;
    private FloatingActionButton addFAB;
    private String planID;
    FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<TripRVModal> tripRVModalArrayList;
    private RelativeLayout bottomsSheetRL;
    private tripRVAdapter tripRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_trip);
        tripRV = findViewById(R.id.idRVtrips);
        progressBar = findViewById(R.id.idPBloading);
        addFAB = findViewById(R.id.idBTNaddfab);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("trip_title");
        tripRVModalArrayList = new ArrayList<>();
        tripRVAdapter = new tripRVAdapter(tripRVModalArrayList,this,this);
        tripRV.setLayoutManager(new LinearLayoutManager(this));
        tripRV.setAdapter(tripRVAdapter);
        bottomsSheetRL = findViewById(R.id.idRLBottomsheet);
        addFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view_trip.this,CreateTrip.class));
            }
        });

        getAllTrips();
    }

    private void getAllTrips(){

        tripRVModalArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                progressBar.setVisibility(View.GONE);
                tripRVModalArrayList.add(snapshot.getValue(TripRVModal.class));
                tripRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                progressBar.setVisibility(View.GONE);
                tripRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                progressBar.setVisibility(View.GONE);
                tripRVAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                progressBar.setVisibility(View.GONE);
                tripRVAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                tripRVAdapter.notifyDataSetChanged();

            }
        });

    }

    @Override
    public void onTripClick(int position) {
        displayBottomsheet(tripRVModalArrayList.get(position));



    }

    private void displayBottomsheet(TripRVModal tripRVModal){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_dialog,bottomsSheetRL);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView tripNameTV = layout.findViewById(R.id.idTVtripname);
        TextView tripDescriptionTV = layout.findViewById(R.id.idTVdescription);
        TextView tripDate = layout.findViewById(R.id.idTVdate);
        TextView tripIV = layout.findViewById(R.id.idIVtrip);
        Button editBtn = layout.findViewById(R.id.idBtnEdit);
        Button viewDetailsBtn = layout.findViewById(R.id.idBtnViewDetails);

        tripNameTV.setText(tripRVModal.getTripName());
        tripDescriptionTV.setText(tripRVModal.getTripDescription());
        tripDate.setText(tripRVModal.getTripStartDate() + " - "+ tripRVModal.getTripEndDate());

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( view_trip.this, EditTrip.class);
                intent.putExtra("trip_title",tripRVModal);
                startActivity(intent);

            }
        });

        viewDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(tripRVModal.getTripName()));
                startActivity(intent);

            }
        });


    }
}