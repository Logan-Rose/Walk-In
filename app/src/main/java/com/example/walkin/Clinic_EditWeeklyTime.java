package com.example.walkin;

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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class Clinic_EditWeeklyTime extends AppCompatActivity {

    TimePicker startTime;
    TimePicker endTime;
    EditText day;
    Button submit;
    private FirebaseAuth firebaseAuth;
    DatabaseReference dataref;
    DatabaseReference datarefUsers;

    List<Clinic_Availability> availabilities;
    Clinic_Availability availability;
    Clinic currentUser;

    ArrayList<Clinic_Availability> storedAvailabilities;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_weekly_time);
        submit = findViewById(R.id.submit);
        startTime = findViewById(R.id.pickStartTime);
        endTime = findViewById(R.id.pickEndTime);
        dataref = FirebaseDatabase.getInstance().getReference().child("Main_Service");

        datarefUsers = FirebaseDatabase.getInstance().getReference().child("User");

        storedAvailabilities =  new ArrayList<>();
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
                day = (EditText) findViewById(R.id.inputDay);
                final String weekday = day.getText().toString();
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
                final Clinic_Availability current = new Clinic_Availability(weekday, stringStart, stringEnd); // New time to be added

                if(!weekday.equalsIgnoreCase("sunday") && !weekday.equalsIgnoreCase("monday") && !weekday.equalsIgnoreCase("tuesday") && !weekday.equalsIgnoreCase("wednesday") && !weekday.equalsIgnoreCase("thursday") && !weekday.equalsIgnoreCase("friday") && !weekday.equalsIgnoreCase("saturday")){
                    Toast.makeText(Clinic_EditWeeklyTime.this, "Please enter a valid weekday and try again", Toast.LENGTH_SHORT).show();
                } else {
                    String userID = firebaseAuth.getCurrentUser().getUid();
                    DatabaseReference dR = FirebaseDatabase.getInstance().getReference("User");
                    DatabaseReference newUser = dR.child(userID);


//                    DatabaseReference daysLogged = newUser.child("weekly");
//                    daysLogged.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
//                                if(postSnapshot.child("day").getValue(String.class).toLowerCase() == weekday.toLowerCase()){
//                                    alreadyStored = true;
//                                }
//                                    avail.setStart(postSnapshot.child("start").getValue(String.class));
//
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });

//                    DatabaseReference availRef = newUser.child("weekly").child(weekday.toLowerCase()).child("availabilities");
//                    availRef.addListenerForSingleValueEvent(new ValueEventListener(){
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
//                                Clinic_Availability avail = new Clinic_Availability();
//                                avail.setStart(postSnapshot.child("start").getValue(String.class));
//                                avail.setEnd(postSnapshot.child("end").getValue(String.class));
//                                storedAvailabilities.add(avail);
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });


                    Daily_Availability toAdd = new Daily_Availability(weekday, stringStart, stringEnd, storedAvailabilities);
                    FirebaseDatabase.getInstance().getReference("User").child(firebaseAuth.getCurrentUser().getUid()).child("weekly").child(toAdd.getDay().toLowerCase()).setValue(toAdd);
//
//
//                    for(int i = 0; i< storedAvailabilities.size(); i++){
//                        FirebaseDatabase.getInstance().getReference("User").child(firebaseAuth.getCurrentUser().getUid()).child("weekly").child(toAdd.getDay().toLowerCase()).child(Integer.toString(i)).setValue(storedAvailabilities.get(i));
//                    }


                    Intent intent = new Intent(Clinic_EditWeeklyTime.this, Clinic_WeeklyViewAvailabilities.class);
                    startActivity(intent); // Begin Activity



                }
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
                    storedAvailabilities.add(availability);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
