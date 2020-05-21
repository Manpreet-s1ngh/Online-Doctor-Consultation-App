package com.example.doctorconsultantapp;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;


public class PostReview extends Fragment {

    TextView tv1,tv2,tvRateCount;
    Button Bt1;
    ImageView imv1;
    RatingBar rt1;
    EditText et1;
    private float ratedValue;
    DatabaseReference maianref;
String dkey;
    public PostReview(String dkey) {
        // Required empty public constructor
        this.dkey = dkey;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post_review, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences sharedPreference= getActivity().getSharedPreferences("Patient",MODE_PRIVATE);
        final String  pkey = sharedPreference.getString("Patient_Key","");


        tv1=getActivity().findViewById(R.id.tv1);
        tv2=getActivity().findViewById(R.id.tv2);
        tvRateCount=getActivity().findViewById(R.id.tv3);
        Bt1=getActivity().findViewById(R.id.Bt1);
        imv1=getActivity().findViewById(R.id.imv1);
        rt1= getActivity().findViewById(R.id.rt1);
        et1= getActivity().findViewById(R.id.et1);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        maianref = firebaseDatabase.getReference("RatingDetails");

        rt1.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override

            public void onRatingChanged(RatingBar ratingBar, float rating,

                                        boolean fromUser) {

                ratedValue = ratingBar.getRating();

                tvRateCount.setText("Your Rating : "

                        + ratedValue + "/5.");

                Toast.makeText(getActivity(), rating+"", Toast.LENGTH_SHORT).show();
                if(ratedValue<1){

                    tvRateCount.setText("ohh ho...");

                }else if(ratedValue<2){

                    tvRateCount.setText("Ok.");

                }else if(ratedValue<3){

                    tvRateCount.setText("Not bad.");

                }else if(ratedValue<4){

                    tvRateCount.setText("Nice");

                }else if(ratedValue<5){

                    tvRateCount.setText("Very Nice");

                }else if(ratedValue==5){

                    tvRateCount.setText("Thank you..!!!");

                }

            }

        });
        Bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment =et1.getText().toString();
                if(comment.equals("")){
                    Toast.makeText(getActivity(), "All fields are required", Toast.LENGTH_SHORT).show();
                }
                else {
                    String pushkey = maianref.push().getKey();
                    Date d = new Date();
                    SimpleDateFormat df = new SimpleDateFormat("dd-Mm-yyyy");
                    String date = df.format(d);
                    ReviewDetails obj = new ReviewDetails(pushkey,pkey,dkey,ratedValue+"",comment,date,d.getTime());
                    maianref.child(pushkey).setValue(obj);
                    Toast.makeText(getActivity(), "Review Added..", Toast.LENGTH_SHORT).show();

                }


            }
        });



    }
}
