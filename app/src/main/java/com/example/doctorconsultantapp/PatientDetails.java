package com.example.doctorconsultantapp;

public class PatientDetails
{
    String FullName;
    String Email;
    String Password;
    String Phoneno;
    String Photo;
    String PatientKey;

    public PatientDetails()
    {

   }

    public PatientDetails(String fullName, String email, String password, String phoneno, String photo, String patientKey) {
        FullName = fullName;
        Email = email;
        Password = password;
        Phoneno = phoneno;
        Photo = photo;
        PatientKey = patientKey;
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

    public String getPhoneno() {
        return Phoneno;
    }

    public void setPhoneno(String phoneno) {
        Phoneno = phoneno;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getPatientKey() {
        return PatientKey;
    }

    public void setPatientKey(String patientKey) {
        PatientKey = patientKey;
    }
}
