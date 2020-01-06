package com.example.walkin;

public class Employee extends User {
    String clinic;
    public Employee() {
        super("", "", "", "");
    }
    public Employee(String id, String email, String name, String type) {
        super(id, email, name, type);
    }
    public Employee(String id, String email, String name, String type, String clinic) {
        super(id, email, name, type);
        this.clinic = clinic;
    }
    public void setClinic(String clinic){
        this.clinic = clinic;
    }
    public String getClinic() {
        return clinic;
    }
}
