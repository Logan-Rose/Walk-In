package com.example.walkin;

import android.app.ActionBar;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {
    private EditText emailInput;
    private String email;
    private EditText passwordInput;
    private String password;
    private FirebaseAuth firebaseAuth;
    private Button signup;
    private Button signin;
    private DatabaseReference dataref;
    private DatabaseReference datarefPatient;
    private DatabaseReference datarefEmployee;
    String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailInput = (EditText) findViewById(R.id.emailInput);
        passwordInput = (EditText) findViewById(R.id.InputPassword);
        signup = findViewById(R.id.Button_SignUp);
        signin = findViewById(R.id.Button_SignIn);
        email = emailInput.getText().toString();
        password = passwordInput.getText().toString();
        type = "";
        firebaseAuth = FirebaseAuth.getInstance();


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, Main_ChooseAccountType.class);
                startActivity(myIntent);
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signInWithEmailAndPassword(emailInput.getText().toString(), passwordInput.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            FirebaseAuth auth = FirebaseAuth.getInstance();
                            dataref = FirebaseDatabase.getInstance().getReference().child("User").child(auth.getUid());
                            dataref.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    User user = dataSnapshot.getValue(User.class);
                                    if(user.getType() != null){
                                        type = user.getType();
                                    }else{
                                        type = "Null";
                                    }

                                    if (type.equals("Admin")) {
                                        final String welcomeToast = "Welcome, " + user.getName() + "\nYou are logged in as an admin!";
                                        Toast.makeText(MainActivity.this, welcomeToast, Toast.LENGTH_LONG).show();
                                        Intent myIntent = new Intent(MainActivity.this, Admin_ActivityScreen.class);
                                        startActivity(myIntent); // Begin Activity
                                    } else if (type.equals("Employee")) {
                                        final String welcomeToast = "Welcome, " + user.getName() + "\nYou are logged in as an employee!";
                                        Toast.makeText(MainActivity.this, welcomeToast, Toast.LENGTH_LONG).show();
                                        Intent myIntent = new Intent(MainActivity.this, Clinic_ActivityScreen.class);
                                        startActivity(myIntent); // Begin Activity
                                    }else if (type.equals("Patient")) {
                                        final String welcomeToast = "Welcome, " + user.getName() + "\nYou are logged in as a patient!";
                                        Toast.makeText(MainActivity.this, welcomeToast, Toast.LENGTH_LONG).show();
                                        Intent myIntent = new Intent(MainActivity.this, Patient_searchClinics.class);
                                        startActivity(myIntent); // Begin Activity
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Log.e("Error: ", "Firebase Failed");
                                }
                            });

                        } else{
                            Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }
                });
            }
        });
    }
}
