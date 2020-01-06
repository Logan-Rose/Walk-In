package com.example.walkin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Patient_ActivityScreen extends AppCompatActivity {
    Button viewServices;
    Button searchClinics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient__screen);
        viewServices = findViewById(R.id.button_viewServices);
        viewServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Patient_ActivityScreen.this, Patient_viewServices.class);
                startActivity(myIntent); // Begin Activity
            }
        });
        searchClinics = findViewById(R.id.button_searchClinics);
        searchClinics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Patient_ActivityScreen.this, Patient_searchClinics.class);
                startActivity(myIntent); // Begin Activity
            }
        });
    }
}
