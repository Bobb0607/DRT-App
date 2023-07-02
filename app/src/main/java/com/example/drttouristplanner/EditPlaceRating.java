package com.example.drttouristplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditPlaceRating extends AppCompatActivity {

    private Button btn_back_to_cardview_places;

    String barangay_name = "";
    String place_category = "";
    String rating_value = "";
    String review_value = "";
    String user_id = "";
    String review_id = "";
    String place_id = "";
    String date_time_rating = "";

    RatingBar ratingBar_place_rating_edit;
    EditText editText_review_edit;

    List<String> arraylist_words;

    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_place_rating);

        Intent from_content_adapter_intent = getIntent();
        barangay_name = from_content_adapter_intent.getStringExtra("content_data");
        place_category = from_content_adapter_intent.getStringExtra("target_data");
        rating_value = from_content_adapter_intent.getStringExtra("rating_value");
        review_value = from_content_adapter_intent.getStringExtra("review_value");
        user_id = from_content_adapter_intent.getStringExtra("user_id");
        place_id = from_content_adapter_intent.getStringExtra("place_id");
        date_time_rating = from_content_adapter_intent.getStringExtra("date_time_rating");
        review_id = from_content_adapter_intent.getStringExtra("review_id");

        ratingBar_place_rating_edit = (RatingBar) findViewById(R.id.ratingBar_place_rating_edit);
        editText_review_edit = (EditText) findViewById(R.id.editText_review_edit);

        ratingBar_place_rating_edit.setRating(Float.parseFloat(rating_value));
        editText_review_edit.setText(review_value);


        arraylist_words = new ArrayList<>();



        try{
            String text_file;
            InputStream is = getApplicationContext().getAssets().open("bla.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            while((text_file = reader.readLine()) != null){
                arraylist_words.add(text_file);
                //String filtered_word = filteredString(text_file);
                //Toast.makeText(getApplicationContext(),""+filtered_word+"",Toast.LENGTH_LONG).show();
            }
        }catch(Exception ioe){
            // Toast.makeText(getApplicationContext(),""+ioe.getMessage().toString()+"",Toast.LENGTH_LONG).show();
        }

        btn_back_to_cardview_places = (Button) findViewById(R.id.btnBackToPlaceCardView);

        btn_back_to_cardview_places.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            int error_count = 0;
            String string_errors = "";

           String rating_value = ""+ratingBar_place_rating_edit.getRating()+"";
           String review_value = ""+editText_review_edit.getText().toString()+"";

            if(rating_value.isEmpty()){
                error_count += 1;
                string_errors += "Please specify Your Rating\n";
            }

            if(review_value.isEmpty()){
                error_count += 1;
                string_errors += "Please specify Your Review\n";
            }

            if(error_count < 1){

                Date currentTime = Calendar.getInstance().getTime();
                date_time_rating = currentTime.toString();

                SimpleDateFormat simpleDateFormat;

                //simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss aaa z");
                simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss aaa");
                date_time_rating = simpleDateFormat.format(currentTime.getTime()).toString();

                String filtered_review_edit = filterInput(arraylist_words,review_value);

                updatePlaceRating(review_id,rating_value,filtered_review_edit,user_id,place_id,date_time_rating,v);

                if(barangay_name.equals("bayabas")){
                    Intent ii = new Intent(getApplicationContext(), Content_Bayabas.class);

                    ii.putExtra("target_data",""+place_category+"");
                    ii.putExtra("content_data",""+barangay_name+"");

                    startActivity(ii);
                }
                else if(barangay_name.equals("camachile")){
                    Intent ii = new Intent(getApplicationContext(), Content_Camachile.class);

                    ii.putExtra("target_data",""+place_category+"");
                    ii.putExtra("content_data",""+barangay_name+"");
                    startActivity(ii);
                }
                else if(barangay_name.equals("camachin")){
                    Intent ii = new Intent(getApplicationContext(), Content_Camachin.class);

                    ii.putExtra("target_data",""+place_category+"");
                    ii.putExtra("content_data",""+barangay_name+"");
                    startActivity(ii);
                }
                else if(barangay_name.equals("kalawakan")){
                    Intent ii = new Intent(getApplicationContext(), Content_Kalawakan.class);

                    ii.putExtra("target_data",""+place_category+"");
                    ii.putExtra("content_data",""+barangay_name+"");
                    startActivity(ii);
                }
                else if(barangay_name.equals("talbak")){
                    Intent ii = new Intent(getApplicationContext(), Content_Talbak.class);

                    ii.putExtra("target_data",""+place_category+"");
                    ii.putExtra("content_data",""+barangay_name+"");
                    startActivity(ii);
                }
                else{
                    Intent ii = new Intent(getApplicationContext(), Barangay.class);
                    startActivity(ii);
                }
            }else{
                Toast.makeText(getApplicationContext(),""+string_errors+"",Toast.LENGTH_LONG).show();
            }


            }
        });
    }


    private String filteredString(String str_word_input){
        String str = "";

        String[] array_word = new String[str_word_input.length()];
        String[] array_final_word = new String[str_word_input.length()];

        array_word = str_word_input.split("");

        for(int i = 0;i<str_word_input.length();i++){
            if(i > 0 && i<(str_word_input.length()-1)){
                array_final_word[i] = "*";
            }else{
                array_final_word[i] = array_word[i];
            }
        }

        for(int j = 0;j<array_final_word.length;j++){
            str += array_final_word[j]+"";
        }

        return str;
    }

    private Boolean isProfanity(List<String> array_list, String word_test){
        boolean bool = false;

        for(int i = 0;i<array_list.size();i++){
            String word_from_array = array_list.get(i);
            if(word_from_array.toLowerCase().equals(word_test.toLowerCase())){
                bool = true;
            }
        }

        return bool;
    }

    private String filterInput(List<String> arraylist_words, String input_paragraph){
        String str = "";


        String[] word_p;
        word_p = input_paragraph.split(" ");

        for(int i = 0;i<word_p.length;i++){
            String word_scan = remove_special_characters(word_p[i]);
            String word_scan_no_char_filter = word_p[i];
            if(isProfanity(arraylist_words,word_scan.trim()) == true){
                str += filteredString(word_scan_no_char_filter)+" ";
            }else{
                str += word_scan_no_char_filter+" ";
            }
        }

        return str;
    }

    private String remove_special_characters(String word_input){
        String str = "";

        str = word_input.replaceAll("[!@#$%^&,]", "");

        return str;
    }

    private void updatePlaceRating(String ReviewID, String rating_value, String review_value, String user_id, String place_id, String date_time_rating, View v) {


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