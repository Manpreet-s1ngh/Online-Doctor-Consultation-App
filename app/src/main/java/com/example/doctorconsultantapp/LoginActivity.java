package com.example.doctorconsultantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
  FirebaseDatabase firebaseDatabase;
  DatabaseReference mainref;
    EditText  et1, et2;
   Button Bt1;
   int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);
        et1 = findViewById(R.id.staffname);
        et2 = findViewById(R.id.staffpassword);
        Bt1 = findViewById(R.id.Bt1);
        firebaseDatabase = FirebaseDatabase.getInstance();

        mainref = firebaseDatabase.getReference("Admin");


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
                }
                else
                    {
                    mainref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot Admin : dataSnapshot.getChildren()) {
                                Login obj = Admin.getValue(Login.class);
                                if (username.equals(obj.UserName) && pass.equals(obj.getPassword())) {
                                    SharedPreferences sharedPreferences = getSharedPreferences("Admin", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("UserName", obj.getUserName());
                                    editor.apply();
                                    flag = 1;
                                    break;

                                }


                            }
                            if (flag == 1) {

                                Intent intent = new Intent(getApplicationContext(), AdminHome.class);
                                startActivity(intent);
                                Toast.makeText(LoginActivity.this,"Login Successfull",Toast.LENGTH_SHORT).show();

                            }
                            else {
                                Toast.makeText(LoginActivity.this, "Login unsuccessfull", Toast.LENGTH_SHORT).show();
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


}
