package com.example.drttouristplanner;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class PersonalInfo extends AppCompatActivity {

    TextView fullName, email, contactInfo, address;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String tourist_ID;
    Button edit_profile_btn, cancelBtn;
    CircleImageView profileImage;
    ImageButton changeProfileImage;
    StorageReference storageReference;


    ActivityResultLauncher<String> mTakePhoto;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        edit_profile_btn = findViewById(R.id.edit_profile_btn);
        changeProfileImage =findViewById(R.id.add_profilePicButton);
        contactInfo = findViewById(R.id.profile_contact);
        address = findViewById(R.id.profile_address);
        cancelBtn = findViewById(R.id.cancelBtn3);

        fullName = findViewById(R.id.profile_name);
        email = findViewById(R.id.profile_email);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        StorageReference profileRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImage);
            }
        });

        tourist_ID = fAuth.getCurrentUser().getUid();

        profileImage = findViewById(R.id.profile_image);

        mTakePhoto = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {

                        profileImage.setImageURI(result);

                    }
                }
        );

        DocumentReference documentReference = fStore.collection("users").document(tourist_ID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if (documentSnapshot.exists()) {
                    fullName.setText(documentSnapshot.getString("first_name"));
                    email.setText(documentSnapshot.getString("email"));
                    contactInfo.setText(documentSnapshot.getString("contact_number"));
                    address.setText(documentSnapshot.getString("user_address"));
                } else {
                    Log.d("TAG", "onEvent: Document do not exist");
                }
            }
        });


        edit_profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),edit_profile.class);
                intent.putExtra("first_name" ,fullName.getText().toString());
                intent.putExtra("email" ,email.getText().toString());
                intent.putExtra("contact_number" ,contactInfo.getText().toString());
                intent.putExtra("user_address", address.getText().toString());



                startActivity(intent);

            }
        });

        changeProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*CODE TO OPEN THE GALLERY
                Intent  openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent,1000);*/
                mTakePhoto.launch("image/*");



            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

try {


    Uri imageUri = data.getData();
    profileImage.setImageURI(imageUri);

    uploadImagetoFirebase(imageUri);
}

catch (Exception e) {
    e.printStackTrace();
}


    }





    private void uploadImagetoFirebase(Uri imageUri) {
        //UPLOAD IMAGE TO FIREBASE STORAGE
        StorageReference fileRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profileImage);

                    }
                });

                Toast.makeText(PersonalInfo.this, "Image uploaded.", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PersonalInfo.this, "Failed.Y", Toast.LENGTH_SHORT).show();
            }
        });

    }
}