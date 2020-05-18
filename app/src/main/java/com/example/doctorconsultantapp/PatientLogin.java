package com.example.doctorconsultantapp;

public class PatientLogin
{
    String UserName;
    String password;

    public PatientLogin()
    {

    }

    public PatientLogin(String userName, String password) {
        UserName = userName;
        this.password = password;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
