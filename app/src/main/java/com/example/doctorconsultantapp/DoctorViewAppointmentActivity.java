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
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DoctorViewAppointmentActivity extends AppCompatActivity {
    ArrayList<Booking> al;
    ListView lv1;
    DatabaseReference mainref;
    myadapter ad = new myadapter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_view_appointment);
        al= new ArrayList<>();
        lv1= findViewById(R.id.lv1);
        lv1.setAdapter(ad);
        SharedPreferences sharedPreference=getSharedPreferences("Doctor",MODE_PRIVATE);
        String   d_key = sharedPreference.getString("Doctorid","");

        Toast.makeText(this, ""+d_key, Toast.LENGTH_SHORT).show();
        Log.d("doctorkey",d_key);


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        mainref= firebaseDatabase.getReference("BookingDetails");
        mainref.orderByChild("doctorId").equalTo(d_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                al.clear();
                Log.d("snap", dataSnapshot.toString());
                if(dataSnapshot.exists()){
                    for (DataSnapshot sin : dataSnapshot.getChildren()){
                        Booking obj = sin.getValue(Booking.class);
                        al.add(obj);

                    }
                    ad.notifyDataSetChanged();
            }
                else {
                    Toast.makeText(DoctorViewAppointmentActivity.this, "No Appointment Yet", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    class myadapter extends BaseAdapter {

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
            return position * 10;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.singlerowdesign6, parent, false);

            final Booking bk = al.get(position);
//            TextView tv11, tv22, tv33, tv44, tv55, tv66;
//            tv11 = convertView.findViewById(R.id.tv11);
//            tv22 = convertView.findViewById(R.id.tv22);
//            tv33 = convertView.findViewById(R.id.tv33);
//            tv44 = convertView.findViewById(R.id.tv44);
//            tv55 = convertView.findViewById(R.id.tv55);
//            tv66 = convertView.findViewById(R.id.tv66);
//
//            tv22.setText(bk.PhoneNo);
//            tv33.setText(bk.Problem);
//            tv44.setText(bk.SlotDay);
//            tv55.setText(bk.Start);
//            tv66.setText(bk.End);
            TextView pid=convertView.findViewById(R.id.tvpid);
            TextView probid=convertView.findViewById(R.id.tvprobid);
            TextView abc=convertView.findViewById(R.id.tvpatientname);
            TextView date=convertView.findViewById(R.id.tvdate);
            TextView day=convertView.findViewById(R.id.tvday);
            TextView from=convertView.findViewById(R.id.tvfrom);
            TextView to=convertView.findViewById(R.id.tvto);
            CardView cardview1=convertView.findViewById(R.id.cardview1);
            abc.setText(bk.P_Name);
            date.setText(bk.getDate());
            probid.setText(bk.Problem);
            day.setText(bk.SlotDay);
            from.setText(bk.Start);
            to.setText(bk.End);
            pid.setText(bk.getP_Mobile());
            cardview1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(),Call_options.class);
                    intent.putExtra("mobile",bk.getP_Mobile());
                    startActivity(intent);
                }
            });



            return convertView;

        }
    }
}
