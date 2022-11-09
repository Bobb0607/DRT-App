package com.example.drttouristplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class edit_name_layout extends AppCompatActivity {
    EditText firstName, lastName;
    Button savebtn, cancelBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fstrore;
    FirebaseUser user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_name_layout);

        fAuth = FirebaseAuth.getInstance();
        fstrore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();


        firstName = findViewById(R.id.first_name);
        lastName = findViewById(R.id.last_name);
        savebtn = findViewById(R.id.save_fullname_btn);
        cancelBtn = findViewById(R.id.cancel_btn);

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

                String myfirstName = firstName.getText().toString().trim();
                String mylastName = lastName.getText().toString().trim();
                if (TextUtils.isEmpty(myfirstName)) {
                    firstName.setError("Your first name is required!");
                    return;
                }
                if (TextUtils.isEmpty(mylastName)) {
                    lastName.setError("Your last name is required!");
                    return;
                } else {
                    //UPDATE FULL NAME
                    DocumentReference docRef = fstrore.collection("users").document(user.getUid());
                    Map<String, Object> edited = new HashMap<>();
                    edited.put("first_name", firstName.getText().toString() + " " + lastName.getText().toString());
                    docRef.update(edited);

                    Toast.makeText(edit_name_layout.this, "Full name successfully changed", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),edit_profile.class));
                    finish();
                }
            }


        });




    }
}