package com.example.doctorconsultantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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


public class ViewAllDoctorsActivity extends AppCompatActivity {
    DatabaseReference mainref;
    ArrayList<Doctor_details> al = new ArrayList<>();
    ListView lv1;
    myadapter myad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_doctors);
        lv1=findViewById(R.id.lv1);
        myad = new myadapter();
        lv1.setAdapter(myad);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        mainref = firebaseDatabase.getReference("DoctorDetails");
        Intent in  = getIntent();
        String cat =in.getStringExtra("category");
        mainref.orderByChild("category").equalTo(cat).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                al.clear();
                if(dataSnapshot.exists()){
                    for (DataSnapshot sin : dataSnapshot.getChildren()){
                        Doctor_details obj  = sin.getValue(Doctor_details.class);
                        if(obj.status.equals("approve")) {
                            al.add(obj);
                        }
                    }
                    myad.notifyDataSetChanged();




                }
                else {
                    Toast.makeText(ViewAllDoctorsActivity.this, "No Doctor under this category", Toast.LENGTH_SHORT).show();
                }

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

            Picasso.get().load(dd.getImagepath()).into(imv1);
            tv1.setText(dd.FullName);
            tv2.setText(dd.Experience);
            tv3.setText(dd.PhoneNo);
            tv4.setText(dd.BasicFees);
            tv5.setText(dd.StartHour + "  " +  dd.EndHour);
            bt1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 Intent intent= new Intent(getApplicationContext(),ViewSingleDoctorDetailsActivity.class);
                     intent.putExtra("FullName",dd.getFullName());
                    intent.putExtra("PhoneNo",dd.getPhoneNo());
                    intent.putExtra("Experience",dd.getExperience());
                    intent.putExtra("start",dd.getStartHour());
                    intent.putExtra("end",dd.getEndHour());
                    intent.putExtra("did",dd.getD_key());
                    intent.putExtra("email",dd.getEmail());
                    intent.putExtra("image",dd.getImagepath());
                    intent.putExtra("BasicFees",dd.getBasicFees());






                 startActivity(intent);
                }
            });

            return convertView;

        }

    }
}
