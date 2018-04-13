package com.example.sunkee.vehicletracker.model;

public class User {
    public String phoneNumber;
    public String name;


    public User(){

    }

    public User (String phoneNumber,String name){
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
}
