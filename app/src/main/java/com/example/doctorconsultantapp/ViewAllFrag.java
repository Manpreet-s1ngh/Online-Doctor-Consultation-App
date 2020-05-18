package com.example.doctorconsultantapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.CaseMap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;



public class ViewAllFrag extends Fragment {
   TextView tv2,tv22;
   Button bt1;
   ListView listone;

    String pkey;
    DatabaseReference mainref;
    ArrayList<PrescriptionDetails> al = new ArrayList<>();
    myadapter myad = new  myadapter();


    public ViewAllFrag(String pkey) {
        // Required empty public constructor
        this.pkey = pkey;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_all, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        bt1= getActivity().findViewById(R.id.bt1);
        tv2=getActivity().findViewById(R.id.tv2);
        tv22= getActivity().findViewById(R.id.tv22);
        listone= getActivity().findViewById(R.id.listone);
        listone.setAdapter(new myadapter());


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        SharedPreferences sharedPreference=getActivity().getSharedPreferences("Doctor",MODE_PRIVATE);
        final String  did = sharedPreference.getString("Doctorid","");

        mainref = firebaseDatabase.getReference("PrescriptionDetails");
        mainref.orderByChild("p_Key").equalTo(pkey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                al.clear();
                Log.d("vmm",dataSnapshot.toString());
                if(dataSnapshot.exists()){
                    for (DataSnapshot sin : dataSnapshot.getChildren()){
                        PrescriptionDetails obj = sin.getValue(PrescriptionDetails.class);
                        if(obj.getD_Key().equals(did)) {
                            al.add(obj);
                            myad.notifyDataSetChanged();
                        }

                    }

                }


                else {



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
            convertView = inflater.inflate(R.layout.prescription_design,parent,false);

            final PrescriptionDetails pd = al.get(position);
            tv2 = convertView.findViewById(R.id.tv2);
            tv22 = convertView.findViewById(R.id.tv22);
            bt1 = convertView.findViewById(R.id.bt1);
            if(pd.getType().equalsIgnoreCase("text")){
                bt1.setVisibility(View.GONE);
            }

            tv2.setText(pd.Title);
            tv22.setText(pd.Details);
            bt1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),Image_Activity.class);
                    intent.putExtra("image",pd.getPicPath());
                    startActivity(intent);
                }
            });
            return convertView;
        }
    }

}
