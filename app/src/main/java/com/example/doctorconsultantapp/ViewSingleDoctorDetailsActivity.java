package com.example.doctorconsultantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class ViewSingleDoctorDetailsActivity extends AppCompatActivity {
   TextView tv111,tv222,tv333,tv444,tv555,tv666,tv777,tvaddress;
   ImageView imv111;
   Button  bt1,bt2;
    String d_key;
    LinearLayout lay_address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_single_doctor_details);


        tv111=findViewById(R.id.tv111);
        tv222=findViewById(R.id.tv222);
        tv333=findViewById(R.id.tv333);
        tv444= findViewById(R.id.tv444);
        tv555= findViewById(R.id.tv555);
        tv666= findViewById(R.id.tv666);
        tv777= findViewById(R.id.tv777);
        tvaddress= findViewById(R.id.tvaddress);
        lay_address= findViewById(R.id.lay_address);
        imv111= findViewById(R.id.imv111);
        bt1= findViewById(R.id.bt1);
        bt2= findViewById(R.id.bt2);

//        intent.putExtra("Experience",dd.getExperience());
//        intent.putExtra("start",dd.getStartHour());
//        intent.putExtra("end",dd.getEndHour());
//        intent.putExtra("did",dd.getD_key());
//        intent.putExtra("email",dd.getEmail());
//        intent.putExtra("image",dd.getImagepath());
        Intent incomingintent = getIntent();
       d_key = incomingintent.getStringExtra("did");
        String fullName = incomingintent.getStringExtra("FullName");
        String PhoneNo = incomingintent.getStringExtra("PhoneNo");
        String Experience = incomingintent.getStringExtra("Experience");
        String start = incomingintent.getStringExtra("start");
        String end = incomingintent.getStringExtra("end");
        String email = incomingintent.getStringExtra("email");
        String image = incomingintent.getStringExtra("image");
        String BasicFees= incomingintent.getStringExtra("BasicFees");
        String address= incomingintent.getStringExtra("address");

        Picasso.get().load(image).into(imv111);
        tv111.setText(fullName);
        tv222.setText(PhoneNo);
        tv333.setText(fullName);
        tv444.setText(Experience);
        tv555.setText(BasicFees);
        tv666.setText("Start:-"+start+" "+"End :- "+end);
        tv777.setText(email);
        if(address.equals("")){
            lay_address.setVisibility(View.GONE);
        }
        else {
            tvaddress.setText(address);
        }



    }

    public void go(View view)
    {

        Intent intent = new Intent(this,BookAppointmentActivity.class);
        intent.putExtra("d_key",d_key);
        startActivity(intent);

    }

    public void go2(View view)
    {
       Intent intent1 = new Intent(this,ReviewDoctorActivity.class);
       intent1.putExtra("d_key",d_key);


       startActivity(intent1);
    }
}
