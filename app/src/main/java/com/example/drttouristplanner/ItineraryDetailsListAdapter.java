package com.example.drttouristplanner;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.orhanobut.dialogplus.DialogPlus;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class ItineraryDetailsListAdapter extends ArrayAdapter<ItineraryClass> {

    Context ctx;
    private String itinerary_ID_value = "";
    private FirebaseFirestore fStore;
    private FirebaseAuth mAuth;
    private String TouristID = "";
    private ArrayList<ItineraryClass> iclist = new ArrayList<>();


    public ItineraryDetailsListAdapter(Context context, ArrayList<ItineraryClass> ItineraryListArray){
        super(context, R.layout.itinerary_list_detail_item, ItineraryListArray);
        this.iclist = ItineraryListArray;
        this.ctx = context;


       // this.TouristID = mAuth.getCurrentUser().getUid();
       // this.mAuth = FirebaseAuth.getInstance();


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){


        ItineraryClass ic_list = iclist.get(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.itinerary_list_detail_item,parent,false);
        }

        TextView text_itinerary_name = (TextView) convertView.findViewById(R.id.textView_itinerary_name);
        TextView text_spot_name_itinerary = (TextView) convertView.findViewById(R.id.textView_spot_name_itinerary);
        TextView text_plan_name = (TextView) convertView.findViewById(R.id.textView_plan_name_label);
        TextView text_place_name = (TextView) convertView.findViewById(R.id.textView_place_name_label);
        TextView text_start_date_time = (TextView) convertView.findViewById(R.id.textView_start_date_time);
        TextView text_end_date_time = (TextView) convertView.findViewById(R.id.textView_end_date_time);
        TextView text_category_label = (TextView) convertView.findViewById(R.id.textView_category_label);
        TextView text_itinerary_status = (TextView) convertView.findViewById(R.id.textView_itinerary_status);
        Button btn_edit_itinerary = (Button) convertView.findViewById(R.id.btnEdit_itinerary);
        Button btn_delete_itinerary = (Button) convertView.findViewById(R.id.btnDelete_itinerary);
        ImageView image_itinerary_logo_category = (ImageView) convertView.findViewById(R.id.imageView_itinerary_category_logo);

        String TouristIDValue = ic_list.getUser_id();
        String Itinerary_ID = ic_list.getItinerary_id();
        String itinerary_name = ic_list.getItinerary_name();
        String itinerary_spot = ic_list.getItinerary_spot();
        String plan_id = ic_list.getTrip_id();
        String trip_id = ic_list.getTrip_id();
        String place_id =  ic_list.getPlace_id();
        //String place_name = ic_list.getPlace_id();
        String start_date = ic_list.getPlan_start_date();
        String end_date = ic_list.getPlan_end_date();
        String start_time = ic_list.getPlan_start_time();
        String end_time = ic_list.getPlan_end_time();
        String category_label = ic_list.getCategory_name();
        String trip_name_label = ic_list.getTrip_name();

        text_itinerary_name.setText(itinerary_name);

        text_spot_name_itinerary.setText(itinerary_spot);



        //text_place_name.setText(place_name); // Display on the Dialog Box Header
        //text_plan_name.setText(plan_name); // Display on the Dialog Box Header below Place Name

        String display_start_time = formatTimeDisplayTo12Hrs(start_time, ":");
        String display_end_time = formatTimeDisplayTo12Hrs(end_time, ":");

        text_start_date_time.setText("From:  "+display_start_time+"");
        text_end_date_time.setText("To:  "+display_end_time+"");
        text_category_label.setText(category_label);

        displayPlaceDetails(place_id, text_place_name);

        boolean is_date_lapse = isDatePast(""+end_date+"");


        if(is_date_lapse == true){
            text_itinerary_status.setVisibility(View.VISIBLE);
            text_itinerary_status.setText("DONE");

            btn_edit_itinerary.setVisibility(View.GONE);
            btn_delete_itinerary.setVisibility(View.GONE);
        }else{
            text_itinerary_status.setVisibility(View.GONE);
            text_itinerary_status.setText("DONE");

            btn_edit_itinerary.setVisibility(View.VISIBLE);
            btn_delete_itinerary.setVisibility(View.VISIBLE);
        }

        if(category_label.toLowerCase().trim().equals("restaurant")){
            image_itinerary_logo_category.setBackgroundResource(R.drawable.categoryrestaurant1);
        }else if(category_label.toLowerCase().trim().equals("cruise")){
            image_itinerary_logo_category.setBackgroundResource(R.drawable.categorycruise1);
        }else if(category_label.toLowerCase().trim().equals("car rental")){
            image_itinerary_logo_category.setBackgroundResource(R.drawable.categorycarrenta1);
        }else if(category_label.toLowerCase().trim().equals("lodging")){
            image_itinerary_logo_category.setBackgroundResource(R.drawable.categorylodging1);
        }
        else if(category_label.toLowerCase().trim().equals("flight")){
            image_itinerary_logo_category.setBackgroundResource(R.drawable.categoryflight1);
        }
        else if(category_label.toLowerCase().trim().equals("meeting")){
            image_itinerary_logo_category.setBackgroundResource(R.drawable.categorymeeting1);
        }
        else if(category_label.toLowerCase().trim().equals("parking")){
            image_itinerary_logo_category.setBackgroundResource(R.drawable.categoryparking1);
        }
        else if(category_label.toLowerCase().trim().equals("rail")){
            image_itinerary_logo_category.setBackgroundResource(R.drawable.categoryrail1);
        }
        else if(category_label.toLowerCase().trim().equals("transportation")){
            image_itinerary_logo_category.setBackgroundResource(R.drawable.categorytransportation1);
        }
        else if(category_label.toLowerCase().trim().equals("note")){
            image_itinerary_logo_category.setBackgroundResource(R.drawable.categorynote1);
        }
        else if(category_label.toLowerCase().trim().equals("map")){
            image_itinerary_logo_category.setBackgroundResource(R.drawable.categorymap1);
        }
        else if(category_label.toLowerCase().trim().equals("directions")){
            image_itinerary_logo_category.setBackgroundResource(R.drawable.categorydirection1);
        }
        else if(category_label.toLowerCase().trim().equals("activity")){
            image_itinerary_logo_category.setBackgroundResource(R.drawable.categoryactivity1);
        }
        else if(category_label.toLowerCase().trim().equals("hiking")){
            image_itinerary_logo_category.setBackgroundResource(R.drawable.categoryactivity1);
        }
        else{
            image_itinerary_logo_category.setBackgroundResource(R.drawable.icon_gps);
        }


        btn_edit_itinerary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final DialogPlus dialogPlus = DialogPlus.newDialog(ctx)
                        .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.edit_itinerary))
                        .setExpanded(false,0)
                        .create();

                View view1 = dialogPlus.getHolderView();

                final String[] selected_itinerary_category = new String[1];

                TextView textview_place_name = (TextView) view1.findViewById(R.id.textView_place_name_for_itinerary);
                TextView textview_trip_name = (TextView) view1.findViewById(R.id.textView_trip_name_itinerary);
                EditText et_itinerary_name = (EditText) view1.findViewById(R.id.txt_itinerary_name_edit);
                Button button_submit_edit = (Button) view1.findViewById(R.id.btnSubmitEditItinerary);
                Spinner spinner_itinerary_category = (Spinner) view1.findViewById(R.id.spinner_itinerary_category_edit);

                TextView text_view_start_date_edit_dialog = (TextView) view1.findViewById(R.id.textView_start_date_dialog_edit);
                TextView text_view_end_date_edit_dialog = (TextView) view1.findViewById(R.id.textView_end_date_dialog_edit);
                TextView text_view_start_time_edit_dialog = (TextView) view1.findViewById(R.id.textView_start_time_dialog_edit);
                TextView text_view_end_time_edit_dialog = (TextView) view1.findViewById(R.id.textView_edit_end_time_dialog);

                TextView text_view_start_date_value = (TextView) view1.findViewById(R.id.textView_start_date_edit);
                TextView text_view_end_date_value = (TextView) view1.findViewById(R.id.textView_end_date_edit);
                TextView text_view_start_time_value = (TextView) view1.findViewById(R.id.textView_start_time_edit);
                TextView text_view_end_time_value = (TextView) view1.findViewById(R.id.textView_end_time_edit);

                text_view_start_date_edit_dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initDateDialog(view1,text_view_start_date_value);
                    }
                });

                text_view_end_date_edit_dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initDateDialog(view1,text_view_end_date_value);
                    }
                });

                text_view_start_time_edit_dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openTimeDialog(view1, text_view_start_time_value);
                    }
                });

                text_view_end_time_edit_dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openTimeDialog(view1, text_view_end_time_value);
                    }
                });


                String place_name_string = text_place_name.getText().toString();
                textview_place_name.setText(place_name_string);
                textview_trip_name.setText("Trip Name: "+trip_name_label);

                et_itinerary_name.setText(itinerary_name);

                text_view_start_date_value.setText(start_date);
                text_view_end_date_value.setText(end_date);
                text_view_start_time_value.setText(start_time);
                text_view_end_time_value.setText(end_time);

                String[] itinerary_categories;

                itinerary_categories = new String[]{" -- Select Category -- ","Activity","Note","Restaurant","Transportation","Lodging","Meeting"};

                ArrayAdapter arrayAdapter_spinner_itinerary_categories = new ArrayAdapter(view1.getContext(),android.R.layout.simple_spinner_item,itinerary_categories);
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

                int spinner_selected_item = 0;

                for(int ii = 0;ii<itinerary_categories.length-1;ii++){
                    String cat_value = itinerary_categories[ii];

                    if(cat_value.trim().toLowerCase().equals(""+category_label.toLowerCase()+"")){
                        spinner_selected_item = ii;
                    }
                }

                spinner_itinerary_category.setSelection(spinner_selected_item);

                 button_submit_edit.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {


                         String itinerary_name = et_itinerary_name.getText().toString();



                                 int error_count = 0;
                                 String error_msg = "";

                                 String plan_start_date = "";
                                 String plan_end_date = "";
                                 String plan_start_time = "";
                                 String plan_end_time = "";

                                 plan_start_date = text_view_start_date_value.getText().toString();
                                 plan_end_date =  text_view_end_date_value.getText().toString();
                                 plan_start_time = text_view_start_time_value.getText().toString();
                                 plan_end_time = text_view_end_time_value.getText().toString();

                                 String ItineraryCategory = selected_itinerary_category[0];

                                 if(itinerary_name.isEmpty()){
                                     error_count+=1;
                                     error_msg += "Itinerary Name is Blank\n";
                                 }

                                 if(ItineraryCategory.isEmpty()){
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

                                     String formatted_start_date = "";
                                     String formatted_end_date = "";

                                     //formatted_start_date = formatDateTwo(trip_start);
                                     //formatted_end_date = formatDateTwo(trip_end);

                                     String formatted_plan_start_date = formatDate(plan_start_date);
                                     String formatted_plan_end_date = formatDate(plan_end_date);

                                     //Toast.makeText(getApplicationContext(),""+trip_start+" ("+formatted_start_date+") - "+trip_end+"("+formatted_end_date+")",Toast.LENGTH_LONG).show();
                                     //Toast.makeText(getApplicationContext(),""+formatted_plan_start_date+"",Toast.LENGTH_LONG).show();
                                     //Toast.makeText(getApplicationContext(),""+plan_start_date+"",Toast.LENGTH_LONG).show();


                                     boolean bool_check_date_range_date_start_input = isDateInRange(formatted_start_date, formatted_end_date, ctx, formatted_plan_start_date);
                                     boolean bool_check_date_range_date_end_input = isDateInRange(formatted_start_date, formatted_end_date, ctx, formatted_plan_end_date);

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

                                        //Toast.makeText(ctx, "Tourist ID: "+TouristIDValue+"\nTrip ID: "+trip_id+"\nPlace ID:"+place_id+"\nItinerary ID:"+Itinerary_ID+"",Toast.LENGTH_LONG).show();

                                         Map<String,Object> map_itinerary = new HashMap<>();
                                         map_itinerary.put("place_id",place_id);
                                         map_itinerary.put("trip_id",trip_id);
                                         map_itinerary.put("user_id",TouristIDValue);
                                         map_itinerary.put("itinerary_name",""+itinerary_name+"");
                                         map_itinerary.put("itinerary_category",ItineraryCategory);
                                         map_itinerary.put("plan_start_date",plan_start_date);
                                         map_itinerary.put("plan_start_time",plan_start_time);
                                         map_itinerary.put("plan_end_date",plan_end_date);
                                         map_itinerary.put("plan_end_time",plan_end_time);



                                         fStore = FirebaseFirestore.getInstance();
                                         fStore.collection("itinerary").
                                                 document(Itinerary_ID).
                                                 set(map_itinerary).
                                                 addOnSuccessListener(new OnSuccessListener<Void>() {
                                                     @Override
                                                     public void onSuccess(Void aVoid) {
                                                         Toast.makeText(v.getContext(),"Itinerary Details Successfully Changed!",Toast.LENGTH_LONG).show();

                                                         Intent i = new Intent(ctx, ItineraryList.class);


                                                         ctx.startActivity(i);
                                                     }
                                                 }).addOnFailureListener(new OnFailureListener() {
                                                     // inside on failure method we are
                                                     // displaying a failure message.
                                                     @Override
                                                     public void onFailure(@android.support.annotation.NonNull Exception e) {
                                                         //Toast.makeText(UpdateCourse.this, "Fail to update the data..", Toast.LENGTH_SHORT).show();
                                                     }
                                                 });



                                         // query for saving itinerary details edit

                                         dialogPlus.dismiss();

                                     }else{

                                         Toast.makeText(ctx,""+date_input_error_message+"",Toast.LENGTH_LONG).show();
                                         date_input_error_count = 0;
                                         date_input_error_message = "";

                                     }


                                 }else{

                                     Toast.makeText(ctx,""+error_msg+"",Toast.LENGTH_LONG).show();
                                     error_count = 0;
                                     error_msg = "";



                                 }




                     }



                 });

                dialogPlus.show();


               //Toast.makeText(v.getContext(),"Hey",Toast.LENGTH_LONG).show();

            }

        });

        btn_delete_itinerary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ItineraryID = "";

                //ItineraryID = text_itinerary_name.getText().toString();

                fStore = FirebaseFirestore.getInstance();

                itinerary_ID_value = ic_list.getItinerary_id();

                AlertDialog.Builder builder_delete_itinerary = new AlertDialog.Builder(v.getContext());
                builder_delete_itinerary.setTitle("Set as Done");
                builder_delete_itinerary.setMessage("Are you sure this schedule is done? Completed schedules will be deleted. \n");

                builder_delete_itinerary.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        fStore.collection("itinerary").

                                document(itinerary_ID_value).
                                delete().

                                addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()) {

                                            Toast.makeText(v.getContext(),"Schedule Listing Deleted",Toast.LENGTH_LONG).show();

                                           Intent i = new Intent(ctx, ItineraryList.class);

                                           ctx.startActivity(i);


                                        }
                                    }
                                });


                    }
                });
                builder_delete_itinerary.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(v.getContext(), "Selected Itinerary Listing not Deleted", Toast.LENGTH_SHORT).show();

                    }
                });
                builder_delete_itinerary.show();

            }
        });

        return convertView;
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
            //Toast.makeText(this,"Exception: "+e.getMessage()+"",Toast.LENGTH_LONG).show();
        }


        return bool;
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
                    //Toast.makeText(getApplicationContext(),"Time Format Error: "+e.getMessage().toString()+"",Toast.LENGTH_LONG).show();
                }


            }
        },hour,minute,false);
        timePickerDialog.show();
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


    private String formatTimeDisplayTo12Hrs(String inputTime, String delim){
        String formatted_time = "";
        String meridian = "";

        String array_string[] = new String[3];
        int i = 0;

        StringTokenizer st = new StringTokenizer(""+inputTime+"",""+delim+"");
        while (st.hasMoreTokens()) {
            array_string[i] = st.nextToken().toString();
            i++;
        }

        int hr = Integer.parseInt(array_string[0]);
        int min = Integer.parseInt(array_string[1]);
        int sec = Integer.parseInt(array_string[2]);

        String hr_string = "";
        String min_string = "";
        String sec_string = "";

        min_string = ""+min;
        sec_string = ""+sec;

        if(hr > 12){
            meridian = "PM";
            hr = hr - 12;
            hr_string = ""+hr+"";
        }else{
            if(hr < 1){
                hr_string = "12";
            }else{
                hr_string = ""+hr;
            }
            meridian = "AM";
        }

        if(min < 10 && min > -1){
           min_string = "0"+min+"";
        }

        formatted_time = hr_string+":"+min_string+" "+meridian+"";

        return formatted_time;
    }


    private String toMonthNumberString(String full_month_string){
        String return_value = "";

        if(full_month_string.toLowerCase().equals("january") || full_month_string.toLowerCase().equals("JAN")){
            return_value = "1";
        }else if(full_month_string.toLowerCase().equals("february") || full_month_string.toLowerCase().equals("FEB")){
            return_value = "2";
        }
        else if(full_month_string.toLowerCase().equals("march") || full_month_string.toLowerCase().equals("MAR")){
            return_value = "3";
        }
        else if(full_month_string.toLowerCase().equals("april") || full_month_string.toLowerCase().equals("APR")){
            return_value = "4";
        }
        else if(full_month_string.toLowerCase().equals("may") || full_month_string.toLowerCase().equals("MAY")){
            return_value = "5";
        }
        else if(full_month_string.toLowerCase().equals("june") || full_month_string.toLowerCase().equals("JUN")){
            return_value = "6";
        }
        else if(full_month_string.toLowerCase().equals("july") || full_month_string.toLowerCase().equals("JUL")){
            return_value = "7";
        }
        else if(full_month_string.toLowerCase().equals("august") || full_month_string.toLowerCase().equals("AUG")){
            return_value = "8";
        }
        else if(full_month_string.toLowerCase().equals("september") || full_month_string.toLowerCase().equals("SEP")){
            return_value = "9";
        }
        else if(full_month_string.toLowerCase().equals("october") || full_month_string.toLowerCase().equals("OCT")){
            return_value = "10";
        }
        else if(full_month_string.toLowerCase().equals("november") || full_month_string.toLowerCase().equals("NOV")){
            return_value = "11";
        }
        else if(full_month_string.toLowerCase().equals("december") || full_month_string.toLowerCase().equals("DEC")){
            return_value = "12";
        }

        return return_value;
    }

    private boolean isDateInRange(String s1, String s2, Context ctx, String input_date){

        boolean bool = true;

    SimpleDateFormat dateFormat = new SimpleDateFormat("MM.dd.yyyy");
    Calendar calendarFrom = Calendar.getInstance();
    Calendar calendarUntil = Calendar.getInstance();

    try{

        calendarFrom.setTime(dateFormat.parse(s1));
        calendarUntil.setTime(dateFormat.parse(s2));

        while (calendarFrom.compareTo(calendarUntil) != 0) {
            System.out.println(dateFormat.format(calendarFrom.getTime()));
            Toast.makeText(ctx, dateFormat.format(calendarFrom.getTime()),Toast.LENGTH_LONG).show();
            calendarFrom.add(Calendar.DATE, 1);
        }

    }catch(Exception e){


    }

    return bool;

    }




    private void msg(String msg, Context ctx){
        Toast.makeText(ctx,""+msg+"", Toast.LENGTH_LONG).show();
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

    private void displayPlaceDetails(String placeID, TextView text_place_name){

        fStore = FirebaseFirestore.getInstance();

        fStore.collection("places")
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

    } // displayPlaceDetails function







}
