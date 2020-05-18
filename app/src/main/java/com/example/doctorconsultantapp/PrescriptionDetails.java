package com.example.doctorconsultantapp;

public class PrescriptionDetails
{
   String P_Key;
   String D_Key;
   String Title;
   String Details;
   String Type;
   String PicPath;
   String DoctorName;
   String Category;
   String PhoneNo;
    String prescription_Key;

    public PrescriptionDetails()
    {

    }

    public PrescriptionDetails(String p_Key, String d_Key, String title, String details, String type, String picPath, String doctorName, String category, String phoneNo, String prescription_Key) {
        P_Key = p_Key;
        D_Key = d_Key;
        Title = title;
        Details = details;
        Type = type;
        PicPath = picPath;
        DoctorName = doctorName;
        Category = category;
        PhoneNo = phoneNo;
        this.prescription_Key = prescription_Key;
    }

    public String getP_Key() {
        return P_Key;
    }

    public void setP_Key(String p_Key) {
        P_Key = p_Key;
    }

    public String getD_Key() {
        return D_Key;
    }

    public void setD_Key(String d_Key) {
        D_Key = d_Key;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        Details = details;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getPicPath() {
        return PicPath;
    }

    public void setPicPath(String picPath) {
        PicPath = picPath;
    }

    public String getDoctorName() {
        return DoctorName;
    }

    public void setDoctorName(String doctorName) {
        DoctorName = doctorName;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    public String getPrescription_Key() {
        return prescription_Key;
    }

    public void setPrescription_Key(String prescription_Key) {
        this.prescription_Key = prescription_Key;
    }
}
