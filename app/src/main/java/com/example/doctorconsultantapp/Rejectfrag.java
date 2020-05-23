package com.example.doctorconsultantapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Rejectfrag extends Fragment {
    DatabaseReference mainref;
    ListView lv1;
    myadapter myad;
    ArrayList<Doctor_details> al = new ArrayList<>();
    public Rejectfrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rejectfrag, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        lv1=getActivity().findViewById(R.id.lvreject);
        myad = new myadapter();
        lv1.setAdapter(myad);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        mainref  = firebaseDatabase.getReference("DoctorDetails");
        mainref.orderByChild("status").equalTo("pending").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("vmm_reject",dataSnapshot.toString());
                al.clear();
                for (DataSnapshot sin : dataSnapshot.getChildren()){
                    Doctor_details obj = sin.getValue(Doctor_details.class);
                    al.add(obj);

                }
                myad.notifyDataSetChanged();



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
            return al.size();
        }

        @Override
        public Object getItem(int position) {
            return al.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position*10;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.doctorcategorydetailsdesign,parent,false);
            final Doctor_details dd = al.get(position);
            ImageView imv1;
            final TextView tv1,tv2,tv3,tv4,tv5;
            Button bt1;
            imv1= convertView.findViewById(R.id.imv111);
            tv1= convertView.findViewById(R.id.tv111);
            tv2= convertView.findViewById(R.id.tv222);
            tv3= convertView.findViewById(R.id.tv333);
            tv4= convertView.findViewById(R.id.tv444);
            tv5= convertView.findViewById(R.id.tv555);
            bt1=convertView.findViewById(R.id.bt1);
            bt1.setText("Approve");

            Picasso.get().load(dd.getImagepath()).into(imv1);
            tv1.setText(dd.FullName);
            tv2.setText(dd.Experience);
            tv3.setText(dd.PhoneNo);
            tv4.setText(dd.BasicFees);
            tv5.setText(dd.category);
            bt1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mainref.child(dd.getD_key()).child("status").setValue("approve");
                    Toast.makeText(getActivity(), "Doctor Approved Successfully", Toast.LENGTH_SHORT).show();




                }
            });

            return convertView;

        }

    }
}
