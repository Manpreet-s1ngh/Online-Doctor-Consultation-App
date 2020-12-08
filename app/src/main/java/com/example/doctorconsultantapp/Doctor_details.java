package com.example.doctorconsultantapp;

import android.graphics.Insets;

public class Doctor_details
{
     String  FullName;
     String  Email;
     String Password;
     String category;
     String PhoneNo;
     String BasicFees;
     String Experience;
     String Service;
     String StartHour;
     String EndHour;
     String imagepath;
     String d_key;
     String status;
     String address ="";

   public Doctor_details()
    {

    }

    public Doctor_details(String fullName, String email, String password, String category, String phoneNo, String basicFees, String experience, String service, String startHour, String endHour, String imagepath, String d_key, String status,String address) {
        FullName = fullName;
        Email = email;
        Password = password;
        this.category = category;
        PhoneNo = phoneNo;
        BasicFees = basicFees;
        Experience = experience;
        Service = service;
        StartHour = startHour;
        EndHour = endHour;
        this.imagepath = imagepath;
        this.d_key = d_key;
        this.status = status;
        this.address = address;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    public String getBasicFees() {
        return BasicFees;
    }

    public void setBasicFees(String basicFees) {
        BasicFees = basicFees;
    }

    public String getExperience() {
        return Experience;
    }

    public void setExperience(String experience) {
        Experience = experience;
    }

    public String getService() {
        return Service;
    }

    public void setService(String service) {
        Service = service;
    }

    public String getStartHour() {
        return StartHour;
    }

    public void setStartHour(String startHour) {
        StartHour = startHour;
    }

    public String getEndHour() {
        return EndHour;
    }

    public void setEndHour(String endHour) {
        EndHour = endHour;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public String getD_key() {
        return d_key;
    }

    public void setD_key(String d_key) {
        this.d_key = d_key;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
