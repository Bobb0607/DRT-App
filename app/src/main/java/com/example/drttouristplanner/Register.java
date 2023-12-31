package com.example.drttouristplanner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Paint;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{8,}" +               //at least 8 characters
                    "$");
    public static final String TAG = "TAG";

    EditText myFirstname,myLastname, myEmail, myPassword, myConfirmPassword, myAddress;
    Button myRegisterBtn;
    TextView myLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progBar;
    boolean passwordVisible;
    FirebaseFirestore fStore;
    String tourist_iD;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        myFirstname = findViewById(R.id.firstName);
        myLastname = findViewById(R.id.lastName);
        myEmail = findViewById(R.id.Email);
        myAddress = findViewById(R.id.AddressInputrgstr);
        myPassword = findViewById(R.id.password);
        myRegisterBtn = findViewById(R.id.registerBtn);
        myLoginBtn = findViewById(R.id.createTxt);
        myConfirmPassword = findViewById(R.id.confirmPassword);


        myLoginBtn.setPaintFlags(myLoginBtn.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        //initialize places
        Places.initialize(getApplicationContext(),"AIzaSyCTFEhjwd3MS971CvMG85qIFLWXEYi-Q8s");
        myAddress.setFocusable(false);
        myAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Initialize place field list
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS,Place.Field.LAT_LNG,Place.Field.NAME);

                //Create intent
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,
                        fieldList).build(Register.this);
                //Start activity result
                startActivityForResult(intent, 100);
            }
        });



        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        progBar = findViewById(R.id.progressBar);

        myPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right=2;

                if (event.getAction()==MotionEvent.ACTION_UP){
                    if (event.getRawX()>=myPassword.getRight()-myPassword.getCompoundDrawables()[Right].getBounds().width()){
                        int selection=myPassword.getSelectionEnd();
                        if(passwordVisible){
                            //SET DRAWABLE IMAGE HERE
                            myPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.visibilityoffwhite,0);
                            //HIDE PASSWORD
                            myPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible=false;
                        }else {
                            //SET DRAWABLE IMAGE HERE
                            myPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.visibilitywhite,0);
                            //SHOW PASSWORD
                            myPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible=true;

                        }
                        myPassword.setSelection(selection);
                        return true;
                    }

                }

                return false;
            }
        });

        myConfirmPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right=2;

                if (event.getAction()==MotionEvent.ACTION_UP){
                    if (event.getRawX()>=myConfirmPassword.getRight()-myConfirmPassword.getCompoundDrawables()[Right].getBounds().width()){
                        int selection=myConfirmPassword.getSelectionEnd();
                        if(passwordVisible){
                            //SET DRAWABLE IMAGE HERE
                            myConfirmPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.visibilityoffwhite,0);
                            //HIDE PASSWORD
                            myConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible=false;
                        }else {
                            //SET DRAWABLE IMAGE HERE
                            myConfirmPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.visibilitywhite,0);
                            //SHOW PASSWORD
                            myConfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible=true;

                        }
                        myConfirmPassword.setSelection(selection);
                        return true;
                    }

                }

                return false;
            }
        });



        myRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = myEmail.getText().toString().trim();
                String password = myPassword.getText().toString().trim();
                String confirmPassword = myConfirmPassword.getText().toString().trim();
                String firstName = myFirstname.getText().toString().trim();
                String lastName = myLastname.getText().toString().trim();
                String addressrgstr = myAddress.getText().toString().trim();



                //VALIDATE EMAIL ADDRESS (REGULAR EXPRESSION)
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if (TextUtils.isEmpty(firstName)) {
                    myFirstname.setError("Your name is required!");
                    return;
                }
                if (firstName.length() < 3) {
                    myFirstname.setError("First name must be at least 3 characters");
                    return;
                }

                if (firstName.length() >= 50) {
                    myFirstname.setError("Maximum characters for first name is 50");
                    return;
                }

                if (TextUtils.isEmpty(lastName)) {
                    myLastname.setError("Your name is required!");
                    return;
                }
                if (lastName.length() < 3) {
                    myLastname.setError("Last name must be at least 3 characters");
                    return;
                }
                if (addressrgstr.length() < 1) {
                    myLastname.setError("Address must be filled out");
                    return;
                }

                if (lastName.length() >= 50) {
                    myLastname.setError("Maximum characters for last name is 50");
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    myEmail.setError("Email is required!");
                    return;
                }
                if (email.matches(emailPattern)) {

                } else {
                    myEmail.setError("Invalid E-mail format!");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    myPassword.setError("Password is required!");
                    return;
                }
                if(!PASSWORD_PATTERN.matcher(password).matches()){
                    myPassword.setError("Password must contain at least 1 letters, numbers, and special characters!");
                    return;

                }
                if (password.length() < 8) {
                    myPassword.setError("Password is too short!");
                    return;
                }
                if (!password.equals(confirmPassword)) {
                    myConfirmPassword.setError("Password does not match!");
                    return;
                }

                progBar.setVisibility(View.VISIBLE);

                //REGISTER USER TO FIREBASE

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            fAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(Register.this, "User successfully created. Please check your email to verify your account ", Toast.LENGTH_LONG).show();

                                    tourist_iD = fAuth.getCurrentUser().getUid();
                                    DocumentReference documentReference = fStore.collection("users").document(tourist_iD);
                                    Map<String,Object> user = new HashMap<>();
                                    user.put("first_name" ,firstName + " " +lastName);
                                    user.put("email",email);
                                    user.put("user_address",addressrgstr);
                                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Log.d(TAG, "onSuccess: User profile is created for " + tourist_iD);
                                            if (fAuth.getCurrentUser().isEmailVerified()) {
                                                Intent intent = new Intent(Register.this, MainActivity.class);
                                                startActivity(intent);
                                            } else {
                                                Toast.makeText(Register.this, "Please verify your account on your email.", Toast.LENGTH_SHORT).show();
                                                progBar.setVisibility(View.GONE);
                                                Intent intent = new Intent(Register.this, Login.class);
                                                startActivity(intent);
                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                            Log.d(TAG, "onFailure: "+ e.toString());

                                        }
                                    });

                                }
                            });

                        }else {
                            Toast.makeText(Register.this, "ERROR! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progBar.setVisibility(View.GONE);
                            finish();
                        }
                    }
                });
            }
        });

        //PROCEED TO LOGIN PAGE IF ALREADY HAVE ACCOUNT

        myLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),Login.class));

            }
        });

        if (isServiceOK()){
            init();
        }

        if (isServiceOK()){
            init();
        }

    }

    private void init(){

        myAddress.setFocusable(false);
        myAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //Initialize place field list
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS,Place.Field.LAT_LNG,Place.Field.NAME);

                //Create intent
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,
                        fieldList).build(Register.this);
                //Start activity result
                startActivityForResult(intent, 100);
            }
        });

    }
    private static final int ERROR_DIALOG_REQUEST = 9001;

    public boolean isServiceOK(){

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(Register.this);

        if (available == ConnectionResult.SUCCESS){
            // every thing is fine and user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;

        }else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error is occurred but can be resolved
            Log.d(TAG, "isServiceOK: an error occurred but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(Register.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else {
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK){
            //  When success
            // Initialize place
            Place place = Autocomplete.getPlaceFromIntent(data);
            // Set address on EditText
            myAddress.setText(place.getAddress());

        }else if (resultCode == AutocompleteActivity.RESULT_ERROR){
            // When error
            // Initialize status
            Status status = Autocomplete.getStatusFromIntent(data);
            // Display toast
            Toast.makeText(getApplicationContext(),status.getStatusMessage(),Toast.LENGTH_SHORT).show();
        }
    }


}