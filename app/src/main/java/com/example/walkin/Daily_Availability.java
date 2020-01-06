package com.example.walkin;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Daily_Availability {
    private ArrayList<Clinic_Availability> availabiltiies;
    private String day;
    private String start;
    private String end;
    private String id;


    public Daily_Availability(){
        availabiltiies = new ArrayList<>();
    }
    public Daily_Availability(String days, String starts, String ends, ArrayList<Clinic_Availability> avail){
        this.day = days;
        this.start = starts;
        this.end = ends;
        this.availabiltiies = avail;
    }
    public Daily_Availability(ArrayList<Clinic_Availability> preDet){
        availabiltiies = preDet;
    }

    public void setDay(String newDay){this.day = newDay;}
    public void setId(String newID){this.id = newID; }
    public void setStart(String newStart){this.start = newStart; }
    public void setEnd(String newEnd){this.end = newEnd;}

    public String getDay (){ return this.day;}
    public String getStart (){ return this.start;}
    public String getEnd ( ){ return this.end;}
    public String getId ( ){ return this.id;}



    public void setAvailabiltiies(ArrayList<Clinic_Availability> newset){
        availabiltiies = newset;
    }
    public ArrayList<Clinic_Availability> getAvailabiltiies(){
        return availabiltiies;
    }
    public void addAvailability(Clinic_Availability newAv){
        availabiltiies.add(newAv);
    }
}
