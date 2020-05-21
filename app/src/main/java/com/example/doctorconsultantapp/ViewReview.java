package com.example.doctorconsultantapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import static android.text.format.DateUtils.FORMAT_ABBREV_RELATIVE;


public class ViewReview extends Fragment {
String dkey;
ArrayList<ReviewDetails> arrayList = new ArrayList<>();
DatabaseReference mainref;
    Date convertedDate = new Date();
    ListView lv1;
    myadapter ad = new myadapter();
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
        lv1= getActivity().findViewById(R.id.lv1);
        lv1.setAdapter(ad);

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
                Collections.sort(arrayList);
                ad.notifyDataSetChanged();
                Toast.makeText(getActivity(), ""+arrayList.size(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    class myadapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return arrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position*10;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.reviewdesign,parent,false);

            ReviewDetails bk= arrayList.get(position);
            final TextView tv1,tv2,tv3;
            tv1= convertView.findViewById(R.id.tvcomment);
            tv2= convertView.findViewById(R.id.tvrating);
            tv3= convertView.findViewById(R.id.tvtime);

            tv1.setText(bk.getComment());
            tv2.setText("Rating :- "+bk.getRating());
            String dateString = bk.getDate();

            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");

            try {
                convertedDate = dateFormat.parse(dateString);
                long cuMillis = System.currentTimeMillis();
                long diff=cuMillis-convertedDate.getTime();


                String timeAgo = (String) DateUtils.getRelativeTimeSpanString(convertedDate.getTime(), cuMillis, 1, FORMAT_ABBREV_RELATIVE);
                tv3.setText(timeAgo);

            } catch (Exception e) {

                e.printStackTrace();
            }






            return convertView;

        }
    }
}
