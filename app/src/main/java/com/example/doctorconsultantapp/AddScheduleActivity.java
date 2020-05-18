package com.example.doctorconsultantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class AddScheduleActivity extends AppCompatActivity {
    LinearLayout ll1;
    ArrayList<String> al;
    Spinner sp1;
    ArrayAdapter<String> ad;
    slotdetails obj;
    String day = "";
    DatabaseReference mainref;
    String did;
    ArrayList<slotdetails> arrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        mainref = firebaseDatabase.getReference("DoctorSlots");
        SharedPreferences sharedPreference = getSharedPreferences("Doctor", MODE_PRIVATE);
        did = sharedPreference.getString("Doctorid", "");


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


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ll1 = findViewById(R.id.ll1);


//        for (int i=0; i< al.size(); i++)
//        {
//            checkBox = new CheckBox(this);
//            checkBox.setId(i);
//            checkBox.setText(al.get(i));
//            ll1.addView(checkBox);
//
//
//        }

            Intent in = getIntent();
            int start = Integer.parseInt("09");
            int end = Integer.parseInt("15");
            int abc = 0;
            for (int i = start; i < end; i++) {
                final CheckBox cbi;
                final int finalI = i;
                cbi = new CheckBox(getApplicationContext());
                cbi.setText("" + i + ":" + (finalI + 1));

                ll1.addView(cbi);


                final int finalAbc = abc;
                cbi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (cbi.isChecked()) {
//                        Toast.makeText(Doctor_Post_Schedule.this, "iiiii" + cbi.getText(), Toast.LENGTH_SHORT).show();
                            StringTokenizer st = new StringTokenizer(cbi.getText().toString(), ":");
                            String first = st.nextToken();
                            String last = st.nextToken();
//                        Toast.makeText(Doctor_Post_Schedule.this, first+"..."+last, Toast.LENGTH_SHORT).show();
                            if (day.equals("")) {
                                Toast.makeText(AddScheduleActivity.this, "Select day first", Toast.LENGTH_SHORT).show();
                            } else {
                                obj = new slotdetails();
                                obj.setDay(day);
                                obj.setDid(did);
                                obj.setScheduleid(mainref.push().getKey());
                                obj.setSlotend(last);
                                obj.setSlotstart(first);
                                arrayList.add(obj);

                            }

                        } else {


                            // Delete last element by passing index
                           try {
                               arrayList.remove(finalAbc);
                               Toast.makeText(AddScheduleActivity.this, "Removed", Toast.LENGTH_SHORT).show();
                           }
                          catch (Exception ex)
                          {
                              ex.printStackTrace();
                          }
                        }
                    }
                });
                abc++;
            }

        }





    public void go(View view)
    {
        for (int i = 0 ; i < arrayList.size();i++){
            slotdetails obj = new slotdetails();
            obj.setDay(arrayList.get(i).day);
            obj.setDid(arrayList.get(i).did);
            obj.setScheduleid(arrayList.get(i).getScheduleid());
            obj.setSlotend(arrayList.get(i).slotend);
            obj.setSlotstart(arrayList.get(i).slotstart);
            mainref.child(did).child(obj.getScheduleid()).setValue(obj);

        }
        finish();

    }
}
