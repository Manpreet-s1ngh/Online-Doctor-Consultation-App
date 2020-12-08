package com.example.doctorconsultantapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Call_options extends AppCompatActivity {

    CardView userlogin;
    String phone ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_options);
        Intent in = getIntent();
        phone = in.getStringExtra("mobile");
//        Toast.makeText(this, ""+phone, Toast.LENGTH_SHORT).show();
         userlogin = findViewById(R.id.userlogin);

        userlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //whatsapp
                String st = phone.trim();
                if(!st.contains("+91")){
                    st = "+91"+st;
                }

                String text = "Welcome to Doctor Consultations";
                String contact = st.trim(); // use country code with your phone number
                String url = "https://api.whatsapp.com/send?text="+text+"&phone=" + contact;

                try {
                    PackageManager pm = getApplication().getPackageManager();
                    pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);

                } catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(Call_options.this, "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        });
    }

    public void btn_call(View view) {
        //dialer

        Uri u = Uri.parse("tel:" +phone);

        // Create the intent and set the data for the
        // intent as the phone number.
        Intent i = new Intent(Intent.ACTION_DIAL, u);

        try
        {
            // Launch the Phone app's dialer with a phone
            // number to dial a call.
            startActivity(i);
        }
        catch (SecurityException s)
        {
            // show() method display the toast with
            // exception message.
            Toast.makeText(this, "An error occurred", Toast.LENGTH_LONG)
                    .show();
        }

    }
}