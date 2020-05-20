package com.example.doctorconsultantapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.prefs.Preferences;

public class DoctorHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mainref2;
    ImageView imv1;
    TextView tv1,tv2,tv3,tv4;
    String did;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_home);
        imv1= findViewById(R.id.imv1);
        tv1= findViewById(R.id.tv1);
        tv2= findViewById(R.id.tv2);
        tv3= findViewById(R.id.tv3);
        tv4= findViewById(R.id.tv4);
        SharedPreferences sharedPreference=getSharedPreferences("Doctor",MODE_PRIVATE);
        did = sharedPreference.getString("Doctorid","");
        Toast.makeText(this, ""+did, Toast.LENGTH_SHORT).show();


        firebaseDatabase = FirebaseDatabase.getInstance();

        mainref2 = firebaseDatabase.getReference("DoctorDetails");

        mainref2.orderByChild("d_key").equalTo(did).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("datasnap",dataSnapshot.toString());
                for (DataSnapshot sin : dataSnapshot.getChildren()){
                    Doctor_details obj = sin.getValue(Doctor_details.class);
                    Doctor_Global_Class.category = obj.getCategory();
                    Doctor_Global_Class.dname = obj.getFullName();
                    Doctor_Global_Class.dphone = obj.getPhoneNo();
                    tv1.setText(obj.FullName);
                    tv2.setText("Email :"+obj.Email);
                    tv3.setText(obj.PhoneNo);
                    tv4.setText(obj.Experience);
                    Picasso.get().load(obj.getImagepath()).into(imv1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

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
                break;

            case R.id.nav_7:

                SharedPreferences sharedPreference=getSharedPreferences("Doctor",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreference.edit();
                editor.remove("......");
                editor.apply();
                finish();
                Intent intent = new Intent(getApplicationContext(),DoctorLoginActivity.class);
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

}
