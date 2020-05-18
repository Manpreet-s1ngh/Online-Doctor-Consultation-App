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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
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
import java.lang.reflect.Array;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;


public class UploadFrag extends Fragment {

    EditText et1,et2;
    RadioButton rb1,rb2;
    ImageView imv1;
    Button bt1,bt2,bt3;
    String type ="";
    String type1 ="";
    String pkey,did;
    DatabaseReference mainref;
    String filenametobeuploaded;
    String tempfilepath;
    Uri GalleryUri = null;
    Bitmap CameraBitmap = null;
    StorageReference mainref2;

    public UploadFrag(String pkey) {
        // Required empty public constructor
        this.pkey = pkey;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upload, container, false);
    }

    public void onStart() {

        super.onStart();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        mainref = firebaseDatabase.getReference("PrescriptionDetails");
        et1 = getActivity().findViewById(R.id.et1);
        et2 = getActivity().findViewById(R.id.et2);
        rb1 = getActivity().findViewById(R.id.rb1);
        rb2 = getActivity().findViewById(R.id.rb2);
        bt1 = getActivity().findViewById(R.id.bt1);
        bt2 = getActivity().findViewById(R.id.bt2);
        bt3 = getActivity().findViewById(R.id.bt3);
        imv1 = getActivity().findViewById(R.id.imv1);
        SharedPreferences sharedPreference = getActivity().getSharedPreferences("Doctor", MODE_PRIVATE);
        did = sharedPreference.getString("Doctorid", "");
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        mainref2 = firebaseStorage.getReference("prescriptionimages");



        rb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "text";
            }
        });
        rb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                type = "Image";
                imv1.setVisibility(View.VISIBLE);


            }
        });

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String titile = et1.getText().toString();
                final String detais = et2.getText().toString();
                if (titile.equals("") || detais.equals("")) {

                    Toast.makeText(getActivity(), "All Fields Are Required", Toast.LENGTH_SHORT).show();

                } else {
                    if (type.equals("text")) {
                        String prescription_Key = mainref.push().getKey();
                        PrescriptionDetails obj = new PrescriptionDetails(pkey, did, titile, detais, type, "No Image", Doctor_Global_Class.dname, Doctor_Global_Class.category, Doctor_Global_Class.dphone, prescription_Key);
                        mainref.child(prescription_Key).setValue(obj);
                        Toast.makeText(getActivity(), "Prescription Added", Toast.LENGTH_SHORT).show();

                    } else if (type.equals("Image"))  {
                        Toast.makeText(getActivity(), "Reach"+type, Toast.LENGTH_SHORT).show();
                        if (type1.equals("gallery")) {

                            Log.d("vmm",type);
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
                                            String key = mainref.push().getKey();

                                            PrescriptionDetails obj = new PrescriptionDetails(pkey, did, titile, detais, type, downloadpath, Doctor_Global_Class.dname, Doctor_Global_Class.category, Doctor_Global_Class.dphone, key);
                                            mainref.child(key).setValue(obj);
                                            Toast.makeText(getActivity(), "Prescription Added", Toast.LENGTH_SHORT).show();



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
                        else  if (type1.equals("camera")) {
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
                                            String key = mainref.push().getKey();
                                            PrescriptionDetails obj = new PrescriptionDetails(pkey, did, titile, detais, type, downloadpath, Doctor_Global_Class.dname, Doctor_Global_Class.category, Doctor_Global_Class.dphone, key);
                                            mainref.child(key).setValue(obj);
                                            Toast.makeText(getActivity(), "Prescription Added", Toast.LENGTH_SHORT).show();



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
            }
        });


        //For Camera
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(in, 10);
            }
        });

                     //For Gallery
                     bt3.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {
                             Intent in = new Intent(Intent.ACTION_PICK);
                             in.setType("image/*");
                             startActivityForResult(in, 20);
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
                type1 = "camera";
                try {
                    tempfilepath = Environment.getExternalStorageDirectory() + File.separator + "temp.jpg";
                    FileOutputStream fos = new FileOutputStream(tempfilepath);
                    CameraBitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);

                    filenametobeuploaded = "temp" + (int) (Math.random() * 1000000000) + ".jpg";
                    Toast.makeText(getActivity(), "" + filenametobeuploaded, Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                imv1.setImageBitmap(CameraBitmap);

            }

        } else if (requestCode == 20) //from gallery
        {
            if (resultCode == RESULT_OK) {
                GalleryUri = backdata.getData();
                type1 = "gallery";
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
