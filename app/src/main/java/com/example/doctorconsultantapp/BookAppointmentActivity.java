package com.example.doctorconsultantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class BookAppointmentActivity extends AppCompatActivity {

    ArrayList<String> al;
    //Spinner sp1;

    ArrayAdapter<String> ad;
    BookAppointmentActivity.myadapter2 myad;
    ArrayList<slotdetails> al1;
    String day =" ";


    String did;
    String finder;

    //
    TextView showdate;
    ArrayList<String> mylist;
    TextView daySelected;
    TextView stayAlert;
    String selecteddate;
     ArrayList<leaveDetails> leavelist;
     Set<String> set;
    DatabaseReference leaveref;
    DatabaseReference mainref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        showdate=findViewById(R.id.showDate);
//        Toast.makeText(this, "RECEIVED>>>", Toast.LENGTH_SHORT).show();
        stayAlert=findViewById(R.id.showAlert);
        daySelected=findViewById(R.id.daySelected);
        al1= new ArrayList<>();
        mylist=new ArrayList<>();


        // getting the doctor id from ONlickBookingAppointment
        Intent in = getIntent();
        did = in.getStringExtra("d_key");
//       Toast.makeText(this, "---$---->"+did, Toast.LENGTH_SHORT).show();

        FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
        mainref = firebaseDatabase.getReference("DoctorSlots").child(did);
       // FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
        leaveref = firebaseDatabase.getReference("LeaveDetail");

        GridView lvsat = findViewById(R.id.search);
        myad=new BookAppointmentActivity.myadapter2();
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
       // sp1 = findViewById(R.id.sp1);
       // ad = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, al);

       /* sp1.setAdapter(ad);
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                day = al.get(position);// get day name
                mainref.orderByChild("day").equalTo(day).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Log.d("datasnap",dataSnapshot.toString());

                        al1.clear();
                        for (DataSnapshot sin :  dataSnapshot.getChildren())
                        {
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
        });*/

        // creating a new funcion to get name

        mylist.add("Select day of the week");
        mylist.add("Sunday");
        mylist.add("Monday");
        mylist.add("Tuesday");
        mylist.add("Wednesday");
        mylist.add("Thursday");
        mylist.add("Friday");
        mylist.add("Saturday");

        final Calendar myCalender= Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalender.set(android.icu.util.Calendar.YEAR,year);
                myCalender.set(android.icu.util.Calendar.MONTH,month);
                myCalender.set(android.icu.util.Calendar.DAY_OF_MONTH,dayOfMonth);

                Label();
            }

            @SuppressLint("ResourceAsColor")
            private void Label() {
                String myFormat = "dd-MM-yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                int dayOfWeek = myCalender.get(Calendar.DAY_OF_WEEK);
                day=mylist.get(dayOfWeek); // getting the day with date;
                daySelected.setText(day);
                //Storing the date in variable
                selecteddate=sdf.format(myCalender.getTime());
                showdate.setText(selecteddate);

                stayAlert.setTextColor(android.R.color.white);
                // check for leave



                    //  checking for the existing slots
                    getbookedslots();

                         //   //
                    // //
                    mainref.orderByChild("day").equalTo(day).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            Log.d("datasnap", dataSnapshot.toString());

                            al1.clear();
                            stayAlert.setText("");
                            for (DataSnapshot sin : dataSnapshot.getChildren()) {
                                slotdetails bj = sin.getValue(slotdetails.class);
                                if(set.contains(bj.getSlotstart()))
                                {
                                    // skip the value if the there is booked slot
                                }
                                else {
                                    al1.add(bj);
                                }
                            }

                            if (al1.size() == 0) // if there is no booking schedule then
                            {   stayAlert.setText("No Schedule for Today");
                               // stayAlert.setTextColor(Color.RED);
                            }

                            Toast.makeText(BookAppointmentActivity.this, "Yeh works --", Toast.LENGTH_SHORT).show();
                            myad.notifyDataSetChanged();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(BookAppointmentActivity.this, "Check For Internet Connection", Toast.LENGTH_SHORT).show();
                        }
                    });

                  Log.d("MYMSG","going to run");
                  getleavefunction();
               /* if(leavelist.size()==0) {
                    Toast.makeText(BookAppointmentActivity.this, "HelloWorld", Toast.LENGTH_SHORT).show();
                }
                else
                {
                }
                 stayAlert.setTextColor(Color.RED);
*/                 stayAlert.setTextColor(Color.RED);
            }
        };

        showdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
                DatePickerDialog datePickerDialog=new DatePickerDialog(BookAppointmentActivity.this,
                        date,myCalender.get(android.icu.util.Calendar.YEAR),
                        myCalender.get(android.icu.util.Calendar.MONTH),
                        myCalender.get(android.icu.util.Calendar.DAY_OF_MONTH));

                 datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
                datePickerDialog.show();

            }
        });

        // // //


    }

    private void getbookedslots() {
        Log.d("MYMSG","I am here");
        set= new HashSet<>();
        //String finderkey=did+"--"+selecteddate; // making the key for checking
        FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
        DatabaseReference booked = firebaseDatabase.getReference("BookedSlots");
        DatabaseReference bookeddoctor =booked.child(did);
        Log.d("MYMSG","startiinggg");
        bookeddoctor.orderByChild("date").equalTo(selecteddate).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                set.clear();
                for (DataSnapshot sin : dataSnapshot.getChildren()) {
                    Log.d("MYMSG","searching Successfully");
                    Log.d("MYMSG",sin+"");
                    BookedSlots bj = sin.getValue(BookedSlots.class);
                    Log.d("MYMSG","Object created Successfully");
                    String start=bj.getStart();
                    set.add(start);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getleavefunction() {
        leavelist=new ArrayList<>();
        finder=did+"-"+selecteddate;

        Log.d("MYMSG",finder);

        leaveref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                leavelist.clear();
                                       Log.d("MYMSG","Entering");

                for (DataSnapshot sin : dataSnapshot.getChildren() )
                {
                    leaveDetails bj = sin.getValue(leaveDetails.class);
                    Log.d("MYMSG",sin+"  "+"went weong here");
                    if(bj.getIdwithdate().equals(finder))
                    {
                        leavelist.add(bj);
                        Log.d("MYMSG","leave object addded Successfully");
                        Log.d("MYMSG","Runned Successfully");
                        stayAlert.setText(" Sorry, Doctor is on Leave Today ");
                        //stayAlert.setTextColor(Color.RED);
                    }
                    Log.d("MYMSG",leavelist.size()+"");
                }
                Log.d("MYMSG","Exitsssss");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
            convertView= inflater.inflate(R.layout.patienttimeslotdesign,parent,false);

            final slotdetails obj = al1.get(position);
            TextView tv11,tv22;
            Button Bt1;

            tv11=convertView.findViewById(R.id.tv11);
            tv22= convertView.findViewById(R.id.tv22);
            Bt1 = convertView.findViewById(R.id.Bt1);

            tv11.setText("From :- "+obj.slotstart);
            tv22.setText("  To :- "+obj.slotend);
            Bt1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getApplicationContext(),ConfirmAppointmentActivity.class);
                    intent.putExtra("Start",obj.slotstart);
                    intent.putExtra("End",obj.slotend);
                    intent.putExtra("DoctorId",obj.did);
                    intent.putExtra("Day",obj.day);
                    intent.putExtra("date",selecteddate);

                    startActivity(intent);

                }
            });
            return convertView;
        }
    }
}


