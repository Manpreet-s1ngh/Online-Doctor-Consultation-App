package com.example.doctorconsultantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);

        //autologin code
        SharedPreferences sharedPreference=getSharedPreferences("Doctor",MODE_PRIVATE);
        String doctor = sharedPreference.getString("Doctorid","");
        if(doctor.equals("")){

        }
        else {
            Intent intent = new Intent(this,DoctorHomeActivity.class);
            startActivity(intent);
            finish();
        }

        //admin
        SharedPreferences sharedPreference1=getSharedPreferences("Admin",MODE_PRIVATE);
        String admin = sharedPreference1.getString("UserName","");
        if(admin.equals("")){

        }
        else {
            Intent intent = new Intent(this,AdminHome.class);
            startActivity(intent);
            finish();
        }
        //patient
        SharedPreferences sharedPreference3=getSharedPreferences("Patient",MODE_PRIVATE);
        String patient = sharedPreference3.getString("Patient_Key","");
//        Toast.makeText(this, ""+patient, Toast.LENGTH_SHORT).show();
        if(patient.equals("")){

        }
        else {
            Intent intent = new Intent(this,PatientHomeActivity.class);
            startActivity(intent);
            finish();
        }


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
