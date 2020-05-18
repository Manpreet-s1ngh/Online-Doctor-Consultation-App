package com.example.doctorconsultantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class Image_Activity extends AppCompatActivity {
ImageView imvPic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_activity);
        imvPic = findViewById(R.id.imvPic);
        Intent in = getIntent();
        String pipath = in.getStringExtra("image");
        Picasso.get().load(pipath).into(imvPic);
    }
}
