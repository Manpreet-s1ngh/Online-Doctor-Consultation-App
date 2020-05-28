package com.example.doctorconsultantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PatientLoginActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mainref;
    EditText et1,et2;
    Button Bt1;
    int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_login);

        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        Bt1 = findViewById(R.id.Bt1);

        firebaseDatabase = FirebaseDatabase.getInstance();

        mainref = firebaseDatabase.getReference("PatientDetails");
        Bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = et1.getText().toString();
                final String pass = et2.getText().toString();
                if (et1.getText().toString().trim().length() == 0) {
                    et1.setError("UserName is Required");
                    et1.requestFocus();
                } else if (et2.getText().toString().trim().length() == 0) {
                    et2.setError("Password is Required");
                    et2.requestFocus();
                } else {
                    mainref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot Admin : dataSnapshot.getChildren()) {
                                PatientDetails obj = Admin.getValue(PatientDetails.class);
                                if (username.equals(obj.Email) && pass.equals(obj.getPassword())) {
                                    SharedPreferences sharedPreferences = getSharedPreferences("Patient", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("UserName", obj.getEmail());
                                    editor.putString("Patient_Key", obj.getPatientKey());
                                    editor.commit();
                                    flag = 1;
                                    break;

                                }


                            }
                            if (flag == 1) {

                                Intent intent = new Intent(getApplicationContext(), PatientHomeActivity.class);
                                startActivity(intent);
                                Toast.makeText(PatientLoginActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(PatientLoginActivity.this, "Login unsuccessfull", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }


                    });

                }
            }
        });
    }


    public void go(View view)
    {
        Intent intent = new Intent(this,PatientSignupActivity.class);
        startActivity(intent);
    }

    public void go2(View view)
    {
        Intent intent = new Intent(this,PatientForgetPasswordActivity.class);
        startActivity(intent);
    }
}


