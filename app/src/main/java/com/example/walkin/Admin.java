package com.example.walkin;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Admin extends AppCompatActivity {

    private String name;
    private String role;
    private FirebaseAuth firebaseAuth;
    private Button add;
    private TextView accountLabel;
    private EditText roleInput;
    private EditText nameInput;
    Service service;
    DatabaseReference dataref;
    ListView listViewServices;
    List<Service> services;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        dataref = FirebaseDatabase.getInstance().getReference().child("Service");
       	nameInput = findViewById(R.id.input_serviceName);
        roleInput = findViewById(R.id.input_serviceRole);
        add = findViewById(R.id.serviceSubmitButton);
        listViewServices = findViewById(R.id.listViewServices);

        services =  new ArrayList<>();
        service = new Service();
        firebaseAuth = FirebaseAuth.getInstance();

        add.setOnClickListener(new View.OnClickListener() {
        	@Override
        	public void onClick(View view){
                String name = nameInput.getText().toString();
                String role = roleInput.getText().toString();
                if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(role)){
                    service.setName(name);
                    String id =  dataref.push().getKey();
                    Service service = new Service(id, name, role);
                    dataref.child(id).setValue(service);
                }else{
                    Toast.makeText(getApplicationContext(), "Field Empty, please complete all fields", Toast.LENGTH_LONG).show();
                }
        	}
        } );

        listViewServices.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
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
        final View dialogView = inflater.inflate(R.layout.activity_update_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextName = (EditText) dialogView.findViewById(R.id.editTextName);
        final EditText editTextRole = (EditText) dialogView.findViewById(R.id.editTextRole);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateProduct);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteProduct);

        dialogBuilder.setTitle(serviceName);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString().trim();
                String role = editTextRole.getText().toString().trim();
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(role) ) {
                    updateService(serviceId, name, role);
                    b.dismiss();
                } else{
                    Toast.makeText(getApplicationContext(), "Field Empty, please complete all fields", Toast.LENGTH_LONG).show();
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteService(serviceId);//serviceID
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
                services.clear();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Service service = new Service();
                    service.setId(postSnapshot.child("id").getValue(String.class));
                    service.setName(postSnapshot.child("serviceName").getValue(String.class));
                    service.setRole(postSnapshot.child("serviceRole").getValue(String.class));
                    services.add(service);
                }
                Clinic_ServiceList servicesAdapter = new Clinic_ServiceList(Admin.this, services);
                listViewServices.setAdapter(servicesAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
