package com.example.drttouristplanner;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateTrip extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private DatePickerDialog datePickerDialog;
    private DatePickerDialog datePickerDialog2;
    private Button startDate;
    private Button endDate;
    private Button chooseImg;
    private String tripID;
    private String placeID;
    private TripRVModal tripRVModal;
    private Uri mImageUri;


    //firebase
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mDatabaseRef;
    private StorageReference mStorageRef;

    EditText TripName;
    EditText desc;
    TextView tripTitle;
    ImageView imageView;
    FirebaseAuth fAuth;
    FirebaseUser fUser;



    ArrayList<ContentModel> defaultContent = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);
        Intent intent = new Intent(CreateTrip.this, CreateTrip.class);



        Button save = findViewById(R.id.save_trip_button);
        Button cancel = findViewById(R.id.cancel_tripCreation_button);
        tripTitle = findViewById(R.id.tripTitle);
        TripName = findViewById(R.id.TripName);
        desc = findViewById(R.id.EDdesc);
        startDate = findViewById(R.id.EDstartDate);
        endDate = findViewById(R.id.EDendDate);
        ImageView imageView = findViewById(R.id.tripIMG);
        ProgressBar progressBar = findViewById(R.id.idPBloading);
        chooseImg = findViewById(R.id.chooseImgBtn);
        ActivityResultLauncher<String> mTakePhoto;


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton8);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(CreateTrip.this);
                builder.setMessage("1.Tap on Trip Name and input desired name for the trip \n 2.Tap on the Notes and input desired description or note and tap save if you are done")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                // Create the AlertDialog object and return it
                androidx.appcompat.app.AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


        initDatePicker();
        initDatePicker2();

        startDate.setText(getTodaysDate());
        endDate.setText(getTodaysDate());




        //array list ng Integer (image)

        ArrayList<Integer> Images = getIntent().getIntegerArrayListExtra("Image");
        String title = getIntent().getStringExtra("Title");
        placeID = getIntent().getStringExtra("place_id");

        tripTitle.setText(title);

        if (title.equals("Batong Palaka")) {
            imageView.setImageResource(R.drawable.batongpalaka);
        }
        if (title.equals("Puning Cave")) {

            imageView.setImageResource(R.drawable.puningcave1);
        }
        if (title.equals("Malangaan Spring")) {
            imageView.setImageResource(R.drawable.malangaanspring);
        }
        if (title.equals("Mt. Puting Bato")) {
            imageView.setImageResource(R.drawable.putingbato1);
        }
        if (title.equals("Simbahang Bato")) {
            imageView.setImageResource(R.drawable.simbahangbato);
        }
        if (title.equals("Tung-tung Falls")) {
            imageView.setImageResource(R.drawable.tungtungfalls);
        }
        if (title.equals("Adarna Falls")) {
            imageView.setImageResource(R.drawable.adarnafalls);
        }
        if (title.equals("Candle Monument")) {
            imageView.setImageResource(R.drawable.candlemonument);
        }
        if (title.equals("Secret Falls")) {
            imageView.setImageResource(R.drawable.secretfalls);
        }
        if (title.equals("Mt.Balistada")) {
            imageView.setImageResource(R.drawable.balistada);
        }
        if (title.equals("Mt.Manalmon")) {
            imageView.setImageResource(R.drawable.mtmanalmon);
        }
        if (title.equals("Patingan Cave")) {
            imageView.setImageResource(R.drawable.pantingancave);
        }
        if (title.equals("Talon Lukab")) {
            imageView.setImageResource(R.drawable.talonlukab);
        }
        if (title.equals("Talon ni Eba")) {
            imageView.setImageResource(R.drawable.talonnieba);
        }
        if (title.equals("Talon ni Pedro")) {
            imageView.setImageResource(R.drawable.talonnipedro);
        }
        if (title.equals("Talon ni Zamora")) {
            imageView.setImageResource(R.drawable.talonnizamora);
        }
        if (title.equals("Talon ni Pari")) {
            imageView.setImageResource(R.drawable.talonpari);
        }
        if (title.equals("Tangke River")) {
            imageView.setImageResource(R.drawable.tangke);
        }
        if (title.equals("Tila Pilon Hills")) {
            imageView.setImageResource(R.drawable.tilapilonhills);
        }
        if (title.equals("Mt.Mavio")) {
            imageView.setImageResource(R.drawable.mtmavio);
        }
        if (title.equals("Palanguyan")) {
            imageView.setImageResource(R.drawable.palanguyan);
        }
        if (title.equals("Verdibia Falls")) {
            imageView.setImageResource(R.drawable.verdibia);
        }
        if (title.equals("Tila Pilon Hills Cafe")) {
            imageView.setImageResource(R.drawable.cafe1);
        }

        //save button for creating trip

        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String title = tripTitle.getText().toString();
                String name = TripName.getText().toString();
                String description = desc.getText().toString();

                int day = datePickerDialog.getDatePicker().getDayOfMonth();
                int month = datePickerDialog.getDatePicker().getMonth();
                int year = datePickerDialog.getDatePicker().getYear();


                String start = String.valueOf(day + "/" + month + "/" + year);

                int day2 = datePickerDialog2.getDatePicker().getDayOfMonth();
                int month2 = datePickerDialog2.getDatePicker().getMonth();
                int year2 = datePickerDialog2.getDatePicker().getYear();

                String End = String.valueOf(day2 + "/" + month2 + "/" + year2);



                if (TextUtils.isEmpty(name)){
                    TripName.setError("Please Input a Trip Title!");
                } else {



                    insertData(placeID);
                    //clearAll();

                }

            }
        });
    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker2() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                endDate.setText(date);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;


        datePickerDialog2 = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog2.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                startDate.setText(date);

            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month) {
        if (month == 1)
            return "JAN";
        if (month == 2)
            return "FEB";
        if (month == 3)
            return "MAR";
        if (month == 4)
            return "APR";
        if (month == 5)
            return "MAY";
        if (month == 6)
            return "JUN";
        if (month == 7)
            return "JUL";
        if (month == 8)
            return "AUG";
        if (month == 9)
            return "SEP";
        if (month == 10)
            return "OCT";
        if (month == 11)
            return "NOV";
        if (month == 12)
            return "DEC";

        return "JAN";
    }

    public void StartDate(View view) {
        datePickerDialog.show();
    }

    public void EndDate(View view) {
        datePickerDialog2.show();
    }

    public void cancel(View view) {

        finish();
    }



    private void insertData(String place_id)
    {
        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Map<String,Object> map = new HashMap<>();
        map.put("place_id",place_id);
        map.put("trip_spot",tripTitle.getText().toString());
        map.put("trip_name",TripName.getText().toString());
        map.put("trip_start",startDate.getText().toString());
        map.put("trip_end",endDate.getText().toString());
        map.put("trip_desc",desc.getText().toString());

        FirebaseDatabase.getInstance().getReference(currentuser).child("trips").push()
                .setValue((map))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(CreateTrip.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CreateTrip.this,recyclerviewfinal.class);
                        //intent.putExtra("PlaceID",placeID);
                        Toast.makeText(CreateTrip.this, currentuser, Toast.LENGTH_SHORT).show();

                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CreateTrip.this, "Error while insertion.", Toast.LENGTH_SHORT).show();
                    }
                });


    }

/*
    private void clearAll(){
        tripTitle.setText("");
        TripName.setText("");
        startDate.setText("");
        endDate.setText("");
        desc.setText("");

    }*/

}





