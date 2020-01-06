package com.example.walkin;

import java.util.ArrayList;

public class User {
    private String name;
    private String email;
    private String password;
    private String type;
    private String id;
    private boolean licensed;
    private String address;
    private String description;
    private ArrayList<Service> servicesOffered;
    private int totalServices;

    public User() {    }

    public User(String id, String email, String name, String type){
        this.email = email;
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id ) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}