package com.example.walkin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class User_Signup extends AppCompatActivity {
    private EditText emailInput;
    private String email;
    private EditText passwordInput;
    private String password;
    private EditText nameInput;
    private String name;
    private String type;
    private FirebaseAuth firebaseAuth;
    private Button submit;
    private TextView accountLabel;
    private LinearLayout mainInput;
    private boolean licensed;
    private EditText addressInput;
    private Boolean Licensed;
    private EditText descriptionInput;
    private String description;
    private String address;

    Patient user;
    DatabaseReference dataref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_up);

        emailInput = findViewById(R.id.input_email);
        passwordInput = findViewById(R.id.input_password);
        nameInput = findViewById(R.id.input_name);
        submit = findViewById(R.id.submitButton);
        mainInput = findViewById(R.id.inputFields);


        user = new Patient();
        dataref = FirebaseDatabase.getInstance().getReference().child("User");
        firebaseAuth = FirebaseAuth.getInstance();


        type = "Patient";
        email = emailInput.getText().toString();
        password = passwordInput.getText().toString();
        name = nameInput.getText().toString();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                user.setName(nameInput.getText().toString());
                user.setEmail(emailInput.getText().toString());
                user.setType(type);
                user.setPassword(passwordInput.getText().toString());

                firebaseAuth.createUserWithEmailAndPassword(emailInput.getText().toString(), passwordInput.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            final String userID = firebaseAuth.getCurrentUser().getUid();
                            DatabaseReference newUser = dataref.child(userID); // mark new position
                            newUser.setValue(user);
                            user.setId(userID); //adds the id in the database as an instance variable ror the user
                            newUser.setValue(user); // updates the user in the db to nwo contain the id
                            UserProfileChangeRequest addkey = new UserProfileChangeRequest.Builder().setDisplayName(userID).build(); //Begin change of display name, changing it to the storage name of its object
                            FirebaseUser toUpdate = FirebaseAuth.getInstance().getCurrentUser(); // Choose newly made account
                            toUpdate.updateProfile(addkey); // change display name
                            Intent myIntent = new Intent(User_Signup.this,Patient_searchClinics.class); //Head back to main login
                            startActivity(myIntent); // execute
                        } else{
                            Toast.makeText(User_Signup.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }


                    }
                });

            }
        });
    }
}
