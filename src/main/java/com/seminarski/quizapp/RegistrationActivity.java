package com.seminarski.quizapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends Activity {
    EditText emailRegistration, passwordRegistration;
    Button registrationButton;
    ProgressBar progressBar2;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        ConstraintLayout constraintLayout = findViewById(R.id.layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();
        getWindow().setStatusBarColor(ContextCompat.getColor(RegistrationActivity.this,R.color.DPMain));


        emailRegistration = findViewById(R.id.emailRegistracija);
        passwordRegistration = findViewById(R.id.lozinkaRegistracija);
        registrationButton = findViewById(R.id.registrirajSe);
        progressBar2 = findViewById(R.id.progressBar2);
        fAuth = FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainWelcomeActivity.class));
            finish();
        }

        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailRegistration.getText().toString().trim();
                String password = passwordRegistration.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    emailRegistration.setError("Email is required!");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    passwordRegistration.setError("Password is required!");
                    return;
                }
                if(password.length() < 6){
                    passwordRegistration.setError("Password must be more than 6 characters long!");
                    return;
                }

                progressBar2.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegistrationActivity.this, "User successfully created!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainWelcomeActivity.class));
                        } else {
                            Toast.makeText(RegistrationActivity.this, "Failed to create user!", Toast.LENGTH_SHORT).show();
                            progressBar2.setVisibility(View.GONE);
                        }
                        }
                });

            }
        });
    }


}
