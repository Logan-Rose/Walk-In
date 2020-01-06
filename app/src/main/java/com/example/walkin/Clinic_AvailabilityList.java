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

public class Clinic_AvailabilityList extends ArrayAdapter<Clinic_Availability> {
    private Activity context;
    List<Clinic_Availability> availabilityList;

    public Clinic_AvailabilityList(Activity context, List<Clinic_Availability> availabilityList) {
        super(context, R.layout.activity_availability_list, availabilityList);
        this.context = context;
        this.availabilityList = availabilityList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_availability_list, null, true);

        TextView textViewStart =  listViewItem.findViewById(R.id.textViewStart);
        TextView textViewEnd=  listViewItem.findViewById(R.id.textViewEnd);

        Clinic_Availability availability = availabilityList.get(position);
        textViewStart.setText(availability.getStart());
        textViewEnd.setText(availability.getEnd());
        return listViewItem;
    }
}