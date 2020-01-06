package com.example.walkin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Patient_searchClinics extends AppCompatActivity {

    private String name;
    private String role;
    private FirebaseAuth firebaseAuth;
    private Button add;
    private TextView accountLabel;
    private EditText typeInput;
    private EditText nameInput;
    User user;
    ArrayList<String> appointments;
    Clinic clinic;
    DatabaseReference dataref;
    DatabaseReference datarefUser;
    DatabaseReference datarefAppointments;
    ListView listViewClinics;
    List<Clinic> clinics;
    ImageButton Search;
    TextView keyInput;
    String key;
    String stringRating;
    EditText inputRating;
    int intRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_clinics);

        dataref = FirebaseDatabase.getInstance().getReference().child("Clinic");
        //datarefUser = FirebaseDatabase.getInstance().getReference().child("User").child(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        listViewClinics = findViewById(R.id.listClinics);
        Search = findViewById(R.id.Search);
        clinic = new Clinic();
        clinics =  new ArrayList<>();
        user = new User();
        firebaseAuth = FirebaseAuth.getInstance();
        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyInput = findViewById(R.id.searchBar);
                key = keyInput.getText().toString();
                dataref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        clinics.clear();
                        for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                            clinic = postSnapshot.getValue(Clinic.class);
                            if (clinic.getName().toLowerCase().contains(key.toLowerCase()))
                                clinics.add(clinic);
                        }
                        if(clinics.size() ==0)
                            Toast.makeText(Patient_searchClinics.this, "Your search found no clinics, \ntry searching using less key words", Toast.LENGTH_LONG).show();
                        Patient_ClinicList clinicsAdapter = new Patient_ClinicList(Patient_searchClinics.this, clinics);
                        listViewClinics.setAdapter(clinicsAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        listViewClinics.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Clinic clinic = clinics.get(i);
                showBookRateDialog(clinic);
                return true;
            }
        });

    }

    private void showBookRateDialog(final Clinic clinic) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_patient__book_rate, null);
        final Button buttonBook = dialogView.findViewById(R.id.button_book);
        final Button buttonRate = dialogView.findViewById(R.id.button_rate);
        appointments = new ArrayList<String>();
        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle(clinic.getName());

        final AlertDialog b = dialogBuilder.create();
        b.show();
        buttonBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String patientWaitTime =  "" +clinic.getWaitTime();
                clinic.setWaitTime(clinic.getWaitTime() + 15);
                datarefAppointments = FirebaseDatabase.getInstance().getReference().child("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("appointments");
                datarefAppointments.setValue(clinic);
                Toast.makeText(getApplicationContext(), "You have been added to the wait list! You will be seen in " + patientWaitTime + " minutes!", Toast.LENGTH_LONG).show();

                updateClinic(clinic);
                b.dismiss();
            }
        });

        buttonRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputRating = (EditText) dialogView.findViewById(R.id.RatingText);
                stringRating = inputRating.getText().toString();
                if(stringRating.equals("1")){
                    clinic.setTotalRating(clinic.getTotalRating() + 2);
                    clinic.setRatings(clinic.getRatings() + 1);
                    Toast.makeText(getApplicationContext(), "Your Rating has been submitted", Toast.LENGTH_LONG).show();
                }else if(stringRating.equals("2")){
                    clinic.setTotalRating(clinic.getTotalRating() + 2);
                    clinic.setRatings(clinic.getRatings() + 1);
                    Toast.makeText(getApplicationContext(), "Your Rating has been submitted", Toast.LENGTH_LONG).show();
                } else if(stringRating.equals("3")){
                    clinic.setTotalRating(clinic.getTotalRating() + 3);
                    clinic.setRatings(clinic.getRatings() + 1);
                    Toast.makeText(getApplicationContext(), "Your Rating has been submitted", Toast.LENGTH_LONG).show();
                } else if(stringRating.equals("4")){
                    clinic.setTotalRating(clinic.getTotalRating() + 4);
                    clinic.setRatings(clinic.getRatings() + 1);
                    Toast.makeText(getApplicationContext(), "Your Rating has been submitted", Toast.LENGTH_LONG).show();
                } else if(stringRating.equals("5")){
                    clinic.setTotalRating(clinic.getTotalRating() + 5);
                    clinic.setRatings(clinic.getRatings() + 1);
                    Toast.makeText(getApplicationContext(),""+ clinic.getRating(), Toast.LENGTH_LONG).show();
                } else{
                    Toast.makeText(getApplicationContext(), stringRating, Toast.LENGTH_LONG).show();
                }
                updateClinic(clinic);
                b.dismiss();
            }
        });
    }

    private void updateClinic(Clinic clinic) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Clinic").child(clinic.getName());
        dR.setValue(clinic);
    }
}

