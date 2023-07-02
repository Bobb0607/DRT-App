package com.example.drttouristplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EditTrip extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private DatePickerDialog datePickerDialog2;
    private Button startDate;
    private Button endDate;
    private String tripID;
    private TripRVModal tripRVModal;


    //firebase
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    EditText TripName;
    EditText desc;
    TextView tripTitle;
    ImageView imageView;
    ArrayList<ContentModel> defaultContent = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);
        Intent intent = new Intent(EditTrip.this, EditTrip.class);

        Button update = findViewById(R.id.update_tripbtn);
        Button deleteBtn = findViewById(R.id.delete_tripbtn);
        Button cancel = findViewById(R.id.cancel_tripCreation_button);
        TextView tripTitle = findViewById(R.id.tripTitle);
        EditText TripName = findViewById(R.id.TripName);
        EditText desc = findViewById(R.id.EDdesc);
        startDate = findViewById(R.id.EDstartDate);
        endDate = findViewById(R.id.EDendDate);
        ImageView imageView = findViewById(R.id.tripIMG);
        ProgressBar progressBar = findViewById(R.id.idPBloading);

        initDatePicker();
        initDatePicker2();

        startDate.setText(getTodaysDate());
        endDate.setText(getTodaysDate());

        //initialize firebase database
        firebaseDatabase = FirebaseDatabase.getInstance();


        //array list ng Integer (image)

        ArrayList<Integer> Images = getIntent().getIntegerArrayListExtra("Image");
        String title = getIntent().getStringExtra("Title");

        tripTitle.setText(title);

        if(title.equals("Batong Palaka"))
        {
            imageView.setImageResource(R.drawable.batongpalaka);
        } if(title.equals("Puning Cave")) {

            imageView.setImageResource(R.drawable.puningcave1);
        }if(title.equals("Malangaan Spring"))
        {
            imageView.setImageResource(R.drawable.malangaanspring);
        }if(title.equals("Mt. Puting Bato")) {
            imageView.setImageResource(R.drawable.putingbato1);
        }
        if(title.equals("Simbahang Bato")) {
            imageView.setImageResource(R.drawable.simbahangbato);
        }
        if(title.equals("Tung-tung Falls")) {
            imageView.setImageResource(R.drawable.tungtungfalls);
        } if(title.equals("Adarna Falls")) {
            imageView.setImageResource(R.drawable.adarnafalls);
        }if(title.equals("Candle Monument")) {
            imageView.setImageResource(R.drawable.candlemonument);
        }if(title.equals("Secret Falls")) {
            imageView.setImageResource(R.drawable.secretfalls);
        }if(title.equals("Mt. Balistada")) {
            imageView.setImageResource(R.drawable.balistada);
        }if(title.equals("Mt. Manalmon")) {
            imageView.setImageResource(R.drawable.mtmanalmon);
        }if(title.equals("Patingan Cave")) {
            imageView.setImageResource(R.drawable.pantingancave);
        }if(title.equals("Talon Lukab")) {
            imageView.setImageResource(R.drawable.talonlukab);
        }if(title.equals("Talon ni Eba")) {
            imageView.setImageResource(R.drawable.talonnieba);
        }if(title.equals("Talon ni Pedro")) {
            imageView.setImageResource(R.drawable.talonnipedro);
        }
        if(title.equals("Talon ni Zamora")) {
            imageView.setImageResource(R.drawable.talonnizamora);
        } if(title.equals("Talon ni Pari")) {
            imageView.setImageResource(R.drawable.talonpari);
        }if(title.equals("Tangke")) {
            imageView.setImageResource(R.drawable.tangke);
        }if(title.equals("Tila Pilon Hills")) {
            imageView.setImageResource(R.drawable.tilapilonhills);
        }if(title.equals("Mt. Mavio")) {
            imageView.setImageResource(R.drawable.mtmavio);
        }if(title.equals("Palanguyan")) {
            imageView.setImageResource(R.drawable.palanguyan);
        }if(title.equals("Verdibia Falls")) {
            imageView.setImageResource(R.drawable.verdibia);
        }

        tripRVModal = getIntent().getParcelableExtra("trip_title");
        if(tripRVModal!=null){
            tripTitle.setText(tripRVModal.getTripTitle());
            TripName.setText(tripRVModal.getTripName());
            startDate.setText(tripRVModal.getTripStartDate());
            endDate.setText(tripRVModal.getTripEndDate());
            desc.setText(tripRVModal.getTripDescription());
            tripID = tripRVModal.getTripID();
        }

        //initialize firebase
        databaseReference = firebaseDatabase.getReference("trip_title").child(tripID);



        //update trip

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                progressBar.setVisibility(View.VISIBLE);
                String title = tripTitle.getText().toString();
                String name = TripName.getText().toString();
                String description = desc.getText().toString();

                tripID = title;

                if (TextUtils.isEmpty(name)){
                    TripName.setError("Your name is required!");
                    return;
                }

                else {


                    int day = datePickerDialog.getDatePicker().getDayOfMonth();
                    int month = datePickerDialog.getDatePicker().getMonth();
                    int year = datePickerDialog.getDatePicker().getYear();


                    String start = String.valueOf(day + "/" + month + "/" + year);

                    int day2 = datePickerDialog2.getDatePicker().getDayOfMonth();
                    int month2 = datePickerDialog2.getDatePicker().getMonth();
                    int year2 = datePickerDialog2.getDatePicker().getYear();


                    String End = String.valueOf(day2 + "/" + month2 + "/" + year2);

                    Toast.makeText(EditTrip.this, "" + start, Toast.LENGTH_SHORT).show();

                    intent.putExtra("name", String.valueOf(name));
                    intent.putExtra("desc", String.valueOf(description));
                    intent.putExtra("start", String.valueOf(start));
                    intent.putExtra("end", String.valueOf(End));

                    Map<String, Object> map = new HashMap<>();
                    map.put("tripTitle", title);
                    map.put("tripName", name);
                    map.put("tripStartDate", start);
                    map.put("tripEndDate", End);
                    map.put("tripDescription", description);
                    map.put("tripID", tripID);

                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            progressBar.setVisibility(View.GONE);
                            databaseReference.updateChildren(map);
                            Toast.makeText(EditTrip.this, "Trip Updated...", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(EditTrip.this, view_trip.class));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                            Toast.makeText(EditTrip.this, "Failed to update trip...", Toast.LENGTH_SHORT).show();

                        }
                    });
                }

            }
        });

        //delete trip

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteTrip();

            }
        });


        }
    private void deleteTrip(){
        databaseReference.removeValue();
        Toast.makeText(this, "Trip deleted...", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EditTrip.this,view_trip.class));

       /*save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditTrip.this, trip_view.class);


                String title = tripTitle.getText().toString();
                String name = TripName.getText().toString();
                String description = desc.getText().toString();

                tripID = title;


                int day = datePickerDialog.getDatePicker().getDayOfMonth();
                int month = datePickerDialog.getDatePicker().getMonth();
                int year = datePickerDialog.getDatePicker().getYear();




                String start = String.valueOf(day +"/"+ month + "/" + year);

                int day2 = datePickerDialog2.getDatePicker().getDayOfMonth();
                int month2 = datePickerDialog2.getDatePicker().getMonth();
                int year2 = datePickerDialog2.getDatePicker().getYear();



                String End = String.valueOf(day2+ "/" + month2 + "/" + year2);

                Toast.makeText(EditTrip.this, "" + start, Toast.LENGTH_SHORT).show();

                intent.putExtra("name", String.valueOf(name));
                intent.putExtra("desc", String.valueOf(description));
                intent.putExtra("start", String.valueOf(start));
                intent.putExtra("end", String.valueOf(End));


                TripRVModal tripRVModal = new TripRVModal(title,name,description,start,End,tripID);

                //save to firebase

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.child(tripID).setValue(tripRVModal);
                        Toast.makeText(EditTrip.this, "Trip Added...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditTrip.this,view_trip.class));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EditTrip.this, "Error is" +error.toString(), Toast.LENGTH_SHORT).show();

                    }
                });


                if (name.equals("") && description.equals("")) {
                    Toast.makeText(EditTrip.this, "Trip Name and Description Cannot be Null", Toast.LENGTH_SHORT).show();
                }else {
                    startActivity(intent);
                }

            }
        });*/
    }

    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker2()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
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

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
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

    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
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
}