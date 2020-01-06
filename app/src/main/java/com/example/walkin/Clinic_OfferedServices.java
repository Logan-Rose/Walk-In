package com.example.walkin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Clinic_OfferedServices extends AppCompatActivity {
    DatabaseReference datarefUsers;
    DatabaseReference serviceRef;
    DatabaseReference dataref;
    ListView listViewOfferedServices;
    ArrayList<Service> services;
    private FirebaseAuth firebaseAuth;
    Service service;
    private Clinic currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        services = new ArrayList<Service>();
        setContentView(R.layout.activity_clinic_view_services_offered);
        listViewOfferedServices = findViewById(R.id.ListViewOfferedServices);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        dataref = FirebaseDatabase.getInstance().getReference().child("User").child(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        dataref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentUser = dataSnapshot.getValue(Clinic.class);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Error: ", "Firebase Failed");
            }
        });
        DatabaseReference serviceDataRef = dataref.child("servicesOffered");
        serviceDataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Service service = new Service();
                    service.setId(postSnapshot.child("id").getValue(String.class));
                    service.setName(postSnapshot.child("serviceName").getValue(String.class));
                    service.setRole(postSnapshot.child("serviceRole").getValue(String.class));
                    services.add(service);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listViewOfferedServices.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Service service = services.get(i);
                showUpdateDeleteDialog(service.getId(), service.getServiceName());
                return true;
            }
        });


    }

    private void showUpdateDeleteDialog(final String serviceId, String serviceName) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_clinic_delete_service, null);
        dialogBuilder.setView(dialogView);

        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteService);

        dialogBuilder.setTitle(serviceName);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //deleteService(serviceId);//serviceID
                b.dismiss();
            }
        });
    }


    /*
    private boolean deleteService(String id) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Service").child(id);
        dR.removeValue();
        Toast.makeText(getApplicationContext(), "Service Deleted", Toast.LENGTH_LONG).show();
        return true;
    }

     */



    protected void onStart(){
        super.onStart();
        dataref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Clinic_ServiceList servicesAdapter = new Clinic_ServiceList(Clinic_OfferedServices.this, services);
                listViewOfferedServices.setAdapter(servicesAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
