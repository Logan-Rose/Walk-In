package com.example.walkin;

import java.util.ArrayList;

public class Patient extends User {
    ArrayList<String> appointments;
    public Patient(){}
    public Patient (String id, String email, String name, String type, ArrayList<String> appointments) {
        super(id, email, name, type);
        this.appointments = appointments;
    }
    public ArrayList<String> getAppointments(){
        return appointments;
    }
    public void addAppointment(String newAppointment){
        appointments.add(newAppointment);
    }
}
