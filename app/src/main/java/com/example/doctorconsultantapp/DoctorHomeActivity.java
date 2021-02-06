package com.example.doctorconsultantapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Collections;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.prefs.Preferences;

public class DoctorHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mainref2;
    //ImageView imv1;
    //TextView tv1,tv2,tv3,tv4;
    String did;
    Button leaveButton,prescription,gallery;
    //
    TextView name_nav, phone_nav ,email_nav;
    ImageView image_nav;
    DatabaseReference mainref;
    ArrayList<Booking> al;
    ListView lv1;
    myadapter ad=new myadapter();

    String drname,category,endhr,starthr,phone,basicfees,imagepath,exp , service,key;
    private View navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_home);
        //imv1= findViewById(R.id.imv1);
        /*tv1= findViewById(R.id.tv1);
        tv2= findViewById(R.id.tv2);
        tv3= findViewById(R.id.tv3);
        tv4= findViewById(R.id.tv4);*/
        //
        //
        al= new ArrayList<>();
        lv1= findViewById(R.id.todayAPlist);
        lv1.setAdapter(ad);
        //
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        name_nav = (TextView) headerView.findViewById(R.id.name_nav);
        phone_nav = (TextView) headerView.findViewById(R.id.phone_nav);
        email_nav = (TextView) headerView.findViewById(R.id.mail_nav);
        image_nav = (ImageView) headerView.findViewById(R.id.imageView_nav);
         //name_nav= navigationView.findViewById(R.id.name_nav);

        //

        leaveButton=findViewById(R.id.leaveButton);
        prescription=findViewById(R.id.managePresciptionButton);
        gallery=findViewById(R.id.manageGalleryButton);

        SharedPreferences sharedPreference=getSharedPreferences("Doctor",MODE_PRIVATE);
        did = sharedPreference.getString("Doctorid","");
