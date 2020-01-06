package com.example.walkin;

/**
 * Adapted from Code provided for lab 5
 */


import android.app.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class Patient_ClinicList extends ArrayAdapter<Clinic> {
    private Activity context;
    List<Clinic> clinics;

    public Patient_ClinicList(Activity context, List<Clinic> clinics) {
        super(context, R.layout.activity_patient_clinic_list, clinics);
        this.context = context;
        this.clinics = clinics;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_patient_clinic_list, null, true);


        TextView textViewName = /*(TextView)*/ listViewItem.findViewById(R.id.textViewName);
        TextView textViewRole = /*(TextView)*/ listViewItem.findViewById(R.id.textViewAddress);
        TextView textViewRating = /*(TextView)*/ listViewItem.findViewById(R.id.textViewRating);
        TextView textViewWaitTime = /*(TextView)*/ listViewItem.findViewById(R.id.textViewWaitTime);

        Clinic clinic = clinics.get(position);
        int rating = clinic.getRating();
        String stringRating = rating + " /5";
        textViewRating.setText(stringRating);
        int wait = clinic.getWaitTime();
        String stringWait = wait + " minutes";
        textViewWaitTime.setText(stringWait);
        textViewName.setText(clinic.getName());
        textViewRole.setText(String.valueOf(clinic.getAddress()));
        return listViewItem;
    }
}