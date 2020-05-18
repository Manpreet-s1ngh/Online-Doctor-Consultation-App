package com.example.doctorconsultantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageActivity2 extends AppCompatActivity {
    ImageView imv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image2);
        imv1 = findViewById(R.id.imv1);
        Intent in = getIntent();
        String pipath = in.getStringExtra("image");
        Picasso.get().load(pipath).into(imv1);
    }
}
