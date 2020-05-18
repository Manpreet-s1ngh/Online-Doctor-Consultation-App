package com.example.doctorconsultantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
    }

    public void go(View view)
    {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

    public void go2(View view)
    {
        Intent intent = new Intent(this,DoctorLoginActivity.class);
        startActivity(intent);
    }

    public void go3(View view)
    {
        Intent intent = new Intent(this,PatientLoginActivity.class);
        startActivity(intent);
    }
}
