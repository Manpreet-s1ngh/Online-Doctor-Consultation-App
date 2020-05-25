package com.example.doctorconsultantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.AbstractSet;
import java.util.ArrayList;

public class DoctorLoginActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mainref1;
    EditText et1;
    EditText et2;
    Button Bt1,Bt3;
    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_login);
        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        Bt1 = findViewById(R.id.Bt1);
        Bt3 = findViewById(R.id.Bt3);

        firebaseDatabase = FirebaseDatabase.getInstance();

        mainref1 = firebaseDatabase.getReference("DoctorDetails");

        Bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Doctor_Forget_Password.class);
                startActivity(intent);
            }
        });

    }

    public void go(View view) {
        Intent intent = new Intent(this, DoctorSignupActivity.class);
        startActivity(intent);
    }


    public void go2(View view) {
        final String Doctorid = et1.getText().toString();
        final String pass = et2.getText().toString();
        if (et1.getText().toString().trim().length() == 0) {
            et1.setError("UserName is Required");
            et1.requestFocus();
        } else if (et2.getText().toString().trim().length() == 0) {
            et2.setError("Password is Required");
            et2.requestFocus();
        } else {


            mainref1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.d("mymsg",dataSnapshot.toString());
                    if(dataSnapshot.exists()){
                        for (DataSnapshot Doctor : dataSnapshot.getChildren()) {
                            Doctor_details obj = Doctor.getValue(Doctor_details.class);
                            Log.d("mymsg",Doctorid+"..."+obj.getEmail()+"...."+pass+"..."+obj.getPassword());
                            if (Doctorid.equals(obj.getEmail()) && pass.equals(obj.getPassword())) {
                                if(obj.getStatus().equals("approve")){
                                SharedPreferences sharedPreferences = getSharedPreferences("Doctor", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("Doctorid", obj.getD_key());
                                editor.putString("DoctorEmail", obj.getEmail());
                                editor.commit();
                                flag = 1;
                                break;
                                }
                                else {
                                    flag =4 ;
                                    break;

                                }

                            }


                        }

                    }
                    else {
                        Toast.makeText(DoctorLoginActivity.this, "DB Not Exists", Toast.LENGTH_SHORT).show();
                    }
                                       if (flag == 1) {

                        Intent intent = new Intent(getApplicationContext(), DoctorHomeActivity.class);
                        startActivity(intent);
                        Toast.makeText(DoctorLoginActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();

                    } else  if (flag == 4){
                        Toast.makeText(DoctorLoginActivity.this, "Your Status is Pending plz contact to admin", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(DoctorLoginActivity.this, "Login unsuccessfull", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

}


