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

public class Patient_viewServices extends AppCompatActivity {
    DatabaseReference dataref;
    DatabaseReference datarefUsers;
    ListView listViewServices;
    List<Service> services;
    ArrayList<Service> userServices;
    private FirebaseAuth firebaseAuth;
    Service service;
    Clinic currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_view_services);

        dataref = FirebaseDatabase.getInstance().getReference().child("Service");

        datarefUsers = FirebaseDatabase.getInstance().getReference().child("User");

        listViewServices = findViewById(R.id.listViewServices);

        services =  new ArrayList<>();
        service = new Service();
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

        listViewServices.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Service service = services.get(i);
                Intent myIntent = new Intent(Patient_viewServices.this, Patient_viewClinicsOfferingService.class);
                myIntent.putExtra("service", service.getServiceName());

                startActivity(myIntent); // Begin Activity
                return true;
            }
        });
    }
    protected void onStart(){
        super.onStart();
        dataref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                services.clear();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Service service = new Service();

                    service.setId(postSnapshot.child("id").getValue(String.class));
                    service.setName(postSnapshot.child("serviceName").getValue(String.class));
                    service.setRole(postSnapshot.child("serviceRole").getValue(String.class));
                    services.add(service);
                }

                Clinic_ServiceList servicesAdapter = new Clinic_ServiceList(Patient_viewServices.this, services);
                listViewServices.setAdapter(servicesAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}