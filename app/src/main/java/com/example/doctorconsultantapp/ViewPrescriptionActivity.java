package com.example.doctorconsultantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewPrescriptionActivity extends AppCompatActivity {
    ArrayList<PrescriptionDetails> al = new ArrayList<>();
    TextView tv2,tv22,tv222,tv2222;
    Button bt1;
    ListView lv1;
    myadapter ad = new myadapter();
    DatabaseReference mainref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_prescription);

        tv2 = findViewById(R.id.tv2);
        tv22= findViewById(R.id.tv22);
        tv222= findViewById(R.id.tv222);
        tv2222= findViewById(R.id.tv2222);
        bt1 = findViewById(R.id.bt1);
        lv1 = findViewById(R.id.lv1);
        lv1.setAdapter(ad);
        SharedPreferences sharedPreference=getSharedPreferences("Patient",MODE_PRIVATE);
        final String  pkey = sharedPreference.getString("Patient_Key","");


        FirebaseDatabase firebaseDatabase
                 = FirebaseDatabase.getInstance();
        mainref = firebaseDatabase.getReference("PrescriptionDetails");
        mainref.orderByChild("p_Key").equalTo(pkey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("datachange",dataSnapshot.toString());
              if(dataSnapshot.exists()){
                  al.clear();
                  Log.d("vmm",dataSnapshot.toString());
                  for (DataSnapshot sin : dataSnapshot.getChildren()){
                      PrescriptionDetails obj = sin.getValue(PrescriptionDetails.class);
                      al.add(obj);
                      ad.notifyDataSetChanged();
                  }
              }
              else {
                  Toast.makeText(ViewPrescriptionActivity.this, "No Prescription yet", Toast.LENGTH_SHORT).show();
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
            convertView = inflater.inflate(R.layout.viewprescriptiondesign,parent,false);
           final PrescriptionDetails pd1 = al.get(position);
           TextView tv2,tv22,tv222,tv2222;
           Button bt1;

           tv2= convertView.findViewById(R.id.tv2);
           tv22 = convertView.findViewById(R.id.tv22);
           tv222= convertView.findViewById(R.id.tv222);
           tv2222= convertView.findViewById(R.id.tv2222);
           bt1 = convertView.findViewById(R.id.bt_view);
            if(pd1.getType().equalsIgnoreCase("text")){
                bt1.setVisibility(View.GONE);
            }
           tv2.setText(pd1.DoctorName);
           tv22.setText(pd1.Category);
           tv222.setText(pd1.Title);
           tv2222.setText(pd1.Details);
           bt1.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intent = new Intent(getApplicationContext(),ImageActivity2.class);
                   intent.putExtra("image",pd1.getPicPath());
                   startActivity(intent);
               }
           });

           return convertView;


        }
    }

}
