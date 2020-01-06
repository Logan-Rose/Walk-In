package com.example.walkin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Clinic_ActivityScreen extends AppCompatActivity {

    private Button servicesButton;
    private Button accountButton;
    private Button scheduleButton;
    private Button viewServicesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic__screen);

        servicesButton = findViewById(R.id.buttonServices);
        viewServicesButton = findViewById(R.id.buttonViewServices);
        scheduleButton = findViewById(R.id.buttonSchedule);

        servicesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Clinic_ActivityScreen.this, Clinic_ChooseServices.class);
                startActivity(intent); // Begin Activity
            }
        });

        viewServicesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Clinic_ActivityScreen.this, Clinic_OfferedServices.class);
                startActivity(intent); // Begin Activity
            }
        });


        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Clinic_ActivityScreen.this, Clinic_WeeklyViewAvailabilities.class);
                startActivity(intent); // Begin Activity
            }
        });

    }

}
