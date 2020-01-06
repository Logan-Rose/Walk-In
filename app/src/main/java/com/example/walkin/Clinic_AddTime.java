package com.example.walkin;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Clinic_AddTime extends AppCompatActivity {

    TimePicker startTime;
    TimePicker endTime;
    Button submit;
    private FirebaseAuth firebaseAuth;
    DatabaseReference dataref;
    DatabaseReference datarefUsers;

    List<Clinic_Availability> availabilities;
    Clinic_Availability availability;
    Clinic currentUser;
    Calendar cal;
    String sessionDate;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_clinic_time);
        submit = findViewById(R.id.submit);
        startTime = findViewById(R.id.pickStartTime);
        endTime = findViewById(R.id.pickEndTime);
        dataref = FirebaseDatabase.getInstance().getReference().child("Main_Service");

        datarefUsers = FirebaseDatabase.getInstance().getReference().child("User");

        Intent intenty = getIntent();
        sessionDate = intenty.getExtras().getString("date");

        availabilities =  new ArrayList<>();
        availability = new Clinic_Availability();
        firebaseAuth = FirebaseAuth.getInstance();
        dataref.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                currentUser = dataSnapshot.getValue(Clinic.class);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Error: ", "Firebase Failed");
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hour;
                String stringStart ="";
                String stringEnd = "";
                if(startTime.getHour() < 12){
                    if(startTime.getHour() != 0) {
                        stringStart = startTime.getHour() + " : " + startTime.getMinute() + " AM";
                    } else{
                        stringStart = "12 : " + startTime.getMinute() + " AM";
                    }
                } else {
                    stringStart = startTime.getHour() + " : " + startTime.getMinute() + " PM";
                }
                if(endTime.getHour() < 12){
                    if(endTime.getHour() != 0) {
                        stringEnd = endTime.getHour() + " : " + endTime.getMinute() + " AM";
                    } else{
                        stringEnd = "12 : " + endTime.getMinute() + " AM";
                    }
                } else {
                    stringEnd = endTime.getHour() + " : " + endTime.getMinute() + " PM";
                }
                final Clinic_Availability current = new Clinic_Availability( stringStart, stringEnd); // New time to be added


                String userID = firebaseAuth.getCurrentUser().getUid();
                DatabaseReference dR = FirebaseDatabase.getInstance().getReference("User");
                DatabaseReference newUser = dR.child(userID); // mark new position

                DatabaseReference availRef = newUser.child("weekly").child(sessionDate).child("availabilities");
                availRef.addListenerForSingleValueEvent(new ValueEventListener(){
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int i = 1;
                        for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                            Clinic_Availability avail = new Clinic_Availability();
                            avail.setStart(postSnapshot.child("start").getValue(String.class));
                            avail.setEnd(postSnapshot.child("end").getValue(String.class));
                            FirebaseDatabase.getInstance().getReference("User").child(firebaseAuth.getCurrentUser().getUid()).child("weekly").child(sessionDate).child("availabilities").child(Integer.toString(i)).setValue(avail);
                            Log.e("I ERROR", Integer.toString(i));
                            i++;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                Clinic_Availability toAdd = new Clinic_Availability(stringStart, stringEnd);
                FirebaseDatabase.getInstance().getReference("User").child(firebaseAuth.getCurrentUser().getUid()).child("weekly").child(sessionDate).child("availabilities").child(Integer.toString(0)).setValue(toAdd);

                Intent intent = new Intent(Clinic_AddTime.this, Clinic_ViewAvailabilities.class);
                intent.putExtra("date", sessionDate.toString());
                startActivity(intent); // Begin Activity




            }


        });
    }

    protected void onStart(){
        super.onStart();
        dataref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Clinic_Availability availability = new Clinic_Availability();
                    availability.setId(postSnapshot.child("id").getValue(String.class));
                    availability.setStart(postSnapshot.child("start").getValue(String.class));
                    availability.setEnd(postSnapshot.child("end").getValue(String.class));
                    availabilities.add(availability);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}

