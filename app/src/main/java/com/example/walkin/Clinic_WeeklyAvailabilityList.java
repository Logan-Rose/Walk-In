package com.example.walkin;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class Clinic_WeeklyAvailabilityList extends ArrayAdapter<Daily_Availability> {
    private Activity context;
    List<Daily_Availability> daysOfWeek;

    public Clinic_WeeklyAvailabilityList(Activity context, List<Daily_Availability> daysOfWeek) {
        super(context, R.layout.activity_weekly_availability_list, daysOfWeek);
        this.context = context;
        this.daysOfWeek = daysOfWeek;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_weekly_availability_list, null, true);

        TextView textViewDay =  listViewItem.findViewById(R.id.weeklyTextViewDay);
        TextView textViewStart =  listViewItem.findViewById(R.id.weeklyTextViewStart);
        TextView textViewEnd=  listViewItem.findViewById(R.id.weeklyTextViewEnd);

        Daily_Availability availability = daysOfWeek.get(position);
        textViewDay.setText(availability.getDay());
        textViewStart.setText(availability.getStart());
        textViewEnd.setText(availability.getEnd());
        return listViewItem;
    }
}

