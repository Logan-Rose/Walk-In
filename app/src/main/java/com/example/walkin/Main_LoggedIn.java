package com.example.walkin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Main_LoggedIn extends AppCompatActivity {
    private String name;
    private String password;
    private String uID;
    private DatabaseReference dataref;
    private Button signOut;
    private Button proceed;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_loggedin);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        dataref = FirebaseDatabase.getInstance().getReference().child("User").child(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        dataref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                TextView textView = (TextView) findViewById(R.id.welcome_name);
                if(user.getName() != null){
                    textView.setText(user.getName());
                }else{
                    textView.setText("User");
                }
                TextView newText = (TextView) findViewById(R.id.welcome_type);
                if(user.getType() != null){
                    newText.setText(user.getType());
                }else{
                    newText.setText("Type");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Error: ", "Firebase Failed");
            }
        });

        proceed = findViewById(R.id.Button_proceed);

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user.getType().equals("Admin")) {
                    Intent myIntent = new Intent(Main_LoggedIn.this, Admin_ActivityScreen.class);
                    startActivity(myIntent); // Begin Activity
                } else if (user.getType().equals("Clinic")) {
                    Intent myIntent = new Intent(Main_LoggedIn.this, Patient_searchClinics.class);
                    startActivity(myIntent); // Begin Activity
                }else if (user.getType().equals("Patient")) {
                    Intent myIntent = new Intent(Main_LoggedIn.this, Patient_ActivityScreen.class);
                    startActivity(myIntent); // Begin Activity
                }
            }
        });
    }
}
