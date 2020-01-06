package com.example.walkin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main_ChooseAccountType extends AppCompatActivity {
    private Button clinic;
    private Button patient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_account_type);

        clinic = findViewById(R.id.buttonClinic);
        patient = findViewById(R.id.buttonPatient);


        clinic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Main_ChooseAccountType.this , Main_SignUp.class);
                startActivity(myIntent); // Begin Activity
            }

        });

        patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Main_ChooseAccountType.this, User_Signup.class);
                startActivity(myIntent); // Begin Activity


            }
        });




        /**
         * Here you should add an onclick listener for the patient button to lead to a (different) sign up page
         * You can reuse most of the code form the normal sign up page
         */

    }
}
