package com.example.doctorconsultantapp;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class ViewFrag extends Fragment {
    ArrayList<GalleryDetails> al;

     GridView gv1;
     myadapter myad;
     DatabaseReference mainref;



    public ViewFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

       gv1= getActivity().findViewById(R.id.gv1);
       myad = new myadapter();
        al= new ArrayList<>();
        gv1.setAdapter(myad);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        SharedPreferences sharedPreference=getActivity().getSharedPreferences("Doctor",MODE_PRIVATE);
      String  did = sharedPreference.getString("Doctorid","");
mainref = firebaseDatabase.getReference("DoctorGallery").child(did);
mainref.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        al.clear();
        Log.d("mymsg",dataSnapshot.toString());
        for (DataSnapshot sin : dataSnapshot.getChildren()){
            GalleryDetails obj = sin.getValue(GalleryDetails.class);
            al.add(obj);
        }
        myad.notifyDataSetChanged();
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
            convertView = inflater.inflate(R.layout.doctorsgallerydesign, parent, false);
            final GalleryDetails cs = al.get(position);
            ImageView imv1;
            TextView tv1;
            Button Bt1;



            imv1 = convertView.findViewById(R.id.GalleryImage);
            tv1 = convertView.findViewById(R.id.tv1);
            Bt1 = convertView.findViewById(R.id.Bt1);

            Picasso.get().load(cs.image).into(imv1);
            tv1.setText(cs.caption);

            Bt1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                 mainref.child(cs.galleryid).removeValue();
                  Toast.makeText(getActivity(),"Doctors gallery Deleted",Toast.LENGTH_SHORT).show();

                }
            });

           return convertView;
        }

    }
}
