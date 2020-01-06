package com.example.walkin;

import java.sql.Date;
import java.sql.Time;

public class Clinic_Availability {
    private String start;
    private String end;
    private String id;
    private boolean booked;
    public Clinic_Availability(){
    }
    public Clinic_Availability(String start, String end){

        this.start = start;
        this.end = end;
        this.booked = false;
    }
    public Clinic_Availability(String start, String end, String id){
        this.id = id;
        this.start = start;
        this.end = end;
        this.booked = false;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBooked( boolean newBooked){ this.booked = newBooked;}

    public boolean getBooked (){ return booked;}

}
