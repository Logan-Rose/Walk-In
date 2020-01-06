package com.example.walkin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Patient_viewClinicsOfferingService extends AppCompatActivity {
    DatabaseReference dataref;
    DatabaseReference datarefUsers;
    ListView listViewClinics;
    List<Clinic> clinics;
    private FirebaseAuth firebaseAuth;
    Clinic clinic;
    Clinic currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_view_clinics_offering_service);

        dataref = FirebaseDatabase.getInstance().getReference().child("Clinic");

        datarefUsers = FirebaseDatabase.getInstance().getReference().child("User");

        listViewClinics = findViewById(R.id.listViewClinics);

        clinics =  new ArrayList<>();
        clinic = new Clinic();
        firebaseAuth = FirebaseAuth.getInstance();
        datarefUsers.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                currentUser = dataSnapshot.getValue(Clinic.class);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Error: ", "Firebase Failed");
            }
        });

    }
    protected void onStart(){
        super.onStart();
        dataref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Clinic clinic = new Clinic();
                    clinics.add(clinic);
                }

                Patient_ClinicList clinicsAdapter = new Patient_ClinicList(Patient_viewClinicsOfferingService.this, clinics);
                listViewClinics.setAdapter(clinicsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}