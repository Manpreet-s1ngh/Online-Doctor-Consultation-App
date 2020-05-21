package com.example.doctorconsultantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
<<<<<<< HEAD
import android.os.Bundle;
import android.view.View;
=======
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
>>>>>>> f05ff515b4b99082dfdffdfbdf287ed1aa3a39f7

public class AdminHome extends AppCompatActivity {
Button bt1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        bt1 = findViewById(R.id.bt1);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreference=getSharedPreferences("Admin",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreference.edit();
                editor.remove("UserName");
                editor.apply();
                finish();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);

            }
        });
    }

    public void go(View view)
    {
        Intent intent = new Intent(this,AdminChangePasswordActivity.class);
        startActivity(intent);
    }
}
