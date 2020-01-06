package com.example.walkin;

import java.util.ArrayList;

public class Clinic{
    private Boolean licensed;
    private String address;
    private String description;
    private int totalServices;
    private ArrayList<Service> servicesOffered;
    private ArrayList<Clinic_Availability> schedule;
    private int totalRating;
    private int ratings;
    private int waitTime;
    private String name;
    private String employeeID;
    private int rating;
    public Clinic(){
        this.rating = 0;
        this.licensed = false;
        this.address = "";
        this.description = "";
        this.totalServices = 0;
        this.waitTime = 0;
        this.servicesOffered = new ArrayList<>();
        this.schedule = new ArrayList<>();
        this.totalRating = 0;
        this.ratings = 0;
    }
    public Clinic ( String name, boolean licensed, String address, String description){
        this.rating = 0;
        this.name = name;
        this.licensed = licensed;
        this.address = address;
        this.description = description;
        this.totalServices = 0;
        this.totalRating = 0;
        this.ratings = 0;
        this.waitTime = 0;
    }

    public boolean isLicensed() {
        return licensed;
    }

    public void setLicensed(boolean licensed) {
        this.licensed = licensed;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addService(Service service){
        this.servicesOffered.add(service);
        totalServices++;
    }

    public void removeService(Service service){
        int pos = -1;
        for(int i = 0; i < totalServices; i++){
            if(servicesOffered.get(i).equals(service))
                pos = i;
        }
        if(pos != -1)
            servicesOffered.remove(pos);
    }

    public void setServices(ArrayList<Service> newservices){this.servicesOffered = newservices; }

    public void setRatings(int i){
        this.ratings = i;
    }

    public int getRatings() {
        return ratings;
    }
    public int getTotalRating(){
        return this.totalRating;
    }
    public void setTotalRating(int i){
        if(ratings != 0){
            this.totalRating = i;
            this.rating = totalRating / ratings;
        } else{
            this.totalRating = i;
            this.rating = totalRating;
        }
    }

    public void addRating(int newRating){
        ratings++;
        totalRating = totalRating + newRating;
    }

    public int getRating(){
        return this.rating;
    }

    public int getWaitTime(){
        return waitTime;
    }

    public void setWaitTime(int i){
        this.waitTime = i;
            }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employee) {
        this.employeeID = employee;
    }
}
