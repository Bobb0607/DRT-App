package com.example.drttouristplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    EditText myEmail, myPassword;
    Button myLoginBtn;
    TextView myCreateBtn, forgetText;
    ProgressBar progBar;
    FirebaseAuth fAuth;


    boolean passwordVisible;

    //if user is logged in then redirect to main activity
    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser user = fAuth.getCurrentUser();
        if(user!=null && user.isEmailVerified()){
            Intent i = new Intent(Login.this,MainActivity.class);
            startActivity(i);
            this.finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fAuth = FirebaseAuth.getInstance();

        /*if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(Login.this, MainActivity.class));
            finish();
        } */

        setContentView(R.layout.activity_login);

        myEmail = findViewById(R.id.Email);
        myPassword = findViewById(R.id.password);
        progBar = findViewById(R.id.progressBar);
        fAuth = FirebaseAuth.getInstance();
        myLoginBtn = findViewById(R.id.loginBtn);
        myCreateBtn = findViewById(R.id.createTxt);
        forgetText = findViewById(R.id.forgetPassword);

        myLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = myEmail.getText().toString().trim();
                String password = myPassword.getText().toString().trim();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if (TextUtils.isEmpty(email)) {
                    myEmail.setError("Email is required!");
                    return;
                }
                if (email.matches(emailPattern)) {

                } else {
                    myEmail.setError("Invalid E-mail!");
                }
                if (TextUtils.isEmpty(password)) {
                    myPassword.setError("Password is required!");
                    return;
                }
                if (password.length() < 8) {
                    myPassword.setError("Password is too short!");
                    return;
                }
                progBar.setVisibility(View.VISIBLE);

                //AUTHENTICATE USER

                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            if (fAuth.getCurrentUser().isEmailVerified()) {
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                startActivity(intent);


                            }

                            else {

                                fAuth.getCurrentUser().sendEmailVerification();

                                Toast.makeText(Login.this, "A Verification link is sent to your email. Please verify your account on your email.", Toast.LENGTH_SHORT).show();
                                progBar.setVisibility(View.GONE);

                            }
                        } else {
                            Toast.makeText(Login.this, "Email or password is incorrect", Toast.LENGTH_SHORT).show();
                            progBar.setVisibility(View.GONE);

                        }

                    }


                });


            }
        });


            myPassword.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    final int Right = 2;

                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (event.getRawX() >= myPassword.getRight() - myPassword.getCompoundDrawables()[Right].getBounds().width()) {
                            int selection = myPassword.getSelectionEnd();
                            if (passwordVisible) {
                                //SET DRAWABLE IMAGE HERE
                                myPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_off_24, 0);
                                //HIDE PASSWORD
                                myPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                                passwordVisible = false;
                            } else {
                                //SET DRAWABLE IMAGE HERE
                                myPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_24, 0);
                                //SHOW PASSWORD
                                myPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                                passwordVisible = true;

                            }
                            myPassword.setSelection(selection);
                            return true;
                        }

                    }

                    return false;
                }
            });

            //PROCEED TO LOGIN PAGE IF ALREADY HAVE ACCOUNT

            myCreateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), Register.class));
                }
            });

            forgetText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText resetMail = new EditText(v.getContext());
                    AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                    passwordResetDialog.setTitle("Reset Password?");
                    passwordResetDialog.setMessage("Enter your E-mail address to receive a reset password link.");
                    passwordResetDialog.setView(resetMail);

                    passwordResetDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //EXTRACT EMAIL AND SEND A RESET LINK

                            String email = resetMail.getText().toString();
                            if (TextUtils.isEmpty(email)) {
                                Toast.makeText(Login.this, "Email address is required to reset password", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            fAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(Login.this, "A reset link was sent to your E-mail.", Toast.LENGTH_SHORT).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Login.this, "ERROR! The reset link is not sent." + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });

                    passwordResetDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //CLOSE THE DIALOG
                        }
                    });

                    passwordResetDialog.create().show();
                }
            });



        }

    }
