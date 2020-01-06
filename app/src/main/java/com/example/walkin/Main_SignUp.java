package com.example.walkin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Main_SignUp extends AppCompatActivity {
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
    private boolean cardtaken;
    private boolean cashtaken;
    private Clinic clinic;

    private Employee user;
    DatabaseReference dataref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        emailInput = findViewById(R.id.input_email);
        passwordInput = findViewById(R.id.input_password);
        nameInput = findViewById(R.id.inputName);
        addressInput = findViewById(R.id.inputAddress);
        descriptionInput = findViewById(R.id.inputDesc);
        submit = findViewById(R.id.submitButton);
        mainInput = findViewById(R.id.inputFields);

        user = new Employee();
        clinic = new Clinic();
        type = "Employee";
        email = emailInput.getText().toString();
        password = passwordInput.getText().toString();
        name = nameInput.getText().toString();
        description = descriptionInput.getText().toString();
        address = addressInput.getText().toString();


        final Switch licenseSwitch = findViewById(R.id.licensed);

        licenseSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    licensed = true;

                } else {
                    licensed = false;
                }
            }
        });

        final Switch cashswitch = findViewById(R.id.cashswitch);

        licenseSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cashtaken = true;
                } else {
                    cashtaken = false;
                }
            }
        });

        final Switch cardswitch = findViewById(R.id.cardswitch);

        licenseSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cardtaken = true;
                } else {
                    cardtaken = false;
                }
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dataref = FirebaseDatabase.getInstance().getReference();
                firebaseAuth = FirebaseAuth.getInstance();
                user.setName(nameInput.getText().toString());
                user.setEmail(emailInput.getText().toString());
                user.setType(type);
                user.setPassword(passwordInput.getText().toString());
                user.setClinic(nameInput.getText().toString());
                clinic.setName(nameInput.getText().toString());
                clinic.setAddress(addressInput.getText().toString());
                clinic.setDescription(descriptionInput.getText().toString());
                clinic.setLicensed(licensed);
                firebaseAuth.createUserWithEmailAndPassword(emailInput.getText().toString(), passwordInput.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            final String userID = firebaseAuth.getCurrentUser().getUid();
                            DatabaseReference newUser = dataref.child("User").child(userID); // mark new position
                            newUser.setValue(user);
                            user.setId(userID); //adds the id in the database as an instance variable ror the user
                            newUser.setValue(user); // updates the user in the db to nwo contain the id
                            UserProfileChangeRequest addkey = new UserProfileChangeRequest.Builder().setDisplayName(userID).build(); //Begin change of display name, changing it to the storage name of its object
                            FirebaseUser toUpdate = FirebaseAuth.getInstance().getCurrentUser(); // Choose newly made account
                            toUpdate.updateProfile(addkey); // change display name
                            clinic.setEmployeeID(userID);
                            DatabaseReference newClinic = dataref.child("Clinic").child(nameInput.getText().toString()); // mark new position
                            newClinic.setValue(clinic);

                            Intent myIntent = new Intent(Main_SignUp.this,Clinic_ActivityScreen.class); //Head back to main login
                            startActivity(myIntent); // execute
                        } else{
                            Toast.makeText(Main_SignUp.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }


                    }
                });
            }
        });
    }
}
