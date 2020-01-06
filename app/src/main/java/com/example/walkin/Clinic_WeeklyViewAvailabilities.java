package com.example.walkin;

import android.content.Intent;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Clinic_WeeklyViewAvailabilities extends AppCompatActivity {
    DatabaseReference datarefUsers;
    DatabaseReference dataref;
    ListView listViewAvailability;
    List<Daily_Availability> daysOfWeek;
    private FirebaseAuth firebaseAuth;
    private Clinic currentUser;
    Button editDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_view_weekly_avalibilities);
        listViewAvailability = findViewById(R.id.weeklyListViewAvailability);
        editDate = findViewById(R.id.buttonEditDate);
        daysOfWeek =  new ArrayList<>();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        dataref = FirebaseDatabase.getInstance().getReference().child("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
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
        DatabaseReference availDataRef = dataref.child("weekly");
        availDataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Daily_Availability avail = new Daily_Availability();
                    avail.setDay(postSnapshot.child("day").getValue(String.class));
                    avail.setStart(postSnapshot.child("start").getValue(String.class));
                    avail.setEnd(postSnapshot.child("end").getValue(String.class));
                    daysOfWeek.add(avail);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Clinic_WeeklyViewAvailabilities.this, Clinic_EditWeeklyTime.class);
                startActivity(intent); // Begin Activity
            }
        });


        listViewAvailability.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(daysOfWeek.get(i).getDay() == null){
                    Toast.makeText(Clinic_WeeklyViewAvailabilities.this, "Date Not Setup. Please Add Open/Close Times.", Toast.LENGTH_SHORT).show();
                }else{
                    showTimeSlots(daysOfWeek.get(i).getDay().toLowerCase());
                    return true;
                }
                return false;
            }
        });
    }

    private void showTimeSlots(String dayOfWeek) {
        Intent intent = new Intent(Clinic_WeeklyViewAvailabilities.this, Clinic_ViewAvailabilities.class);
        intent.putExtra("date", dayOfWeek.toString());
        startActivity(intent); // Begin Activity
    }



    protected void onStart(){
        super.onStart();
        dataref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Daily_Availability avail = new Daily_Availability();
                    avail.setDay(postSnapshot.child("day").getValue(String.class));
                    avail.setStart(postSnapshot.child("start").getValue(String.class));
                    avail.setEnd(postSnapshot.child("end").getValue(String.class));
                    daysOfWeek.add(avail);
                }

                Clinic_WeeklyAvailabilityList availabilitiesAdapter = new Clinic_WeeklyAvailabilityList(Clinic_WeeklyViewAvailabilities.this, daysOfWeek);
                listViewAvailability.setAdapter(availabilitiesAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}