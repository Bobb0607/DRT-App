package com.example.drttouristplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.orhanobut.dialogplus.DialogPlus;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TimeZone;


public class ItineraryList extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ListView list_itinerary;
    public TextView textview_back, textview_trip_name, textview_place_name, textview_start_end_trip_date;
    private Button btn_add_itinerary;
    private ArrayList<ItineraryClass> ic_list;
    private FirebaseFirestore fStore;
    private FirebaseFirestore fireStore;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;

    FloatingActionButton fab_add_itinerary;

    private DatePickerDialog datePickerDialog_end_date;

    private LinearLayout ll_no_data;

    String TouristID = "";
    String placeID = "";
    String tripID = "";
    String tripName = "";

    String trip_start = "";
    String trip_end = "";

    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    String latitude, longitude;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary_list);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton12);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(ItineraryList.this);
                builder.setMessage("1.Tap on the plus button to create itinerary." +
                                "\n 2. Tap the box below Itinerary Name to input desired name for the Itinerary" +
                                " \n 3. Tap Select Category and pick and tap the desired Activity  " +
                                "\n 4. Tap on Change to change start time and end time or the start date, Click the Numbers shown and tap the AM or PM to choose what time and tap Ok if you are done. " +
                                "\n 5. end date, swipe up or down to change the shown dates and tap Ok if you are done. " +
                                "\n 6. Tap Save Schedule if you are done")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                // Create the AlertDialog object and return it
                androidx.appcompat.app.AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


        fStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        TouristID = mAuth.getCurrentUser().getUid();


        Intent ii = getIntent();
        placeID = ii.getStringExtra("place_id");
        tripID = ii.getStringExtra("trip_id");
        tripName = ii.getStringExtra("trip_name");
        trip_start = ii.getStringExtra("date_start");
        trip_end = ii.getStringExtra("date_end");

        fab_add_itinerary = findViewById(R.id.add_itinerary_fab);
        textview_back = (TextView) findViewById(R.id.textView_back_to_trips);
        textview_trip_name = (TextView) findViewById(R.id.textView_schedule_trip_name);
        textview_place_name = (TextView) findViewById(R.id.textView_schedule_place_name);
        textview_start_end_trip_date = (TextView) findViewById(R.id.textView_trip_start_end_date);
        btn_add_itinerary = (Button) findViewById(R.id.btnAddItinerary);
        ll_no_data = (LinearLayout) findViewById(R.id.linearlayout_no_data_itinerary);

        ic_list = new ArrayList<>();
        list_itinerary = (ListView) findViewById(R.id.listview_itinerary);

        textview_trip_name.setText("'"+tripName+"'");
        textview_start_end_trip_date.setText("From "+trip_start+" To "+trip_end+"");

        displayPlaceDetails(placeID, textview_place_name);

        fab_add_itinerary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addItinerary(v, textview_place_name.getText().toString(),tripName);

            }
        });

        btn_add_itinerary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               addItinerary(v, textview_place_name.getText().toString(),tripName);

            }
        });

        textview_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent trip = new Intent(ItineraryList.this,recyclerviewfinal.class);
                startActivity(trip);
            }
        });

        getItineraryData(TouristID,placeID,tripID);


    }



    private void addItinerary(View v,String place_name, String trip_name){

        final DialogPlus dialogPlus = DialogPlus.newDialog(v.getContext())
                .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.add_itinerary_dialog))
                .setExpanded(false,0)
                .create();

        View view1 = dialogPlus.getHolderView();

        final String[] selected_itinerary_category = new String[1];

        final Button btnSaveItinerary = (Button) view1.findViewById(R.id.btnSubmitAddItinerary);

        final TextView txt_place_name = (TextView) view1.findViewById(R.id.textView_place_name_for_itinerary);
        final TextView txt_trip_name = (TextView) view1.findViewById(R.id.textView_trip_name_itinerary);

        //days spinner
        final Spinner txt_itinerary_name = (Spinner) view1.findViewById(R.id.txt_itinerary_name);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.days, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item );
        txt_itinerary_name.setAdapter(adapter);
        txt_itinerary_name.setOnItemSelectedListener(this);

        //spots spinner
        final Spinner txt_itinerary_spot_name = (Spinner) view1.findViewById(R.id.txt_spot_itinerary_name);
        ArrayAdapter<CharSequence> spotadapter = ArrayAdapter.createFromResource(this, R.array.itineraryspots, android.R.layout.simple_spinner_item);
        spotadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item );
        txt_itinerary_spot_name.setAdapter(spotadapter);
        txt_itinerary_spot_name.setOnItemSelectedListener(this);


        TextView text_view_start_date = (TextView) view1.findViewById(R.id.textView_edit_start_date);
        TextView text_view_start_date_value = (TextView) view1.findViewById(R.id.textView_start_date_value);
        TextView text_view_end_date = (TextView) view1.findViewById(R.id.textView_edit_end_date);
        TextView text_view_end_date_value = (TextView) view1.findViewById(R.id.textView_end_date_value);

        TextView text_view_start_time = (TextView) view1.findViewById(R.id.textView_edit_start_time);
        TextView text_view_start_time_value = (TextView) view1.findViewById(R.id.textView_start_time_value);
        TextView text_view_end_time = (TextView) view1.findViewById(R.id.textView_edit_end_time);
        TextView text_view_end_time_value = (TextView) view1.findViewById(R.id.textView_end_time_value);

        final Spinner spinner_itinerary_category = (Spinner) view1.findViewById(R.id.spinner_itinerary_category);

        txt_place_name.setText(place_name);

        if(trip_name.equals("")){
            txt_trip_name.setVisibility(View.GONE);
        }else{
            txt_trip_name.setVisibility(View.VISIBLE);
            txt_trip_name.setText("Trip Name: "+trip_name);
        }

        String[] itinerary_categories;

        itinerary_categories = new String[]{" -- Select Category -- ","Activity","Note","Restaurant","Transportation","Lodging","Meeting"};

        ArrayAdapter arrayAdapter_spinner_itinerary_categories = new ArrayAdapter(this,android.R.layout.simple_spinner_item,itinerary_categories);
        arrayAdapter_spinner_itinerary_categories.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_itinerary_category.setAdapter(arrayAdapter_spinner_itinerary_categories);

        spinner_itinerary_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_itinerary_category[0] = itinerary_categories [position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        text_view_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDateDialog(view1,text_view_start_date_value);
            }
        });

        text_view_end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDateDialog(view1,text_view_end_date_value);
            }
        });

        text_view_start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimeDialog(view1, text_view_start_time_value);
            }
        });

        text_view_end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimeDialog(view1, text_view_end_time_value);
            }
        });


        btnSaveItinerary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String itinerary_category = "";

                String place_id = placeID;
                String trip_id = tripID;
                String itinerary_name = txt_itinerary_name.getSelectedItem().toString();
                String itinerary_spot_name = txt_itinerary_spot_name.getSelectedItem().toString();

                if(selected_itinerary_category[0].equals(" -- Select Category -- ")){
                    itinerary_category = "";
                }else{
                    itinerary_category = selected_itinerary_category[0];
                }

                int error_count = 0;
                String error_msg = "";

                String plan_start_date = text_view_start_date_value.getText().toString();
                String plan_end_date = text_view_end_date_value.getText().toString();
                String plan_start_time = text_view_start_time_value.getText().toString();
                String plan_end_time = text_view_end_time_value.getText().toString();

                if(itinerary_name.isEmpty()){
                    error_count+=1;
                    error_msg += "Itinerary Name is Blank\n";
                }

                if(itinerary_spot_name.isEmpty()){
                    error_count+=1;
                    error_msg += "Spot is Blank\n";
                }


                if(itinerary_category.isEmpty()){
                    error_count+=1;
                    error_msg += "No Category Selected\n";
                }

                if(plan_start_date.isEmpty()){
                    error_count+=1;
                    error_msg += "Start Date is Blank\n";
                }

                if(plan_start_date.equals("--- --,----")){
                    error_count+=1;
                    error_msg += "Please set the Start Date\n";
                }

                if(plan_end_date.isEmpty()){
                    error_count+=1;
                    error_msg += "End Date is Blank\n";
                }

                if(plan_end_date.equals("--- --,----")){
                    error_count+=1;
                    error_msg += "Please Set the End Date\n";
                }

                if(plan_start_time.isEmpty()){
                    error_count+=1;
                    error_msg += "Start Time is Blank\n";
                }

                if(plan_start_time.equals("--,--")){
                    error_count+=1;
                    error_msg += "Please Set the Start Time\n";
                }

                if(plan_end_time.equals("--,--")){
                    error_count+=1;
                    error_msg += "Please Set the End Time\n";
                }


                    if(error_count < 1){

                        String formatted_start_date = formatDateTwo(trip_start);
                        String formatted_end_date = formatDateTwo(trip_end);
                        String formatted_plan_start_date = formatDate(plan_start_date);
                        String formatted_plan_end_date = formatDate(plan_end_date);

                        //Toast.makeText(getApplicationContext(),""+trip_start+" ("+formatted_start_date+") - "+trip_end+"("+formatted_end_date+")",Toast.LENGTH_LONG).show();
                        //Toast.makeText(getApplicationContext(),""+formatted_plan_start_date+"",Toast.LENGTH_LONG).show();
                        //Toast.makeText(getApplicationContext(),""+plan_start_date+"",Toast.LENGTH_LONG).show();


                        boolean bool_check_date_range_date_start_input = isDateInRange(formatted_start_date, formatted_end_date, getApplicationContext(), formatted_plan_start_date);
                        boolean bool_check_date_range_date_end_input = isDateInRange(formatted_start_date, formatted_end_date, getApplicationContext(), formatted_plan_end_date);

                        bool_check_date_range_date_start_input = true;
                        bool_check_date_range_date_end_input = true;

                        boolean is_date_from_past = isDatePast(formatted_plan_start_date);
                        boolean is_date_to_past = isDatePast(formatted_plan_end_date);

                        int date_input_error_count = 0;
                        String date_input_error_message = "";

                        if(bool_check_date_range_date_start_input == false){
                            date_input_error_count += 1;
                            date_input_error_message += "Start Date Input is outside the Trip Date Range\n";
                        }

                        if(bool_check_date_range_date_end_input == false){
                            date_input_error_count += 1;
                            date_input_error_message += "End Date Input is outside the Trip Date Range\n";
                        }

                        if(is_date_from_past == true){
                            date_input_error_count += 1;
                            date_input_error_message += "You cannot select past dates on Start Date";
                        }

                        if(is_date_to_past == true){
                            date_input_error_count += 1;
                            date_input_error_message += "You cannot select past dates on End Date";
                        }

                        if(date_input_error_count < 1){

                            fStore = FirebaseFirestore.getInstance();
                            String ItineraryID = fStore.collection("itinerary").document().getId();
                            DocumentReference documentReference = fStore.collection("itinerary").document(ItineraryID);
                            Map<String,Object> user_review = new HashMap<>();
                            user_review.put("itinerary_id",ItineraryID);
                            user_review.put("place_id",place_id);
                            user_review.put("trip_id",trip_id);
                            user_review.put("user_id",TouristID);
                            user_review.put("itinerary_name",""+itinerary_name+"");
                            user_review.put("itinerary_spot_name",""+itinerary_spot_name);
                            user_review.put("itinerary_category",itinerary_category);
                            user_review.put("plan_start_date",plan_start_date);
                            user_review.put("plan_start_time",plan_start_time);
                            user_review.put("plan_end_date",plan_end_date);
                            user_review.put("plan_end_time",plan_end_time);

                            documentReference.set(user_review).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    getItineraryData(TouristID,place_id,trip_id);
                                    ll_no_data.setVisibility(View.GONE);
                                    fab_add_itinerary.setVisibility(View.VISIBLE);
                                    Toast.makeText(getApplicationContext(),"One Schedule for Itinerary Added\n"+itinerary_name+"",Toast.LENGTH_LONG).show();
                                    dialogPlus.dismiss();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    Toast.makeText(view1.getContext(),"Error rating submission, Try again later",Toast.LENGTH_LONG).show();

                                }
                            });

                            dialogPlus.dismiss();

                        }else{

                            Toast.makeText(getApplicationContext(),""+date_input_error_message+"",Toast.LENGTH_LONG).show();
                            date_input_error_count = 0;
                            date_input_error_message = "";

                        }


                }else{

                        Toast.makeText(getApplicationContext(),""+error_msg+"",Toast.LENGTH_LONG).show();
                        error_count = 0;
                        error_msg = "";

                }

            }
        });

        dialogPlus.show();
    }

    public Context getContextItinerary(){
        return ItineraryList.this;
    }

    private String formatDate(String inputDate){
        String return_value = "";


        String array_string[] = new String[3];
        String array_string_second[] = new String[3];

        int i = 0;

        StringTokenizer st = new StringTokenizer(""+inputDate+""," ");
        while (st.hasMoreTokens()) {
            array_string[i] = st.nextToken().toString();
            i++;
        }

        int j = 0;
        StringTokenizer st_second = new StringTokenizer(""+array_string[1]+"",",");
        while (st_second.hasMoreTokens()) {
            array_string_second[j] = st_second.nextToken().toString();
            j++;
        }

        String month = toMonthNumberString(array_string[0]);
        String day = array_string_second[0].trim();
        String year = array_string[2].trim();

        return_value = month+"."+day+"."+year;

        return return_value;
    }


    private String formatDateTwo(String inputDate){
        String return_value = "";

        String array_string[] = new String[3];

        int i = 0;

        StringTokenizer st = new StringTokenizer(""+inputDate+""," ");
        while (st.hasMoreTokens()) {
            array_string[i] = st.nextToken().toString();
            i++;
        }

        String month = toMonthNumberString(array_string[0]);
        String day = array_string[1];
        String year = array_string[2];

        return_value = month+"."+day+"."+year;

        return return_value;
    }

    private String toMonthNumberString(String full_month_string){
        String return_value = "";

        if(full_month_string.toLowerCase().equals("january") || full_month_string.toLowerCase().equals("jan")){
            return_value = "1";
        }else if(full_month_string.toLowerCase().equals("february") || full_month_string.toLowerCase().equals("feb")){
            return_value = "2";
        }
        else if(full_month_string.toLowerCase().equals("march") || full_month_string.toLowerCase().equals("mar")){
            return_value = "3";
        }
        else if(full_month_string.toLowerCase().equals("april") || full_month_string.toLowerCase().equals("apr")){
            return_value = "4";
        }
        else if(full_month_string.toLowerCase().equals("may") || full_month_string.toLowerCase().equals("may")){
            return_value = "5";
        }
        else if(full_month_string.toLowerCase().equals("june") || full_month_string.toLowerCase().equals("jun")){
            return_value = "6";
        }
        else if(full_month_string.toLowerCase().equals("july") || full_month_string.toLowerCase().equals("jul")){
            return_value = "7";
        }
        else if(full_month_string.toLowerCase().equals("august") || full_month_string.toLowerCase().equals("aug")){
            return_value = "8";
        }
        else if(full_month_string.toLowerCase().equals("september") || full_month_string.toLowerCase().equals("sep")){
            return_value = "9";
        }
        else if(full_month_string.toLowerCase().equals("october") || full_month_string.toLowerCase().equals("oct")){
            return_value = "10";
        }
        else if(full_month_string.toLowerCase().equals("november") || full_month_string.toLowerCase().equals("nov")){
            return_value = "11";
        }
        else if(full_month_string.toLowerCase().equals("december") || full_month_string.toLowerCase().equals("dec")){
            return_value = "12";
        }

        return return_value;
    }

    private boolean isDatePast(String input_date){
        Boolean bool = false;

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM.dd.yyyy");
        Calendar calendarDate = Calendar.getInstance();


        try{
            Date current_date = calendarDate.getTime(); // current date/time
            Date selected_date = dateFormat.parse(input_date);
            if(selected_date.after(current_date)){

            }else{
                bool = true;
            }

            if(selected_date.equals(current_date)){
                bool = true;

            }

            //Toast.makeText(this,"Selected Date: "+curr+"")

        }catch(Exception e){
            Toast.makeText(this,"Exception: "+e.getMessage()+"",Toast.LENGTH_LONG).show();
        }


        return bool;
    }

    private boolean isDateInRange(String s1, String s2, Context ctx, String input_date){

        Boolean bool = false;
        int found = 0;

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM.dd.yyyy");
        Calendar calendarFrom = Calendar.getInstance();
        Calendar calendarUntil = Calendar.getInstance();

        try{

            calendarFrom.setTime(dateFormat.parse(s1));
            calendarUntil.setTime(dateFormat.parse(s2));

            found = 0;

            while (calendarFrom.compareTo(calendarUntil) != 0) {
                System.out.println(dateFormat.format(calendarFrom.getTime()));
                //Toast.makeText(ctx, dateFormat.format(calendarFrom.getTime()),Toast.LENGTH_LONG).show();
                calendarFrom.add(Calendar.DATE, 1);

                String fromRange = ""+dateFormat.format(calendarFrom.getTime())+"";
                String fromInput = input_date;

                if(fromRange.trim().equals(fromInput.trim())){
                    found += 1;
                }

            }

        }catch(Exception e){
            Toast.makeText(ctx,""+e.getMessage().toString()+"",Toast.LENGTH_LONG).show();
        }

        if(found > 0){
            bool = true;
        }

        return bool;

    }

    private void initDateDialog(View vv, TextView txt_date){

        final DatePickerDialog datePickerDialog_date;

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                int mon = 0;
                mon = month + 1;
                UtilSet uu = new UtilSet();
                String date_format_string = uu.makeDateString(day,mon,year);
                txt_date.setText(date_format_string);

            }

        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int styleInt = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog_date = new DatePickerDialog(vv.getContext(), styleInt, dateSetListener, year,month,day);

        datePickerDialog_date.show();

    }

    /**
    private void editItinerary(){

        Map<String,Object> map = new HashMap<>();
        map.put("trip_spot", "");
        map.put("trip_spot", "");
        map.put("trip_spot", "");
        map.put("trip_spot", "");

        String TableID = "";

    }
     **/

    public void openTimeDialog(View view, TextView txt_time)
    {

        int hour, minute;

        final Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(), new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int min) {

                String real_hour = "", real_minute = "";
                String time_set = "";

                real_hour = ""+hour+"";
                real_minute = ""+min+"";

                DateFormat simpleDateFormat;
                simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

                time_set = real_hour +":"+real_minute+":00";

                try{
                    Date d = simpleDateFormat.parse(time_set);
                    //txt_time.setText(""+real_hour+":"+real_minute+"");
                    String time_formatted = simpleDateFormat.format(d.getTime());
                    txt_time.setText(""+time_formatted+"");
                }catch(Exception e){
                    Toast.makeText(getApplicationContext(),"Time Format Error: "+e.getMessage().toString()+"",Toast.LENGTH_LONG).show();
                }




            }
        },hour,minute,false);
        timePickerDialog.show();
    }

    public void getItineraryData(String userId, String placeId, String tripId){

        ic_list.clear();

        fStore.collection("itinerary")
                .orderBy("itinerary_name")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                String itinerary_id     = document.getId();
                                String place_id         = (String) document.get("place_id");
                                String trip_id          = (String) document.get("trip_id");
                                String user_id          = (String) document.get("user_id");
                                String itinerary_name   = (String) document.get("itinerary_name");
                                String itinerary_spot_name = (String) document.get("itinerary_spot_name");
                                String category_name    = (String) document.get("itinerary_category");
                                String plan_start_date  = (String) document.get("plan_start_date");
                                String plan_end_date    = (String) document.get("plan_end_date");
                                String plan_time_start  = (String) document.get("plan_start_time");
                                String plan_time_end    = (String) document.get("plan_end_time");
                                String TripName         = tripName;

                                if(placeId.isEmpty()){

                                }else{
                                    if(place_id.equals(placeId) && user_id.equals(userId) && trip_id.equals(tripId)){
                                        ItineraryClass ic_list_record = new ItineraryClass(""+itinerary_id+"",""+itinerary_name+" - "+itinerary_spot_name+"",""+category_name+"",""+place_id+"",""+plan_start_date+"",""+plan_end_date+"",""+plan_time_start+"",""+plan_time_end+"",""+trip_id+"",TripName,""+user_id+"");
                                        ic_list.add(ic_list_record);
                                    }
                                }


                            }

                            ItineraryDetailsListAdapter idlistAdapter = new ItineraryDetailsListAdapter(ItineraryList.this, ic_list);
                            list_itinerary.setAdapter(idlistAdapter);


                            if(ic_list.size() < 1){
                                ll_no_data.setVisibility(View.VISIBLE);
                                fab_add_itinerary.setVisibility(View.GONE);
                            }

                        }
                    }
                });
    }

    private void displayPlaceDetails(String placeID, TextView text_place_name){

        fireStore = FirebaseFirestore.getInstance();

        fireStore.collection("places")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {


                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                String place_ID = document.getId();
                                String place_name = (String) document.get("place_name");

                                if(place_ID.equals(placeID)){

                                    text_place_name.setText(place_name);
                                }


                            }
                        }
                    }
                });

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.navbottomprofile);

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
                    case R.id.navbottomcreateTrip:
                        startActivity(new Intent(getApplicationContext(),Barangay.class));
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}