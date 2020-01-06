package com.example.walkin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Clinic_ChooseServices extends AppCompatActivity {
    DatabaseReference dataref;
    DatabaseReference datarefUsers;
    ListView listViewPossibleServices;
    List<Service> services;
    ArrayList<Service> userServices;
    private FirebaseAuth firebaseAuth;
    Service service;
    Clinic currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_view_services);

        dataref = FirebaseDatabase.getInstance().getReference().child("Service");

        datarefUsers = FirebaseDatabase.getInstance().getReference().child("User");

        listViewPossibleServices = findViewById(R.id.ListViewPossibleServices);

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

        listViewPossibleServices.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Service service = services.get(i);
                showUpdateDeleteDialog(service.getId(), service.getServiceName(), service.getServiceRole());
                return true;
            }
        });
    }

    private void showUpdateDeleteDialog(final String serviceId, String serviceName, String serviceRole) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_confirm_service, null);
        dialogBuilder.setView(dialogView);

        final Button buttonConfirm = (Button) dialogView.findViewById(R.id.buttonAddService);
        final Button buttonCancel = (Button) dialogView.findViewById(R.id.buttonCancel);

        final String newServiceId = serviceId;
        final String newServiceName = serviceName;
        final String newServiceRole = serviceRole;

        dialogBuilder.setTitle(serviceName);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = firebaseAuth.getCurrentUser().getUid();
                DatabaseReference dR = FirebaseDatabase.getInstance().getReference("User");
                DatabaseReference newUser = dR.child(userID); // Pull user's position
                DatabaseReference serviceDataRef = newUser.child("servicesOffered");
                serviceDataRef.addListenerForSingleValueEvent(new ValueEventListener(){
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int i = 1;
                        for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                            Service service = new Service();
                            service.setId(postSnapshot.child("id").getValue(String.class));
                            service.setName(postSnapshot.child("serviceName").getValue(String.class));
                            service.setRole(postSnapshot.child("serviceRole").getValue(String.class));
                            FirebaseDatabase.getInstance().getReference("User").child(firebaseAuth.getCurrentUser().getUid()).child("servicesOffered").child(Integer.toString(i)).setValue(service);
                            i++;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                Service aboutToAdd = new Service();
                aboutToAdd.setId(newServiceId);
                aboutToAdd.setName(newServiceName);
                aboutToAdd.setRole(newServiceRole);
                FirebaseDatabase.getInstance().getReference("User").child(firebaseAuth.getCurrentUser().getUid()).child("servicesOffered").child(Integer.toString(0)).setValue(aboutToAdd);
                b.dismiss();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss();
            }
        });

    }

    private void updateService(String id, String name, String role) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Service").child(id);
        Service service = new Service(id, name, role);
        dR.setValue(service);
        Toast.makeText(getApplicationContext(), "Service Updated", Toast.LENGTH_LONG).show();
    }

    private boolean deleteService(String id) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Service").child(id);
        dR.removeValue();
        Toast.makeText(getApplicationContext(), "Service Deleted", Toast.LENGTH_LONG).show();
        return true;
    }

    protected void onStart(){
        super.onStart();
        dataref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Service service = new Service();

                    service.setId(postSnapshot.child("id").getValue(String.class));
                    service.setName(postSnapshot.child("serviceName").getValue(String.class));
                    service.setRole(postSnapshot.child("serviceRole").getValue(String.class));
                    services.add(service);
                }

                Clinic_ServiceList servicesAdapter = new Clinic_ServiceList(Clinic_ChooseServices.this, services);
                listViewPossibleServices.setAdapter(servicesAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
