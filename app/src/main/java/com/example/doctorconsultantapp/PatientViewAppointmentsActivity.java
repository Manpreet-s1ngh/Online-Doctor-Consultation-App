package com.example.doctorconsultantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

public class PatientViewAppointmentsActivity extends AppCompatActivity {
    ArrayList<Booking> al;
    ListView lv1;
    DatabaseReference mainref;
    myadapter ad = new myadapter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_view_appointments);
        al= new ArrayList<>();
        lv1= findViewById(R.id.lv1);
        lv1.setAdapter(ad);

        SharedPreferences sharedPreference=getSharedPreferences("Patient",MODE_PRIVATE);
      String   p_key = sharedPreference.getString("Patient_Key","");

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        mainref= firebaseDatabase.getReference("BookingDetails");
        mainref.orderByChild("p_Key").equalTo(p_key).addValueEventListener(new ValueEventListener() {
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
                    Toast.makeText(PatientViewAppointmentsActivity.this, "No Appointment Yet", Toast.LENGTH_SHORT).show();
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
            convertView = inflater.inflate(R.layout.singlerowdesign7,parent,false);

           Booking bk= al.get(position);
          //  TextView tv11,tv22,tv33,tv44,tv55,tv66;
            //tv11 = convertView.findViewById(R.id.tv11);
           // tv22 = convertView.findViewById(R.id.tv22);
           // tv33 = convertView.findViewById(R.id.tv33);
           // tv44 = convertView.findViewById(R.id.tv44);
           // tv55 = convertView.findViewById(R.id.tv55);
           // tv66 = convertView.findViewById(R.id.tv66);

          //  tv11.setText(bk.Name);
          //  tv22.setText(bk.PhoneNo);
          //  tv33.setText(bk.Problem);
          //  tv44.setText(bk.SlotDay);
          //  tv55.setText(bk.Start);
          //  tv66.setText(bk.End);
            TextView pid=convertView.findViewById(R.id.tvpid);
            TextView probid=convertView.findViewById(R.id.tvprobid);
            TextView abc=convertView.findViewById(R.id.tvpatientname);
            TextView date=convertView.findViewById(R.id.tvdate);
            TextView day=convertView.findViewById(R.id.tvday);
            TextView from=convertView.findViewById(R.id.tvfrom);
            TextView to=convertView.findViewById(R.id.tvto);

            abc.setText(bk.Name);
            date.setText(bk.getDate());
            probid.setText(bk.Problem);
            day.setText(bk.SlotDay);
            from.setText(bk.Start);
            to.setText(bk.End);
            pid.setText(bk.getPhoneNo());


            return convertView;

        }
    }
}