//        Toast.makeText(this, ""+did, Toast.LENGTH_SHORT).show();

        // OnclickLeaveButton
         leaveButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(DoctorHomeActivity.this,LeaveSet.class);
                 intent.putExtra("did",did);
                 startActivity(intent);

             }
         });

         prescription.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent5 = new Intent(DoctorHomeActivity.this,ManagePrescriptionActivity.class);
                 startActivity(intent5);
             }
         });

         gallery.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent3 = new Intent(DoctorHomeActivity.this,ManageGalleryActivity.class);
                 startActivity(intent3);
             }
         });

        firebaseDatabase = FirebaseDatabase.getInstance();

        mainref2 = firebaseDatabase.getReference("DoctorDetails");

        mainref2.orderByChild("d_key").equalTo(did).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("datasnap",dataSnapshot.toString());

                for (DataSnapshot sin : dataSnapshot.getChildren()){
                    Doctor_details obj = sin.getValue(Doctor_details.class);
                    Doctor_Global_Class.category = obj.getCategory();
                    Doctor_Global_Class.dname = obj.getFullName();
                    Doctor_Global_Class.dphone = obj.getPhoneNo();


                   // tv1.setText(obj.FullName);
                    name_nav.setText(obj.FullName);

                    //tv2.setText("Email :"+obj.Email);
                    email_nav.setText(obj.Email);

                   // tv3.setText(obj.PhoneNo);
                    phone_nav.setText(obj.PhoneNo);

                    //tv4.setText(obj.Experience);
                    //Picasso.get().load(obj.getImagepath()).into(imv1);
                    Picasso.get().load(obj.getImagepath()).into(image_nav);

                    drname = obj.FullName;
                    category = obj.getCategory();

                            endhr = obj.getEndHour();
                            starthr = obj.getStartHour();
                            phone = obj.getPhoneNo();
                            basicfees= obj.getBasicFees();
                            imagepath= obj.getImagepath();
                    exp = obj.getExperience();
                    service = obj.getService();
                    key = obj.getD_key();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //setting up the code for the todays appointment
      // // //
        Date date = new Date();
        final String modifiedDate= new SimpleDateFormat("dd-MM-yyyy").format(date);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        mainref= firebaseDatabase.getReference("BookingDetails");
        mainref.orderByChild("doctorId").equalTo(did).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                al.clear();
                Log.d("snap", dataSnapshot.toString());
                if(dataSnapshot.exists()){
                    for (DataSnapshot sin : dataSnapshot.getChildren()){
                        Booking obj = sin.getValue(Booking.class);

                        if(obj.getDate().equals(modifiedDate)) {
                            al.add(obj);
                        }

                    }
                    Collections.sort(al,Collections.reverseOrder());
                    ad.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(DoctorHomeActivity.this, "No Appointment Yet", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // // // // // //
      // // // // // // // // // // // // // //


         Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
         navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.doctor_home, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.nav_1:
               Intent intent1 = new Intent(this,AddScheduleActivity.class);
               startActivity(intent1);
              break;

            case R.id.nav_2:
                Intent intent2 = new Intent(this,ManageScheduleActivity.class);
                startActivity(intent2);
                break;

            case R.id.nav_3:
                Intent intent6 = new Intent(this,DoctorChangePasswordActivity.class);

                startActivity(intent6);
                Toast.makeText(this," Change Password",Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_4:
                Intent intent5 = new Intent(this,ManagePrescriptionActivity.class);
                startActivity(intent5);
                Toast.makeText(this,"Manage Prescription",Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_5:
               Intent intent4 = new Intent(this,DoctorViewAppointmentActivity.class);
               startActivity(intent4);
                Toast.makeText(this,"View Appointment",Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_6:
                Toast.makeText(this,"Edit Profile",Toast.LENGTH_SHORT).show();
                Intent in = new Intent(this,Doctor_Edit_Profile.class);

                in.putExtra("drname",drname);
                in.putExtra("category",category);
                in.putExtra("endhr",endhr);
                in.putExtra("starthr",starthr);
                in.putExtra("phone",phone);
                in.putExtra("basicfees",basicfees);
                in.putExtra("imagepath",imagepath);
                in.putExtra("exp",exp);
                in.putExtra("service",service);
                in.putExtra("key",key);

                startActivity(in);
                break;

            case R.id.nav_7:

                SharedPreferences sharedPreference=getSharedPreferences("Doctor",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreference.edit();
                editor.remove("Doctorid");
                editor.apply();
                finish();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);

                Toast.makeText(this,"Logout",Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_8:
                Intent intent3 = new Intent(this,ManageGalleryActivity.class);
                startActivity(intent3);
                Toast.makeText(this,"Manage Gallery",Toast.LENGTH_SHORT).show();
                break;



        }
        return true;
    }

// on logout
    //remove value from shared prefrence
    // redirect to doctor login screen
    //Created an adapter for showing list
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

        /*public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.singlerowdesign6, parent, false);

            final Booking bk = al.get(position);

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

        }*/
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.singlerowdesign8, parent, false);

            final Booking bk = al.get(position);

            TextView pid=convertView.findViewById(R.id.tvpid);
            TextView probid=convertView.findViewById(R.id.tvprobid);
            TextView abc=convertView.findViewById(R.id.tvpatientname);
            TextView date=convertView.findViewById(R.id.tvdate);
            TextView day=convertView.findViewById(R.id.tvday);
            TextView from=convertView.findViewById(R.id.tvfrom);
            TextView to=convertView.findViewById(R.id.tvto);

            Button consult=convertView.findViewById(R.id.consultBt);
            Button prescript=convertView.findViewById(R.id.prescriptBt);

            CardView cardview1=convertView.findViewById(R.id.cardview1);
            abc.setText(bk.P_Name);
            date.setText(bk.getDate());
            probid.setText(bk.Problem);
            day.setText(bk.SlotDay);
            from.setText(bk.Start);
            to.setText(bk.End);
            pid.setText(bk.getP_Mobile());

           consult.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Intent intent = new Intent(getApplicationContext(),Call_options.class);
                   intent.putExtra("mobile",bk.PhoneNo);
                   startActivity(intent);
               }
           });

           prescript.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Intent intent = new Intent(getApplicationContext(),ManagePrescription2Activity.class);
                   intent.putExtra("p_key",bk.getP_Key());
                   startActivity(intent);
               }
           });

            /*cardview1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(),Call_options.class);
                    intent.putExtra("mobile",bk.getP_Mobile());
                    startActivity(intent);
                }
            });*/



            return convertView;

        }
    }

}
