package com.example.doctorconsultantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
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

public class ManagePrescriptionActivity extends AppCompatActivity {
    ArrayList<PatientDetails> al = new ArrayList<>();
    ListView lv1;
    myadapter ad = new myadapter();
    DatabaseReference mainref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_prescription);
        lv1 = findViewById(R.id.lv1);
        al = new ArrayList<>();
        lv1.setAdapter(ad);



        SharedPreferences sharedPreference = getSharedPreferences("PatientDetails", MODE_PRIVATE);
        String patientKey = sharedPreference.getString("patientKey", "");

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        mainref = firebaseDatabase.getReference("PatientDetails");
        mainref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                al.clear();
                Log.d("snap", dataSnapshot.toString());
                if (dataSnapshot.exists()) {
                    for (DataSnapshot sin : dataSnapshot.getChildren()) {
                        PatientDetails obj = sin.getValue(PatientDetails.class);
                        al.add(obj);

                    }
                    ad.notifyDataSetChanged();



                }



                 else{
                    Toast.makeText(ManagePrescriptionActivity.this, "No Appointment Yet", Toast.LENGTH_SHORT).show();
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
            convertView = inflater.inflate(R.layout.manageprescriptiondesign,parent,false);

            final PatientDetails pd = al.get(position);
            ImageView imv1;
            TextView textView2,textView3,textView4;
            CardView cv1 = convertView.findViewById(R.id.cardview1);

            imv1 = convertView.findViewById(R.id.imv1);
            textView2 = convertView.findViewById(R.id.textView2);
            textView3 = convertView.findViewById(R.id.textView3);
            textView4 = convertView.findViewById(R.id.textView4);

            Picasso.get().load(pd.Photo).into(imv1);
            textView2.setText(pd.FullName);
            textView3.setText(pd.Email);
            textView4.setText(pd.Phoneno);
            cv1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(),ManagePrescription2Activity.class);
                    intent.putExtra("p_key",pd.getPatientKey());
                    startActivity(intent);
                }
            });

            return convertView;



        }
    }
}
