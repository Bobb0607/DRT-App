package com.example.drttouristplanner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.content.IntentSender;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class edit_profile extends AppCompatActivity {


    private static final String TAG = "edit_profile";
    private static final int ERROR_DIALOG_REQUEST = 9001;

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
    public static final String TAG2 = "TAG";

    FirebaseAuth fAuth;
    FirebaseUser user;
    FirebaseFirestore fStore;
    String Tourist_ID;
    TextView profileFullnameEdit, profileAddressEdit, profileContactEdit, resetPasswordLocal, resetEmailLocal;
    Button cancelBtn, openmapBtn;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Intent data = getIntent();
        String firstName = data.getStringExtra("first_name");
        String contact = data.getStringExtra("contact_number");
        //String address = data.getStringExtra("email");
        String emailAddress = data.getStringExtra("email");

        profileFullnameEdit = findViewById(R.id.profile_name_edit);
        profileContactEdit = findViewById(R.id.profile_contact_edit);
        resetEmailLocal = findViewById(R.id.resetEmailLocal);
        profileAddressEdit = findViewById(R.id.profile_address_edit);
        cancelBtn = findViewById(R.id.cancel_btn2);



        Log.d(TAG2, "onCreate: " + firstName + " " + emailAddress);


        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        fStore = FirebaseFirestore.getInstance();

        resetPasswordLocal = findViewById(R.id.resetPasswordLocal);
        resetEmailLocal = findViewById(R.id.resetEmailLocal);

        profileFullnameEdit = findViewById(R.id.profile_name_edit);
        profileAddressEdit = findViewById(R.id.profile_address_edit);
        profileContactEdit = findViewById(R.id.profile_contact_edit);
        resetEmailLocal = findViewById(R.id.resetEmailLocal);

        resetEmailLocal.setText(emailAddress);
        profileFullnameEdit.setText(firstName);

       /* LocationRequest locationRequest;

        openmapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PermissionListener permissionListener = new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                    }
                };


                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                finish();
            }
        }); */


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),PersonalInfo.class));
                finish();
            }
        });

        resetEmailLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText resetEmail = new EditText(view.getContext());
                AlertDialog.Builder emailResetDialog = new AlertDialog.Builder(view.getContext());
                emailResetDialog.setTitle("Reset Email?");
                emailResetDialog.setMessage("Note: You need to verify your new email address. ");
                emailResetDialog.setView(resetEmail);

                emailResetDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String newEmail = resetEmail.getText().toString();
                        if (TextUtils.isEmpty(newEmail)) {
                            Toast.makeText(edit_profile.this, "A new email is required!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(!PASSWORD_PATTERN.matcher(newEmail).matches()){
                            Toast.makeText(edit_profile.this, "email is too weak!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        user.updateEmail(newEmail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                                DocumentReference docRef = fStore.collection("users").document(user.getUid());
                                Map<String, Object> edited = new HashMap<>();
                                edited.put("email", resetEmail.getText().toString());
                                docRef.update(edited);

                                Toast.makeText(edit_profile.this, "Email reset successfully changed", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),edit_profile.class));
                                finish();
                                Toast.makeText(edit_profile.this, "Please verify your new email address", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(edit_profile.this,edit_profile.class);

                                startActivity(intent);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(edit_profile.this, "email reset failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                emailResetDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                    //CLOSE

                });

                emailResetDialog.create().show();

            }
        });

        //creates a document snapshot immediately with the current contents of the single document
        Tourist_ID = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("users").document(Tourist_ID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if (documentSnapshot.exists()) {
                    profileFullnameEdit.setText(documentSnapshot.getString("first_name"));
                    resetEmailLocal.setText(documentSnapshot.getString("email"));
                    profileContactEdit.setText(documentSnapshot.getString("contact_number"));
                    profileAddressEdit.setText(documentSnapshot.getString("user_address"));
                } else {
                    Log.d("TAG", "onEvent: Document do not exist");
                }
            }
        });

        //EDIT TEXTVIEW

        profileFullnameEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(edit_profile.this, edit_name_layout.class);
                startActivity(intent);
            }
        });

        profileContactEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(edit_profile.this, edit_contact_layout.class);
                startActivity(intent);
            }
        });


        resetPasswordLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                EditText resetPassword = new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password?");
                passwordResetDialog.setMessage("Enter a new password: ");
                passwordResetDialog.setView(resetPassword);

                passwordResetDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override

                    public void onClick(DialogInterface dialog, int which) {

                        String newPassword = resetPassword.getText().toString();
                        if (TextUtils.isEmpty(newPassword)) {
                            Toast.makeText(edit_profile.this, "A new password is required!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(!PASSWORD_PATTERN.matcher(newPassword).matches()){
                            Toast.makeText(edit_profile.this, "Password is too weak!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        user.updatePassword(newPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                                Toast.makeText(edit_profile.this, "Password reset successfully", Toast.LENGTH_SHORT).show();

                            }

                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(edit_profile.this, "Password reset failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                passwordResetDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                    //CLOSE

                });

                passwordResetDialog.create().show();

                    }
                });

        if (isServiceOK()){
            init();
        }



            }

            private void init(){

                profileAddressEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        //Intent intent = new Intent(edit_profile.this, address_layout.class);
                        Intent intent = new Intent(edit_profile.this, AutocompletePlace.class);
                        startActivity(intent);
                    }
                });

            }

            public boolean isServiceOK(){

                int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(edit_profile.this);

                if (available == ConnectionResult.SUCCESS){
                    // every thing is fine and user can make map requests
                    Log.d(TAG, "isServicesOK: Google Play Services is working");
                    return true;

                }else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)){
                    //an error is occurred but can be resolved
                    Log.d(TAG, "isServiceOK: an error occurred but we can fix it");
                    Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(edit_profile.this, available, ERROR_DIALOG_REQUEST);
                    dialog.show();
                }else {
                    Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

        }