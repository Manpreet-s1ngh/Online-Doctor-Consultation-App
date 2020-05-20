package com.example.doctorconsultantapp;

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


public class PostReview extends Fragment {

    TextView tv1,tv2;
    Button Bt1;
    ImageView imv1;
    RatingBar rt1;
    EditText et1;

    public PostReview() {
        // Required empty public constructor
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

        tv1=getActivity().findViewById(R.id.tv1);
        tv2=getActivity().findViewById(R.id.tv2);
        Bt1=getActivity().findViewById(R.id.Bt1);
        imv1=getActivity().findViewById(R.id.imv1);
        rt1= getActivity().findViewById(R.id.rt1);
        et1= getActivity().findViewById(R.id.et1);
        Bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }
}
