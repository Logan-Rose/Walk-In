package com.example.walkin;
public class Service {
    private String name;
    private String role;
    private String id;

    public Service() {
        this.id = "";
        this.name = "00-00";
        this.role = ".";
    }

    public Service(String k, String n, String r){
        this.id = k;
        this.name = n;
        this.role = r;
    }


    public void setName(String n){
        this.name = n;
    }
    public void setRole(String r){
        this.role = r;
    }
    public void setId(String k){
        this.id = k;
    }
    public String getId(){
        return this.id;
    }
    public String getServiceName(){
        return this.name;
    }
    public String getServiceRole(){
        return this.role;
    }
    public boolean equals(Service other){
        return(this.id.equals(other.id) && this.name.equals(other.name) && this.role.equals(other.role));
    }
}

