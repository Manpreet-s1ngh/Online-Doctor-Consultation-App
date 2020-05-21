package com.example.doctorconsultantapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ViewReview extends Fragment {
String dkey;
ArrayList<ReviewDetails> arrayList = new ArrayList<>();
DatabaseReference mainref;
    public ViewReview(String dkey) {
        // Required empty public constructor
        this.dkey = dkey;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_review, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        mainref = firebaseDatabase.getReference("RatingDetails");
        mainref.orderByChild("d_key").equalTo(dkey).addValueEventListener(new ValueEventListener() {
//        mainref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                Log.d("vmm",dataSnapshot.toString());
                for (DataSnapshot sin : dataSnapshot.getChildren()){
                    ReviewDetails obj = sin.getValue(ReviewDetails.class);
                    arrayList.add(obj);
                }
                Toast.makeText(getActivity(), ""+arrayList.size(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
