package com.example.doctorconsultantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PatientForgetPasswordActivity extends AppCompatActivity {

    EditText editText;
    String email;
    DatabaseReference mainref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_forget_password);
        editText = findViewById(R.id.etemail);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        mainref = firebaseDatabase.getReference("PatientDetails");

    }

    public void changepss(View view) {
        email = editText.getText().toString();
        if (email.equals("")) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
        } else {
            mainref.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot sin : dataSnapshot.getChildren()) {
                            PatientDetails obj = sin.getValue(PatientDetails.class);
                            String no = obj.getPhoneno();
                            String password = obj.getPassword();
                            String url = "http://server1.vmm.education/VMMCloudMessaging/AWS_SMS_Sender" + "?username=yoginder_doctor_app&password=HRYXJO1U&phone_numbers=" + no + "&message=Your%20Old%20Password%20is%20" + password + "Please%20Dont%20Share%20with%20Anyone";
//
                            Log.d("hello", url);
                            StringRequest request = new StringRequest(StringRequest.Method.GET, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.d("vmm", response.trim());
                                    Toast.makeText(PatientForgetPasswordActivity.this, "Message Sent to Your Registered Mobile number.", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d("vmm", error.toString());
                                    Toast.makeText(getApplicationContext(), "Error" + error, Toast.LENGTH_SHORT).show();
                                }
                            });
                            Volley.newRequestQueue(PatientForgetPasswordActivity.this).add(request);

                        }
                    } else {
                        Toast.makeText(PatientForgetPasswordActivity.this, "No record found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}