package com.example.walkin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Clinic_ViewAvailabilities extends AppCompatActivity {
    DatabaseReference datarefUsers;
    DatabaseReference dataref;
    ListView listViewAvailability;
    List<Clinic_Availability> availibities;
    private FirebaseAuth firebaseAuth;
    private Clinic currentUser;
    private Button addService;
    private String sessionDate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_view_availabilities);
        listViewAvailability = findViewById(R.id.listViewAvailability);
        addService = findViewById(R.id.buttonAddSchedule);
        availibities =  new ArrayList<>();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        dataref = FirebaseDatabase.getInstance().getReference().child("User").child(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

        Intent intent = getIntent();
        sessionDate = intent.getExtras().getString("date");

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

        addService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Clinic_ViewAvailabilities.this, Clinic_AddTime.class);
                intent.putExtra("date", sessionDate.toString());
                startActivity(intent); // Begin Activity
            }
        });
        ListView listViewUsers = findViewById(R.id.listViewAvailability);

        listViewUsers.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                showDeleteDialogAvailability(i);
                return true;
            }
        });
    }



    private void showDeleteDialogAvailability(int i) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final int o = i;
        final View dialogView = inflater.inflate(R.layout.activity_update_availability, null);
        final Button buttonCancel = dialogView.findViewById(R.id.buttonCancelAvailability);
        final Button buttonDelete = dialogView.findViewById(R.id.buttonDeleteAvailability);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("Delete?");

        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteActivity(o);//serviceID
                b.dismiss();
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.dismiss();
            }
        });
    }

    private boolean deleteActivity(int i){

        for(int u = i; u + 1 < availibities.size(); u++){
            FirebaseDatabase.getInstance().getReference().child("User").child(FirebaseAuth.getInstance().getCurrentUser().getDisplayName()).child("weekly").child(sessionDate).child("availabilities").child(Integer.toString(u)).setValue(availibities.get(u+1));
        }
        FirebaseDatabase.getInstance().getReference().child("User").child(FirebaseAuth.getInstance().getCurrentUser().getDisplayName()).child("weekly").child(sessionDate).child("availabilities").child(Integer.toString(availibities.size() - 1)).removeValue();
        return true;
    }






    protected void onStart(){
        super.onStart();
        dataref.child("weekly").child(sessionDate).child("availabilities").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                availibities = new ArrayList<>();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Clinic_Availability avail = new Clinic_Availability();
                    avail.setStart(postSnapshot.child("start").getValue(String.class));
                    avail.setEnd(postSnapshot.child("end").getValue(String.class));
                    availibities.add(avail);
                }
                Clinic_AvailabilityList availabilitiesAdapter = new Clinic_AvailabilityList(Clinic_ViewAvailabilities.this, availibities);
                listViewAvailability.setAdapter(availabilitiesAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
