package com.example.drttouristplanner;

import static android.content.Intent.getIntent;
import static android.content.Intent.makeRestartActivityTask;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
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

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ViewHolder>{
    private final RecyclerViewInterface recyclerViewInterface;
    private List<ContentModel> mContents;

    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private FirebaseUser user;
    private String tourist_iD;

    private String source_latlong_value = "";

    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    String latitude, longitude;
    String textview_moreinfo;
    public void setFilteredList(List<ContentModel> filteredList) {
        this.mContents = filteredList;
        notifyDataSetChanged();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView titleTextView;
        public TextView descriptionTextView;
        public TextView place_rating;
        public TextView textview_view_reviews;
        public TextView tvPlaceID, tvBarangayName, tvPlaceCategoryName,tvLatLongValue;
        public ImageSlider displayImage;
        public CardView cardView;
        public Button button,button_rate_place, button_view_route;
        public RatingBar ratingBar_content;
        public TextView textview_moreinfo;



        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView, RecyclerViewInterface recyclerViewInterface) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            List<String> arraylist_words;

            arraylist_words = new ArrayList<>();

            titleTextView = (TextView) itemView.findViewById(R.id.tvTitle);
            tvPlaceID = (TextView) itemView.findViewById(R.id.tvPlaceID);
            tvBarangayName = (TextView) itemView.findViewById(R.id.tvBarangayName);
            tvLatLongValue = (TextView) itemView.findViewById(R.id.tvLatLongValue);

            tvPlaceCategoryName = (TextView) itemView.findViewById(R.id.tvPlaceCategoryName);
            textview_view_reviews = (TextView) itemView.findViewById(R.id.textview_view_reviews);

            descriptionTextView = (TextView) itemView.findViewById(R.id.tvDescription);
            place_rating = (TextView) itemView.findViewById(R.id.textView_place_rating);
            displayImage = (ImageSlider) itemView.findViewById(R.id.ivBanner);
            cardView = (CardView) itemView.findViewById(R.id.cardview);
            button = (Button) itemView.findViewById(R.id.create_trip_button);
            button_rate_place = (Button) itemView.findViewById(R.id.rate_place_btn);
            button_view_route = (Button) itemView.findViewById(R.id.view_route_btn);
            ratingBar_content = (RatingBar) itemView.findViewById(R.id.ratingBar_content);
            textview_moreinfo = (TextView)  itemView.findViewById(R.id.textview_moreinfo);
            textview_view_reviews.setPaintFlags(textview_view_reviews.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            textview_moreinfo.setPaintFlags(textview_moreinfo.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerViewInterface != null){
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });


            // profanity words will be scanned from a text file bla.txt in the assets folder of this project
            // words will be saved to arraylist_words and it will be used by word filter functions for profanity for censoring words with * in between letters

            getUserAddress(itemView.getContext(),tourist_iD);

            try{
                String text_file;
                InputStream is = itemView.getContext().getAssets().open("bla.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                while((text_file = reader.readLine()) != null){
                    arraylist_words.add(text_file);

                }
            }catch(Exception ioe){
                // Toast.makeText(getApplicationContext(),""+ioe.getMessage().toString()+"",Toast.LENGTH_LONG).show();
            }

            textview_view_reviews.setPaintFlags(textview_view_reviews.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

            textview_view_reviews.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String placeID_string = tvPlaceID.getText().toString();
                    String barangayName_string = tvBarangayName.getText().toString();
                    String placeCategoryName_string = tvPlaceCategoryName.getText().toString();

                    final DialogPlus dialogPlus = DialogPlus.newDialog(itemView.getContext())
                            .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.view_reviews))
                            .setExpanded(false,0)
                            .create();

                    View view1 = dialogPlus.getHolderView();

                    final Button btnCloseReviews = view1.findViewById(R.id.btnCloseViewReviews);
                    final ListView listview_reviews = view1.findViewById(R.id.listview_reviews);
                    final TextView text_your_rating = view1.findViewById(R.id.textView_your_rating_value);
                    final TextView text_your_review = view1.findViewById(R.id.textView_your_review_string);
                    final RatingBar ratingBar_your_rating = view1.findViewById(R.id.ratingBar_your_rating);

                    final TextView text_no_rating_label = view1.findViewById(R.id.textView_record_filer);

                    final TextView text_edit_review = view1.findViewById(R.id.textView_edit_review);
                    final TextView text_delete_review = view1.findViewById(R.id.textView_delete_review);
                    final TextView textview_moreinfo = view1.findViewById(R.id.textview_moreinfo);

                    final LinearLayout linearlayout_your_review = view1.findViewById(R.id.linearlayout_your_review);



                    btnCloseReviews.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogPlus.dismiss();
                        }
                    });

                    ArrayList<ReviewsClass> array_rc_list;
                    ArrayList<ReviewsClass> array_own_rc_list;
                    ArrayList<ReviewsClass> array_own_rc_list_edit;

                    array_rc_list = new ArrayList<>();
                    array_own_rc_list = new ArrayList<>();
                    array_own_rc_list_edit = new ArrayList<>();

                    fStore.collection("reviews")
                            .whereEqualTo("place_id",""+placeID_string+"")
                            // in the database
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                    String rating = "";
                                    String user_id_getString = "";
                                    String review_str = "";
                                    Integer rating_count = 0;

                                    String your_rating_string = "";
                                    String your_review_string = "";
                                    final String your_review_id = "";

                                    double ratingFloat= 0.2f;
                                    double ratingFloatSum = 0;
                                    double mRatingAve = 0;

                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {

                                            String id = document.getId();
                                            String date_time_rating_getString   = (String) document.get("date_time_rating");
                                            String place_id_getString           = (String) document.get("place_id");
                                            String ratings_getString            = (String) document.get("ratings");
                                            String review_getString             = (String) document.get("review");
                                            user_id_getString                   = (String) document.get("user_id");

                                            if(user_id_getString.equals(tourist_iD)){
                                                your_rating_string = ratings_getString;
                                                your_review_string = review_getString;

                                                ReviewsClass own_rc = new ReviewsClass(""+id+"",""+review_getString+"",""+ratings_getString+"",""+place_id_getString+"",""+date_time_rating_getString+"",""+user_id_getString+"");
                                                array_own_rc_list.add(own_rc);
                                                array_rc_list.add(own_rc);

                                                button_rate_place.setVisibility(View.GONE);
                                            }else{
                                                ReviewsClass rc = new ReviewsClass(""+id+"",""+review_getString+"",""+ratings_getString+"",""+place_id_getString+"",""+date_time_rating_getString+"",""+user_id_getString+"");
                                                array_rc_list.add(rc);
                                            }

                                        }

                                        if(your_rating_string.equals("")){
                                            linearlayout_your_review.setVisibility(View.GONE);
                                        }else{
                                            text_your_review.setText(your_review_string);
                                            text_your_rating.setText("You rated this: "+your_rating_string+" out of 5 stars");
                                            ratingBar_your_rating.setRating(Float.parseFloat(""+your_rating_string+""));
                                            //button_rate_place.setVisibility(View.GONE);
                                        }

                                        ArrayList<PlaceCategoryClass> p_cat_list;
                                        p_cat_list = new ArrayList<>();

                                        PlaceCategoryClass pcclass = new PlaceCategoryClass("",""+placeCategoryName_string+"",""+barangayName_string+"");
                                        p_cat_list.add(pcclass);

                                        ArrayAdapter reviews_adapter = new ReviewsListAdapter(view1.getContext(), array_rc_list, p_cat_list);
                                        listview_reviews.setAdapter(reviews_adapter);

                                        if(array_rc_list.size() > 0){
                                            text_no_rating_label.setVisibility(View.GONE);
                                        }

                                        text_edit_review.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                String content_data_place_name = "";
                                                String target_data_category_name = "";
                                                String rating_value = "";
                                                String review_value = "";
                                                String review_id = "";
                                                String place_id = "";
                                                String date_time_rating = "";

                                                review_id = array_own_rc_list.get(0).getReview_id();
                                                place_id = array_own_rc_list.get(0).getPlace_id();
                                                date_time_rating = array_own_rc_list.get(0).getDate_time_rating();

                                                rating_value = ""+ratingBar_your_rating.getRating()+"";
                                                review_value = text_your_review.getText().toString();

                                                // String filtered_review_edit = filterInput(arraylist_words,review_value);

                                                content_data_place_name = barangayName_string;
                                                target_data_category_name = placeCategoryName_string;

                                                Intent ii = new Intent(view1.getContext(),EditPlaceRating.class);
                                                ii.putExtra("target_data",target_data_category_name);
                                                ii.putExtra("content_data",content_data_place_name);
                                                ii.putExtra("rating_value",rating_value);
                                                ii.putExtra("review_value",review_value);
                                                ii.putExtra("review_id",review_id);
                                                ii.putExtra("user_id",tourist_iD);
                                                ii.putExtra("place_id",place_id);
                                                ii.putExtra("date_time_rating",date_time_rating);
                                                view1.getContext().startActivity(ii);
                                                button_rate_place.setVisibility(View.GONE);

                                            }
                                        }); // text_view edit review


                                        text_delete_review.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {


                                                AlertDialog.Builder builder_delete_review = new AlertDialog.Builder(v.getContext());
                                                builder_delete_review.setTitle("Delete Review");
                                                builder_delete_review.setMessage("Are you sure you want to delete this review?\n");

                                                builder_delete_review.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {

                                                        fStore.collection("reviews").

                                                                document(array_own_rc_list.get(0).getReview_id()).

                                                                delete().

                                                                addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {

                                                                        if (task.isSuccessful()) {

                                                                            //Toast.makeText(UpdateCourse.this, "Course has been deleted from Database.", Toast.LENGTH_SHORT).show();
                                                                            //Intent i = new Intent(UpdateCourse.this, MainActivity.class);
                                                                            //startActivity(i);
                                                                            Toast.makeText(view1.getContext(),"Review deleted", Toast.LENGTH_LONG).show();
                                                                            dialogPlus.dismiss();
                                                                            button_rate_place.setVisibility(View.VISIBLE);

                                                                        } else {

                                                                            //Toast.makeText(UpdateCourse.this, "Fail to delete the course. ", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }
                                                                });


                                                    }
                                                });
                                                builder_delete_review.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        Toast.makeText(v.getContext(), "Review/Ratings not Deleted", Toast.LENGTH_SHORT).show();

                                                    }
                                                });
                                                builder_delete_review.show();

                                            }
                                        }); // text view delete review

                                    }

                                }
                            });

                    dialogPlus.show();
                }
            });

            button_view_route.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    String placeID_string = tvPlaceID.getText().toString();
                    String barangayName_string = tvBarangayName.getText().toString();
                    String latlong_string = tvLatLongValue.getText().toString();

                    final DialogPlus dialogPlus = DialogPlus.newDialog(itemView.getContext())
                            .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.map_view))
                            .setExpanded(false,0)
                            .create();

                    View view1 = dialogPlus.getHolderView();


                    WebView web_map = (WebView) view1.findViewById(R.id.webview_map);

                    WebSettings webSettings = web_map.getSettings();
                    webSettings.setJavaScriptEnabled(true);


                    getUserAddress(view1.getContext(),tourist_iD);



                    String destination_latlong_value = latlong_string;

                    web_map.setWebViewClient(new WebViewClient());
                    web_map.loadUrl("https://www.google.com/maps/dir/"+source_latlong_value+"/"+destination_latlong_value+"/");

                    dialogPlus.show();
                }
            });

            button_rate_place.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String placeID_string = tvPlaceID.getText().toString();

                    final DialogPlus dialogPlus = DialogPlus.newDialog(itemView.getContext())
                            .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.place_rating))
                            .setExpanded(false,0)
                            .create();

                    View view1 = dialogPlus.getHolderView();

                    final Button btnSubmitRating = view1.findViewById(R.id.btnSubmitRating);
                    final RatingBar ratingBar_place_rating = view1.findViewById(R.id.ratingBar_place_rating);
                    final TextView textview_review = view1.findViewById(R.id.textview_review);
                    final TextView textview_rate_label = view1.findViewById(R.id.textView_label_rate);

                    textview_rate_label.setText(titleTextView.getText().toString()+"\nRate this and Share your experience!");

                    btnSubmitRating.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String max_num_star_count = "";
                            String ratingbar_current_value = "";
                            String review_string = "";
                            String message_string = "";
                            String date_time_now = "";
                            String place_id = placeID_string;

                            Date currentTime = Calendar.getInstance().getTime();
                            date_time_now = currentTime.toString();

                            SimpleDateFormat simpleDateFormat;

                            //simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss aaa z");
                            simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss aaa");
                            date_time_now = simpleDateFormat.format(currentTime.getTime()).toString();


                            max_num_star_count = ""+ratingBar_place_rating.getNumStars()+"";
                            ratingbar_current_value = ""+ratingBar_place_rating.getRating()+"";
                            review_string = textview_review.getText().toString();

                            String filtered_review = filterInput(arraylist_words,review_string);

                            int error_count = 0;
                            String error_message = "";

                            if(ratingbar_current_value.equals("0.0")){
                                error_count += 1;
                                error_message += "You haven't cast your rating\n";
                            }

                            if(review_string.isEmpty()){
                                error_count += 1;
                                error_message += "Review is blank\n";
                            }

                            if(error_count < 1){

                                fStore = FirebaseFirestore.getInstance();
                                String ReviewID = fStore.collection("reviews").document().getId();
                                DocumentReference documentReference = fStore.collection("reviews").document(ReviewID);
                                Map<String,Object> user_review = new HashMap<>();
                                user_review.put("review" ,filtered_review);
                                user_review.put("ratings",ratingbar_current_value);
                                user_review.put("user_id",tourist_iD);
                                user_review.put("place_id",""+place_id+"");
                                user_review.put("date_time_rating",date_time_now);

                                documentReference.set(user_review).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                        Toast.makeText(view1.getContext(),"You have rated this place "+ratingBar_place_rating.getRating()+" out of 5 stars",Toast.LENGTH_LONG).show();
                                        dialogPlus.dismiss();
                                        button_rate_place.setVisibility(View.GONE);

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                        Toast.makeText(itemView.getContext(),"Error rating submission, Try again later",Toast.LENGTH_LONG).show();

                                    }
                                });


                            }else{
                                Toast.makeText(view1.getContext(),""+error_message+"",Toast.LENGTH_LONG).show();
                            }


                        }
                    });

                    dialogPlus.show();

                }
            });

            ratingBar_content.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    //String rating_num_stars = ""+ratingBar_content.getNumStars()+"";
                    //String rating_value = ""+ratingBar_content.getRating()+"";
                    //Toast.makeText(itemView.getContext(),"You rate this "+rating_value+" out of "+rating_num_stars+" star(s)!",Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    private void alertBuilderForUnsetProfileAddress(Context ctx){
        AlertDialog.Builder builder_set_profile_address = new AlertDialog.Builder(ctx);
        builder_set_profile_address .setTitle("You did not set address to your profile");
        builder_set_profile_address .setMessage("Go to profile > address?");

        builder_set_profile_address .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent ii = new Intent(ctx,edit_profile.class);
                ctx.startActivity(ii);

            }
        });
        builder_set_profile_address .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(ctx, "Address not set", Toast.LENGTH_SHORT).show();

            }
        });
        builder_set_profile_address .show();
    }

    private void getUserAddress(Context ctx, String my_tourist_id){
        final String[] user_address = {""};

        ArrayList<String> user_address_list;
        user_address_list = new ArrayList<>();

        FirebaseFirestore fStore2;

        fStore2 = FirebaseFirestore.getInstance();

        fStore2.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {


                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                String user_ID = document.getId();
                                String user_address_value = (String) document.get("user_address");

                                if(user_ID.equals(my_tourist_id)){

                                    //Toast.makeText(ctx,"My User ID:"+my_tourist_id+"\nTourist ID Found: "+user_ID+"", Toast.LENGTH_LONG).show();
                                    user_address_list.add(user_address_value);
                                    source_latlong_value = user_address_value;

                                }

                            }

                            if(user_address_list.size() > 0){
                                user_address[0] = user_address_list.get(0).toString();
                            }else{
                                //Toast.makeText(ctx,"You did not set address in your profile", Toast.LENGTH_LONG).show();
                                alertBuilderForUnsetProfileAddress(ctx);
                            }
                        }
                    }
                });

        /**
         if(user_address_list.size() > 0){

         }else{
         Toast.makeText(ctx,"Address List Value is less than 1",Toast.LENGTH_LONG).show();
         }**/

        if(source_latlong_value.equals(" ") || source_latlong_value.isEmpty()){
            //Toast.makeText(ctx,"You did not set your address in your profile",Toast.LENGTH_LONG).show();
        }


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


    // Pass in the contact array into the constructor
    public ContentAdapter(List<ContentModel> content,
                          RecyclerViewInterface recyclerViewInterface) {
        mContents = content;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contentView = inflater.inflate(R.layout.custom_content_layout, parent, false);

        fAuth = FirebaseAuth.getInstance();
        tourist_iD = fAuth.getCurrentUser().getUid();
        fStore = FirebaseFirestore.getInstance();

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contentView, recyclerViewInterface);
        return viewHolder;


    }

    @Override
    public void onBindViewHolder(@NonNull ContentAdapter.ViewHolder holder, int position) {

        ContentModel model = mContents.get(position);

        String string_average_rating = "";
        String place_id = model.getPlaceID();

        ArrayList<ReviewsClass> array_rc;

        array_rc = new ArrayList<>();


        // get all reviews details for a specific place/spot identified by place_id

        fStore.collection("reviews")
                .whereEqualTo("place_id",""+place_id+"")
                // in the database
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        String rating = "";
                        String user_id_getString = "";
                        String review_str = "";
                        Integer rating_count = 0;
                        double ratingFloat= 0.2f;
                        double ratingFloatSum = 0;
                        double mRatingAve = 0;

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                String id = document.getId();
                                String date_time_rating_getString   = (String) document.get("date_time_rating");
                                String place_id_getString           = id;
                                String ratings_getString            = (String) document.get("ratings");
                                String review_getString             = (String) document.get("review");
                                user_id_getString                   = (String) document.get("user_id");

                                // adding all reviews record from specified place
                                // data is presented into ReviewsClass to fit in ArrayList array_rc

                                ReviewsClass rc = new ReviewsClass(""+id+"",""+review_getString+"",""+ratings_getString+"",""+place_id_getString+"",""+date_time_rating_getString+"",""+user_id_getString+"");

                                // add each data in array_rc ArrayList
                                array_rc.add(rc);
                            }

                            // read all data from ArrayList array_rc

                            for(int i = 0;i<array_rc.size()-1;i++){

                                // using array_rc with a class ReviewsClass, calling getRatings() function gets all ratings saved in array_rc ArrayList
                                // while i is incrementing, it identify each record index of the array_rc
                                // get(i) specifies each record/row from array_rc ArrayList

                                String rating_str = array_rc.get(i).getRatings();

                                if(rating_str.equals(null)){

                                }else{
                                    // convert rating_str String to Double and save to ratingFloat variable, which is declared as double
                                    ratingFloat = Double.parseDouble(rating_str);

                                    // sum all ratingFloat or all ratings converted into double
                                    ratingFloatSum += ratingFloat;

                                    // counts all rating record
                                    //rating_count+=1;
                                }
                            }

                            rating_count = array_rc.size();

                            String string_ave_rating = String.format("%.1f", mRatingAve);
                            String string_ave = "";

                            if(rating_count > 0){

                                if(rating_count == 1){
                                    mRatingAve = Double.parseDouble(array_rc.get(0).getRatings());
                                    string_ave_rating = String.format("%.1f", mRatingAve);
                                    string_ave = String.format("%.1f", mRatingAve);
                                }else{
                                    // sum of all ratings divided by number of rating records for getting the average rating
                                    // this will display on each place/spot cardview later

                                    mRatingAve = ratingFloatSum/rating_count;
                                    string_ave_rating = String.format("%.1f", mRatingAve);
                                    string_ave = String.format("%.1f", mRatingAve);
                                }

                            }else{
                                string_ave = "No rating yet";
                            }



                            //Toast.makeText(this,"rating count "+rating_count+"",Toast.LENGTH_LONG).show();

                            if(tourist_iD.equals(user_id_getString)){
                                holder.button_rate_place.setVisibility(View.GONE);
                            }else{
                                holder.button_rate_place.setVisibility(View.VISIBLE);
                            }



                            // Set item views based on your views and data model

                            holder.titleTextView.setText(model.getTitle());
                            holder.tvPlaceID.setText(model.getPlaceID());
                            holder.tvBarangayName.setText(model.getBarangayName());
                            holder.tvLatLongValue.setText(model.getLatlong_value());
                            holder.tvPlaceCategoryName.setText(model.getPlaceCategoryName());
                            holder.descriptionTextView.setText(model.getDetails());
                            holder.textview_moreinfo.setText(model.getInfo());


                            holder.displayImage.setImageList(model.getImage());

                            holder.cardView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.card_anim));

                            holder.place_rating.setText("Tourist Ratings: "+string_ave+"");
                            holder.ratingBar_content.setRating(Float.parseFloat(""+string_ave_rating+""));

                            holder.textview_moreinfo.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                                    View dialogView=LayoutInflater.from(v.getRootView().getContext()).inflate(R.layout.custom_dialogbox_touristspot_information,null);
                                    TextView tvInfoTitle;
                                    TextView TvInfo;

                                    tvInfoTitle = dialogView.findViewById(R.id.tvInfoTitle);
                                    TvInfo = dialogView.findViewById(R.id.TvInfo);

                                    tvInfoTitle.setText(model.getTitle());

                                    if(model.getTitle().equals("Candle Monument")){
                                        TvInfo.setText(R.string.candlemonument);

                                    }
                                    else if(model.getTitle().equals("Malangaan Spring")){
                                        TvInfo.setText(R.string.malangaan);

                                    }
                                    else if(model.getTitle().equals("Palanguyan")){
                                        TvInfo.setText(R.string.palanguyan);

                                    }
                                    else if(model.getTitle().equals("Secret Falls")){
                                        TvInfo.setText(R.string.secretfalls);

                                    }
                                    else if(model.getTitle().equals("Simbahang Bato")){
                                        TvInfo.setText(R.string.simbahangbato);

                                    }
                                    else if(model.getTitle().equals("Talon ni Eba")){
                                        TvInfo.setText(R.string.talonnieva);

                                    }
                                    else if(model.getTitle().equals("Talon ni Pari")){
                                        TvInfo.setText(R.string.talonpari);

                                    }
                                    else if(model.getTitle().equals("Tangke")){
                                        TvInfo.setText(R.string.tangkeriver);

                                    }
                                    else if(model.getTitle().equals("Tila Pilon Hills")){
                                        TvInfo.setText(R.string.tilapilonriver);

                                    }
                                    else if(model.getTitle().equals("Tila Pilon Hills Cafe")){
                                        TvInfo.setText(R.string.tilapilonhillscafe);

                                    }
                                    else if(model.getTitle().equals("Verdibia Falls")){
                                        TvInfo.setText(R.string.verdibiafalls);

                                    }else{
                                        TvInfo.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum");
                                    }




                                    builder.setView(dialogView);
                                    builder.setCancelable(true);
                                    builder.show();

                                }
                            });


                        }


                    }

                });

    }


    @Override
    public int getItemCount() {
        return mContents.size();
    }

}
