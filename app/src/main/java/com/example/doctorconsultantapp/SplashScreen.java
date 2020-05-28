package com.example.doctorconsultantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {
    Intent intent;
    TextView tv1,tv2;
    ImageView imv1;
    Animation top,buttom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        tv1 =findViewById(R.id.tv1);
        tv2 =findViewById(R.id.tv2);
        imv1=findViewById(R.id.imv1);

        top = AnimationUtils.loadAnimation(this,R.anim.top);
        buttom = AnimationUtils.loadAnimation(this,R.anim.buttom);

        tv1.setAnimation(top);
        imv1.setAnimation(buttom);
        tv2.setAnimation(buttom);


        //main
        intent = new Intent(this,MainActivity.class);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        },3000);

    }
}
