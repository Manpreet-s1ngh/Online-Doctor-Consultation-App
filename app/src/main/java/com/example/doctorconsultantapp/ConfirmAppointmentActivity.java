package com.example.doctorconsultantapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import java.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class ConfirmAppointmentActivity extends AppCompatActivity implements PaymentResultListener {
     EditText et2;
    EditText et1;
      Button Bt1;
      String p_key,p_email,Start,End,DoctorId,Day;
      String doctorname ="",Doctorphn="",category="";
      String patientName="",Patientno="";

      TextView tv222,tv333,tv444,tv555,tv666,tv777,tv888,tv999,tv1000;
    DatabaseReference doctorref , patientref,BookingRef,booking,bookingdoctor;
    String problem;
    //String Date;
    String mydate;      ///
    int fees=1;
  //  @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_appointment);

        //
        final Calendar myCalender= Calendar.getInstance();
       et1 =(EditText) findViewById(R.id.et1);
        SharedPreferences sharedPreference=getSharedPreferences("Patient",MODE_PRIVATE);
        p_key = sharedPreference.getString("Patient_Key","");
        p_email = sharedPreference.getString("UserName","");

      // getting values from intent
        Intent incomingintent = getIntent();
       Start= incomingintent.getStringExtra("Start");
       End= incomingintent.getStringExtra("End");
        DoctorId=incomingintent.getStringExtra("DoctorId");
        Day=incomingintent.getStringExtra("Day");
        mydate=incomingintent.getStringExtra("date");  /////////////

      et1.setText(mydate);  ////

        /*final DatePickerDialog.OnDateSetListener date= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
              myCalender.set(Calendar.YEAR,year);
              myCalender.set(Calendar.MONTH,month);
              myCalender.set(Calendar.DAY_OF_MONTH,dayOfMonth);
              Label();
            }

            private void Label() {
                String myFormat = "dd-MM-yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                et1.setText(sdf.format(myCalender.getTime()));
            }
        };
        et1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ConfirmAppointmentActivity.this,date,myCalender.get(Calendar.YEAR),myCalender.get(Calendar.MONTH),myCalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });*/


        et2=findViewById(R.id.et2);
        Bt1=findViewById(R.id.Bt1);
        tv222=findViewById(R.id.tv222);
        tv333=findViewById(R.id.tv333);
        tv444=findViewById(R.id.tv444);
        tv555=findViewById(R.id.tv555);
        tv666=findViewById(R.id.tv666);
        tv777=findViewById(R.id.tv777);
        tv888=findViewById(R.id.tv888);
        tv999=findViewById(R.id.tv999);
        tv1000=findViewById(R.id.tv1000);

      //  tv222.setText(doctorname);
       // tv333.setText(Doctorphn);
       // tv444.setText(patientName);
        //tv555.setText(Patientno);
        //tv666.setText(category);
        //tv777.setText(Day);
        //tv999.setText(Start);
        //tv1000.setText(End);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        doctorref = firebaseDatabase.getReference("DoctorDetails");
        patientref = firebaseDatabase.getReference("PatientDetails");
        BookingRef = firebaseDatabase.getReference("BookingDetails");


        doctorref.child(DoctorId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("doctordata",dataSnapshot.toString());
                Doctor_details obj = dataSnapshot.getValue(Doctor_details.class);
                    Doctorphn = obj.getPhoneNo();
                    doctorname = obj.getFullName();
                    category = obj.getCategory();

                    tv222.setText(doctorname);
                   tv333.setText(Doctorphn);
                tv666.setText(category);
                tv777.setText(Day);
                fees=Integer.parseInt(obj.getBasicFees());
                tv888.setText("Fees :- "+obj.getBasicFees()+" Rs.");
                tv999.setText(Start);
                tv1000.setText(End);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        patientref.child(p_key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                PatientDetails obj = dataSnapshot.getValue(PatientDetails.class);
                Patientno = obj.getPhoneno();
                patientName = obj.getFullName();

                tv444.setText(patientName);
                tv555.setText(Patientno);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//

    }
    public void Proceed(View view) {
        problem = et2.getText().toString();
       // Date =et1.getText().toString();
        startPayment();
    }

    public void startPayment() {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final ConfirmAppointmentActivity activity = this;

        final Checkout co = new Checkout();

        try {

            fees=fees*100;
//            basicfees=fees+"";


            JSONObject options = new JSONObject();
            options.put("name", "Doctor Consultation");
            options.put("description", "Appointment Charges");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            options.put("amount",fees);

            JSONObject preFill = new JSONObject();
            preFill.put("email", "");
            preFill.put("contact", "");

            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Log.d("MYMSG",e.getMessage());
            Toast.makeText(activity, "Error: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }



    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "Success  "+s, Toast.LENGTH_SHORT).show();
        String bookingkey = BookingRef.push().getKey();

        Booking obj = new Booking(p_key , patientName, Patientno , Day , Start , End , DoctorId ,
                                  doctorname,category,Doctorphn,problem,"booked", mydate ,bookingkey);
        BookingRef.child(bookingkey).setValue(obj);

        BookedSlots mob=new BookedSlots(mydate,Start,End,mydate+"--"+Start);
       // Log.d("MYMSG","object created Successfully");

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        booking = firebaseDatabase.getReference("BookedSlots");
        bookingdoctor=booking.child(DoctorId);

        String mykey = bookingdoctor.push().getKey();
       // Log.d("MYMSG","Key Saved Successfully");
        bookingdoctor.child(mykey).setValue(mob);
        //Log.d("MYMSG","Data Saved Successfully");

        //Toast.makeText(this, "Slot Booked Successfully", Toast.LENGTH_SHORT).show();
        finish();

    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Fails "+s, Toast.LENGTH_SHORT).show();

    }


}
