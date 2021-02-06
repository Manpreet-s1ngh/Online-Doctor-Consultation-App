package com.example.doctorconsultantapp;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class LeaveSet extends AppCompatActivity {


 TextView showdate;
 String selecteddate;
 Button getLeave;
 DatabaseReference mainref;

 String did;
 String idwithdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_set);
        // views
        showdate=findViewById(R.id.showDate);
        getLeave=findViewById(R.id.getleave);
        //
        Intent intent=getIntent();
        did=intent.getStringExtra("did");
        Toast.makeText(this, "yess : "+did, Toast.LENGTH_SHORT).show();

        //
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        mainref = firebaseDatabase.getReference("LeaveDetail");
        // code for date
        final Calendar myCalender= Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalender.set(android.icu.util.Calendar.YEAR,year);
                myCalender.set(android.icu.util.Calendar.MONTH,month);
                myCalender.set(android.icu.util.Calendar.DAY_OF_MONTH,dayOfMonth);

                Label();
            }

            @SuppressLint("ResourceAsColor")
            private void Label() {


                String myFormat = "dd-MM-yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                //int dayOfWeek = myCalender.get(Calendar.DAY_OF_WEEK);
              //  day=mylist.get(dayOfWeek); // getting the day with date;
              //  daySelected.setText(day);
                //Storing the date in variable
                selecteddate=sdf.format(myCalender.getTime());

                idwithdate=did+"-"+selecteddate;
                showdate.setText(selecteddate);

               /* mainref.orderByChild("day").equalTo(day).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Log.d("datasnap",dataSnapshot.toString());

                        al1.clear();
                        for (DataSnapshot sin :  dataSnapshot.getChildren())
                        {
                            slotdetails bj = sin.getValue(slotdetails.class);
                            al1.add(bj);
                        }

                        Toast.makeText(BookAppointmentActivity.this, "Yeh works --", Toast.LENGTH_SHORT).show();
                        myad.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });*/

            }

        };
        showdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
                DatePickerDialog datePickerDialog=new DatePickerDialog(LeaveSet.this,
                        date,myCalender.get(android.icu.util.Calendar.YEAR),
                        myCalender.get(android.icu.util.Calendar.MONTH),
                        myCalender.get(android.icu.util.Calendar.DAY_OF_MONTH));

                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
                datePickerDialog.show();

            }
        });
       // when click on button
        getLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String key = mainref.push().getKey();
                //Doctor_details obj = new Doctor_details(Fullname, Email, Password, drcategory, PhoneNo, BasicFees, Experience, Service, StartHour, EndHour, downloadpath, key,"pending",address);
                leaveDetails object=new leaveDetails(did,selecteddate,idwithdate);
                mainref.child(key).setValue(object);
                showdate.setText("Leave Registered Successfully");
                getLeave.setEnabled(false);


                Toast.makeText(LeaveSet.this, "Your Leave Registered Successfully", Toast.LENGTH_SHORT).show();
            }
        });

    }


}