package com.example.drttouristplanner;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Rating;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.concurrent.Executor;

public class ReviewsListAdapter extends ArrayAdapter<ReviewsClass> {

    private Context ctx;
    private ArrayList<ReviewsClass> reviews_class_list = new ArrayList<>();
    private ArrayList<PlaceCategoryClass> pcclass_list = new ArrayList<>();
    private FirebaseFirestore fstore;

    FirebaseAuth fAuth;
    StorageReference storageReference;

    public ReviewsListAdapter(Context context, ArrayList<ReviewsClass> ReviewsListArray, ArrayList<PlaceCategoryClass> PlaceCategoryListArray){
        super(context, R.layout.reviews_list_item, ReviewsListArray);
        this.reviews_class_list = ReviewsListArray;
        this.ctx = context;
        this.pcclass_list = PlaceCategoryListArray;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){


        ReviewsClass rc_list = reviews_class_list.get(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.reviews_list_item,parent,false);
        }

        LinearLayout linearlayout_review_options = (LinearLayout) convertView.findViewById(R.id.linearlayout_review_options);
        TextView text_tourist_name = (TextView) convertView.findViewById(R.id.textView_tourist_name);
        TextView text_tourist_review = (TextView) convertView.findViewById(R.id.textView_tourist_review);
        TextView text_tourist_rating_value = (TextView) convertView.findViewById(R.id.textView_rating_value);
        TextView text_tourist_rating_date_time = (TextView) convertView.findViewById(R.id.textView_date_time_rated);
        RatingBar rating_from_tourist = (RatingBar) convertView.findViewById(R.id.ratingFromTourist);
        ImageView image_profile = (ImageView) convertView.findViewById(R.id.imageView_tourist_profile_pic);

        TextView textView_edit_from_list = (TextView) convertView.findViewById(R.id.textView_edit_review_from_list);
        TextView textView_delete_from_list = (TextView) convertView.findViewById(R.id.textView_delete_review_from_list);

        String review_id_value = rc_list.getReview_id();
        String place_id_value = rc_list.getPlace_id();
        String date_time_rating_value = rc_list.getDate_time_rating();
        String tourist_id = rc_list.getUser_id();
        String tourist_review = rc_list.getReview();
        String tourist_rating = rc_list.getRatings();
        String date_time_posted = rc_list.getDate_time_rating();


        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        String MyTouristID = fAuth.getCurrentUser().getUid();

        StorageReference profileRef = storageReference.child("users/"+tourist_id+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(image_profile);
            }


            public void onFailure(){

                Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/drt-tourist-planner-5bb3d.appspot.com/o/default_images%2Fusericondesign1.png?alt=media&token=cb18f4bd-179c-4282-9a16-f1361f1dd2c1").into(image_profile);

            }
        });


        String barangayName_string  = pcclass_list.get(0).getBarangay_name();
        String placeCategoryName_string = pcclass_list.get(0).getPlace_category_name();

        textView_edit_from_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(ctx,"edit",Toast.LENGTH_LONG).show();

                String content_data_place_name = "";
                String target_data_category_name = "";
                String rating_value = "";
                String review_value = "";
                String review_id = "";
                String place_id = "";
                String date_time_rating = "";




                review_id = review_id_value;
                place_id = place_id_value;
                date_time_rating = date_time_rating_value;

                rating_value =  tourist_rating;
                review_value = tourist_review;

                // String filtered_review_edit = filterInput(arraylist_words,review_value);

                content_data_place_name = barangayName_string;
                target_data_category_name = placeCategoryName_string;

                Intent ii = new Intent(ctx,EditPlaceRating.class);
                ii.putExtra("target_data",target_data_category_name);
                ii.putExtra("content_data",content_data_place_name);
                ii.putExtra("rating_value",rating_value);
                ii.putExtra("review_value",review_value);
                ii.putExtra("review_id",review_id);
                ii.putExtra("user_id",MyTouristID);
                ii.putExtra("place_id",place_id);
                ii.putExtra("date_time_rating",date_time_rating);
                ctx.startActivity(ii);

            }
        });

        textView_delete_from_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fstore = FirebaseFirestore.getInstance();
                AlertDialog.Builder builder_delete_review = new AlertDialog.Builder(ctx);
                builder_delete_review.setTitle("Delete Review");
                builder_delete_review.setMessage("Are you sure you want to delete this review?\n");

                builder_delete_review.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        fstore.collection("reviews").

                                document(review_id_value).

                                delete().

                                addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()) {

                                            Toast.makeText(ctx,"Review deleted", Toast.LENGTH_LONG).show();
                                            //dialogPlus.dismiss();

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
                        Toast.makeText(ctx, "Review/Ratings not Deleted", Toast.LENGTH_SHORT).show();

                    }
                });
                builder_delete_review.show();

            }
        });

        if(tourist_id.equals(MyTouristID)){

            linearlayout_review_options.setVisibility(View.VISIBLE);

        }else{

            linearlayout_review_options.setVisibility(View.INVISIBLE);

        }


        displayUserDetails(tourist_id, text_tourist_name,textView_edit_from_list,textView_delete_from_list);
        text_tourist_review.setText(tourist_review);
        text_tourist_rating_value.setText("Rating: "+tourist_rating+"\n");
        text_tourist_rating_date_time.setText(date_time_posted);


        if(tourist_rating.equals(null)){

            rating_from_tourist.setRating(Float.parseFloat("0"));

        }else{
           rating_from_tourist.setRating(Float.parseFloat(""+tourist_rating+""));
        }

        return convertView;
    }


    private void displayUserDetails(String user_id, TextView text_tourist_name, TextView edit_button, TextView delete_button){

        fstore = FirebaseFirestore.getInstance();

        ArrayList<UserClass> uc_list;

        uc_list = new ArrayList<>();

        fstore.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        String first_name = "";
                        String user_ID = "";

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                String userID = document.getId();
                                String firstName = (String) document.get("first_name");

                                if(userID.equals(user_id)){
                                    UserClass uc = new UserClass(""+userID+"",""+firstName+"","","","","","","");
                                    uc_list.add(uc);
                                    first_name = firstName;
                                    user_ID = user_id;
                                    text_tourist_name.setText(first_name);

                                }else{

                                }


                            }
                        }
                    }
                });

    }







}
