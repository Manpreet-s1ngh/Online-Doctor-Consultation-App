package com.example.doctorconsultantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ManageScheduleActivity extends AppCompatActivity {

    ArrayList<String> al;
    Spinner sp1;

    ArrayAdapter<String> ad;
    myadapter2 myad;
//    ArrayList<Doctor_details> al1;
    ArrayList<slotdetails> al1;
    String day =" ";
    DatabaseReference mainref;
    String did;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_schedule);

        al1= new ArrayList<>();
        SharedPreferences sharedPreference = getSharedPreferences("Doctor", MODE_PRIVATE);
        did = sharedPreference.getString("Doctorid", "");
        FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
        mainref = firebaseDatabase.getReference("DoctorSlots").child(did);

        GridView lvsat = findViewById(R.id.search);
        myad=new myadapter2();
        lvsat.setAdapter(myad);




        al = new ArrayList<>();
        al.add("Select day of the week");
        al.add("Monday");
        al.add("Tuesday");
        al.add("Wednesday");
        al.add("Thursday");
        al.add("Friday");
        al.add("Saturday");
        al.add("Sunday");
        sp1 = findViewById(R.id.sp1);

        ad = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, al);
        sp1.setAdapter(ad);
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                day = al.get(position);
                mainref.orderByChild("day").equalTo(day).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Log.d("datasnap",dataSnapshot.toString());
                        al1.clear();
                        for (DataSnapshot sin :  dataSnapshot.getChildren()){
                            slotdetails bj = sin.getValue(slotdetails.class);
                            al1.add(bj);
                        }
                        myad.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





    }
    ///INNER CLASS
    class myadapter2 extends BaseAdapter
    {

        @Override
        public int getCount() {
            return al1.size();
        }

        @Override
        public Object getItem(int i) {
            return al1.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i*10;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView= inflater.inflate(R.layout.time_slot_design,parent,false);

            final slotdetails obj = al1.get(position);
            TextView tv11,tv22;
            Button Bt1;

            tv11=convertView.findViewById(R.id.tv11);
            tv22= convertView.findViewById(R.id.tv22);
            Bt1 = convertView.findViewById(R.id.Bt1);

            tv11.setText("From :-"+obj.slotstart);
            tv22.setText("To :-"+obj.slotend);
            Bt1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   mainref.child(obj.scheduleid).removeValue();
                    Toast.makeText(ManageScheduleActivity.this, "Slot Deleted", Toast.LENGTH_SHORT).show();

                }
            });
            return convertView;
        }
    }
}
