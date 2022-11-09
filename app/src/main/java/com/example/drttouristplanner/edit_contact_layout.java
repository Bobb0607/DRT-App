package com.example.drttouristplanner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class edit_contact_layout extends AppCompatActivity {

    EditText contactInfo;
    Button savebtn, cancelBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fstrore;
    FirebaseUser user;
    String tourist_iD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact_layout);

        fAuth = FirebaseAuth.getInstance();
        fstrore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();

        contactInfo = findViewById(R.id.edit_contact_number);
        savebtn = findViewById(R.id.save_contact_btn);
        cancelBtn = findViewById(R.id.cancel_btn4);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),PersonalInfo.class));
                finish();
            }
        });

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String myContacts = contactInfo.getText().toString().trim();

                if (TextUtils.isEmpty(myContacts)) {
                    contactInfo.setError("Your contact number is required!");
                    return;
                } else
                {
                    //UPDATE FULL NAME
                    tourist_iD = fAuth.getCurrentUser().getUid();
                    DocumentReference docRef = fstrore.collection("users").document(user.getUid());
                    Map<String, Object> edited = new HashMap<>();
                    edited.put("contact_number", myContacts);
                    docRef.update(edited);

                    Toast.makeText(edit_contact_layout.this, "Contact number successfully changed", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),edit_profile.class));
                    finish();



                }



            }
        });


    }
}