package com.example.doctorconsultantapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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

public class PatientSignupActivity extends AppCompatActivity {
    TextView tv1,tv2;
    Button Bt1,Bt2,Bt3;
    EditText et1,et2,et3,et4,et5;
    ImageView imv1;
    StorageReference mainref2;
    DatabaseReference mainref_db;
    String filenametobeuploaded;
    String tempfilepath;
    Uri GalleryUri = null;
    Bitmap CameraBitmap = null;
    String type = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_signup);
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        mainref2 = firebaseStorage.getReference("Patient images");
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        mainref_db = firebaseDatabase.getReference("PatientDetails");
        tv1= findViewById(R.id.tv1);
        tv2=findViewById(R.id.tv2);
        et1=findViewById(R.id.et1);
        et2=findViewById(R.id.et2);
        et3=findViewById(R.id.et3);
        et4=findViewById(R.id.et4);
        et5=findViewById(R.id.et5);
        Bt1=findViewById(R.id.Bt1);
        Bt2=findViewById(R.id.Bt2);
        Bt3=findViewById(R.id.Bt3);
        imv1=findViewById(R.id.imv1);
    }
     //For Camera
    public void go(View view)
    {
        Intent in = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(in, 10);

    }

     //for Gallery
    public void go2(View view)
    {
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


    //signup
    public void go3(View view) {
        final String Fullname, Email, Password, ConfirmPassword, PhoneNo;
        Fullname = et1.getText().toString();
        Email = et2.getText().toString();
        Password = et3.getText().toString();
        ConfirmPassword = et4.getText().toString();
        PhoneNo = et5.getText().toString();
        if (et1.getText().toString().trim().length() == 0) {
            et1.setError("FullName is Required");
            et1.requestFocus();
        } else if (et2.getText().toString().trim().length() == 0) {
            et2.setError("Email is Required");
            et2.requestFocus();
        }
        if (et3.getText().toString().trim().length() == 0) {
            et3.setError("Password is Required");
            et3.requestFocus();
        } else if (et5.getText().toString().trim().length() == 0) {
            et5.setError("Phone no is required");
            et5.requestFocus();

        }
        else if(CameraBitmap == null || GalleryUri == null){
            String key = mainref_db.push().getKey();
String downloadpath ="https://firebasestorage.googleapis.com/v0/b/doctor-app-firebase.appspot.com/o/boy.png?alt=media&token=0751d5fc-5f14-4f49-be50-217f8830b44c";
            PatientDetails obj = new PatientDetails(Fullname, Email, Password, PhoneNo, downloadpath, key);
            mainref_db.child(key).setValue(obj);
            Toast.makeText(this, "Signup Success", Toast.LENGTH_SHORT).show();
            finish();
        }
        else {
//
//
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
                                String key = mainref_db.push().getKey();

                                PatientDetails obj = new PatientDetails(Fullname, Email, Password, PhoneNo, downloadpath, key);
                                mainref_db.child(key).setValue(obj);
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


            } else {
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
                                String key = mainref_db.push().getKey();

                                PatientDetails obj = new PatientDetails(Fullname, Email, Password, PhoneNo, downloadpath, key);
                                mainref_db.child(key).setValue(obj);
                                finish();


                            }
                        });

                    }
                });


            }

        }

    }
}

