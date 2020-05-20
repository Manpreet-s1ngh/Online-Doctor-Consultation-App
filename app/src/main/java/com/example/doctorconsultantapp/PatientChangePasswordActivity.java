package com.example.doctorconsultantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class PatientChangePasswordActivity extends AppCompatActivity {
    EditText et1,et2,et3,et4;
    Button Bt1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_change_password);

        et1= findViewById(R.id.et1);
        et2= findViewById(R.id.et2);
        et3 = findViewById(R.id.et3);
        et4 = findViewById(R.id.et4);
        Bt1 = findViewById(R.id.Bt1);

    }
}
