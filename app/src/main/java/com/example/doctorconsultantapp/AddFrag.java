package com.example.doctorconsultantapp;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;


public class AddFrag extends Fragment {
     Button Bt111,button6,Bt333;
     ImageView imv1;
     TextView tv1,tv3;
    String filenametobeuploaded;
    String tempfilepath;
    Uri GalleryUri = null;
    Bitmap CameraBitmap = null;
    String type = "";
    EditText etcap;
    StorageReference mainref2;
    DatabaseReference mainref_db;
    String did;


    public AddFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false);
    }

    @Override
    public  void onStart() {


        super.onStart();
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        mainref2 = firebaseStorage.getReference("doctorimages");
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//        mainref_storage = firebaseStorage.getReference("/");
        mainref_db = firebaseDatabase.getReference("DoctorGallery");
        SharedPreferences sharedPreference=getActivity().getSharedPreferences("Doctor",MODE_PRIVATE);
        did = sharedPreference.getString("Doctorid","");


        Bt111= getActivity().findViewById(R.id.Bt111);
        button6= getActivity().findViewById(R.id.button6);
        Bt333= getActivity().findViewById(R.id.Bt333);
        imv1=getActivity().findViewById(R.id.imv1);
        tv1=getActivity().findViewById(R.id.tv1);
        etcap=getActivity().findViewById(R.id.etcaption);
        tv3=getActivity().findViewById(R.id.tv3);

        Bt111.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String caption = etcap.getText().toString();

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

                                    GalleryDetails obj = new GalleryDetails(downloadpath,caption,key);
                                    mainref_db.child(did).child(key).setValue(obj);
                                    getActivity().finish();


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
                    File localfile = new File(getRealPathFromURI(getImageUri(getActivity().getApplicationContext(), CameraBitmap)));

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

                                    GalleryDetails obj = new GalleryDetails(downloadpath,caption,key);
                                    mainref_db.child(did).child(key).setValue(obj);
                                    getActivity().finish();


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
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //gallery
                Intent in = new Intent(Intent.ACTION_PICK);
                in.setType("image/*");
                startActivityForResult(in,20);

                }
        });

        Bt333.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //camera

                Intent in = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(in, 10);

            }
        });



    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent backdata) {
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
        Cursor cursor = getActivity().managedQuery(contentUri, proj, null, null, null);
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




}
