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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class PatientHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<CategoryDetails> al = new ArrayList<>();
    GridView gv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home);
        gv1=findViewById(R.id.gv1);
        gv1.setAdapter(new myadapter());

        al.add(new CategoryDetails("Gynecologists",R.drawable.gynocologist));
        al.add(new CategoryDetails("ent",R.drawable.ent));
        al.add(new CategoryDetails("cardiologist",R.drawable.cardiologist));
       al.add(new CategoryDetails("Allergists",R.drawable.allergist));
       al.add(new CategoryDetails("Dermatologists",R.drawable.dermatologists));
       al.add(new CategoryDetails("Infectious Disease Doctor",R.drawable.infectiousdiseasedoctor));
       al.add(new CategoryDetails("Ophthalmologists",R.drawable.ophthalmologists));
       al.add(new CategoryDetails("Endocrinologists",R.drawable.endocrinologists));
       al.add(new CategoryDetails("Gastroenterologists",R.drawable.gastroenterologists));
       al.add(new CategoryDetails("Nephrologists",R.drawable.nephrologists));
       al.add(new CategoryDetails("Urologists",R.drawable.urologists));
       al.add(new CategoryDetails("Pulmonologists",R.drawable.pulmonologists));
       al.add(new CategoryDetails("Neurologists",R.drawable.nurologist));
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
        getMenuInflater().inflate(R.menu.patient_home, menu);
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.ViewAppointment:
               Intent intent = new Intent(this,PatientViewAppointmentsActivity.class);
               startActivity(intent);
                Toast.makeText(this,"View Appointment",Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_ViewPrescription:
                Intent intent1 = new Intent(this,ViewPrescriptionActivity.class);
                startActivity(intent1);
                Toast.makeText(this,"View Prescription",Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_ChangePassword:
                Intent intent3 = new Intent(this,PatientChangePasswordActivity.class);
                startActivity(intent3);
                Toast.makeText(this,"Change Password Selected",Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_Logout:
                SharedPreferences sharedPreference=getSharedPreferences("Patient",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreference.edit();
                editor.remove("......");
                editor.apply();
                finish();
                Intent intent2 = new Intent(getApplicationContext(),PatientLoginActivity.class);
                startActivity(intent2);
                Toast.makeText(this,"Logout Selected",Toast.LENGTH_SHORT).show();
                break;
        }

       return true;
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
            convertView = inflater.inflate(R.layout.doctorcategorydesign,parent,false);

          final  CategoryDetails pd= al.get(position);

            ImageView imv1;
            TextView tv1;
            LinearLayout lv1;

            imv1= convertView.findViewById(R.id.imv1);
            tv1= convertView.findViewById(R.id.tv1);
            lv1= convertView.findViewById(R.id.lv1);
            tv1.setText(pd.Category);
            Picasso.get().load(pd.imgid).into(imv1);
            lv1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //open new activity and pass category name
///                   mainref.orderbychild("category").equlato(categoryname)
                    Intent intent = new Intent(getApplicationContext(),ViewAllDoctorsActivity.class);
                     intent.putExtra("category",pd.Category);
                    startActivity(intent);

                }
            });

            return convertView;


        }
    }
}
