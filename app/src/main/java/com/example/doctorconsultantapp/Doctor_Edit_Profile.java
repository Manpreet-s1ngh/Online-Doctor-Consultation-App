package com.example.doctorconsultantapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class Doctor_Edit_Profile extends AppCompatActivity {
    EditText et1,  et5, et6, et7, et8, et9, et10,etcategory;
    StorageReference mainref2;
    DatabaseReference mainref_db;
    ProgressDialog progressDoalog;

    ImageView imv1;
    Spinner sp1;
    ArrayAdapter<String> ad;
    String filenametobeuploaded;
    String tempfilepath;
    Uri GalleryUri = null;
    Bitmap CameraBitmap = null;
    String type = "";
    String drcategory = "";
    String drname,category,endhr,starthr,phone,basicfees,imagepath,exp , service,key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor__edit__profile);
        progressDoalog = new ProgressDialog(Doctor_Edit_Profile.this);
        progressDoalog.setMax(100);
        progressDoalog.setMessage("Its loading....");
        progressDoalog.setTitle("Updating Profile");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.setCancelable(false);

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        mainref2 = firebaseStorage.getReference("doctorimages");

        et1 = findViewById(R.id.et1);


        et5 = findViewById(R.id.et5);
        et6 = findViewById(R.id.et6);
        et7 = findViewById(R.id.et7);
        et8 = findViewById(R.id.et8);
        et9 = findViewById(R.id.et9);
        et10 = findViewById(R.id.et10);
        etcategory = findViewById(R.id.etcategory);
        etcategory.setEnabled(false);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        Intent in = getIntent();
        drname  = in.getStringExtra("drname");
        category   = in.getStringExtra("category");
        endhr   = in.getStringExtra("endhr");
        starthr   = in.getStringExtra("starthr");
        phone   = in.getStringExtra("phone");
        basicfees   = in.getStringExtra("basicfees");
        imagepath   = in.getStringExtra("imagepath");
        exp   = in.getStringExtra("exp");
        service   = in.getStringExtra("service");
        key   = in.getStringExtra("key");
        drcategory = category;
       et1.setText(drname);
       et5.setText(phone);
       et6.setText(basicfees);
       et7.setText(exp);
       et8.setText(service);

       et9.setText(starthr);
       et10.setText(endhr);
       etcategory.setText(category);
//        mainref_storage = firebaseStorage.getReference("/");
        mainref_db = firebaseDatabase.getReference("DoctorDetails");
        imv1 = findViewById(R.id.userimage);
        Picasso.get().load(imagepath).into(imv1);
    }
    // signup
    public void go3(View view) {

        if(CameraBitmap == null && GalleryUri == null){
            Toast.makeText(this, "Please select itemphoto photo", Toast.LENGTH_SHORT).show();


        }

        else
        {
//
progressDoalog.show();
            if (type.equals("gallery")) {


                // gallery upload
                File localfile = new File(getRealPathFromURI(GalleryUri));

                String local2 = "temp" + (int) (Math.random() * 1000000000) + localfile.getName();
                Uri uri2 = Uri.fromFile(localfile);
                final StorageReference newfile = mainref2.child(local2);
                final UploadTask uploadTask = newfile.putFile(uri2);
                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = newfile.getDownloadUrl();
                        uriTask.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String downloadpath = uri.toString();
//                                String key = mainref_db.push().getKey();


                                mainref_db.child(key).child("imagepath").setValue(downloadpath);
progressDoalog.dismiss();
                                Toast.makeText(Doctor_Edit_Profile.this, "Profile Edited", Toast.LENGTH_SHORT).show();
                                finish();


                            }
                        });
                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });

                    }
                });


            }
            else {
                File localfile = new File(getRealPathFromURI(getImageUri(getApplicationContext(), CameraBitmap)));

                String local2 = "temp" + (int) (Math.random() * 1000000000) + localfile.getName();
                Uri uri2 = Uri.fromFile(localfile);
                final StorageReference newfile = mainref2.child(local2);
                final UploadTask uploadTask = newfile.putFile(uri2);
                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = newfile.getDownloadUrl();
                        uriTask.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String downloadpath = uri.toString();
//                                String key = mainref_db.push().getKey();

                                mainref_db.child(key).child("imagepath").setValue(downloadpath);

progressDoalog.dismiss();
                                Toast.makeText(Doctor_Edit_Profile.this, "Profile Edited", Toast.LENGTH_SHORT).show();
                                finish();

                            }
                        });
                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });

                    }
                });


            }

        }

    }

    //Choose Photo From Camera
    public void go(View view) {
        Intent in = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(in, 10);

    }

    //Choose Photo From Gallery
    public void go2(View view) {
        Intent in = new Intent(Intent.ACTION_PICK);
        in.setType("image/*");
        startActivityForResult(in, 20);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent backdata) {
        super.onActivityResult(requestCode, resultCode, backdata);
        if (requestCode == 10) //from camera
        {
            if (resultCode == RESULT_OK) {
                Bitmap bmp = (Bitmap) (backdata.getExtras().get("data"));

                CameraBitmap = bmp;
                type = "camera";
                try {
                    tempfilepath = Environment.getExternalStorageDirectory() + File.separator + "temp.jpg";
                    FileOutputStream fos = new FileOutputStream(tempfilepath);
                    CameraBitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);

                    filenametobeuploaded = "temp" + (int) (Math.random() * 1000000000) + ".jpg";
                    Toast.makeText(this, "" + filenametobeuploaded, Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                imv1.setImageBitmap(CameraBitmap);

            }

        } else if (requestCode == 20) //from gallery
        {
            if (resultCode == RESULT_OK) {
                GalleryUri = backdata.getData();
                type = "gallery";
                Picasso.get().load(GalleryUri).resize(150, 150).into(imv1);

            }
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public void  go_submit(View view) {
        final String Fullname,  PhoneNo, BasicFees, Experience, Service, StartHour, EndHour;

        Fullname = et1.getText().toString();

        PhoneNo = et5.getText().toString();
        BasicFees = et6.getText().toString();
        Experience = et7.getText().toString();
        Service = et8.getText().toString();
        StartHour = et9.getText().toString();
        EndHour = et10.getText().toString();
        if (et1.getText().toString().trim().length() == 0) {
            et1.setError("FullName is Required");
            et1.requestFocus();
        }  else if (et5.getText().toString().trim().length() == 0) {
            et5.setError("Phone no is required");
            et5.requestFocus();

        }
        else {
            ////
            progressDoalog.show();
            mainref_db.child(key).child("fullName").setValue(Fullname);
            mainref_db.child(key).child("endHour").setValue(EndHour);
            mainref_db.child(key).child("startHour").setValue(StartHour);
            mainref_db.child(key).child("service").setValue(Service);
            mainref_db.child(key).child("phoneNo").setValue(PhoneNo);
            mainref_db.child(key).child("basicFees").setValue(BasicFees);
            mainref_db.child(key).child("experience").setValue(Experience);
            progressDoalog.dismiss();
            Toast.makeText(Doctor_Edit_Profile.this, "Profile Edited", Toast.LENGTH_SHORT).show();
            finish();



        }
    }
}
