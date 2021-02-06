package com.example.doctorconsultantapp;

class leaveDetails {
    String d_id;
    String date;
    String idwithdate;

    public leaveDetails() {
    }

    public leaveDetails(String d_id, String date, String idwithdate) {
        this.d_id = d_id;
        this.date = date;
        this.idwithdate = idwithdate;
    }

    public String getD_id() {
        return d_id;
    }

    public void setD_id(String d_id) {
        this.d_id = d_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIdwithdate() {
        return idwithdate;
    }

    public void setIdwithdate(String idwithdate) {
        this.idwithdate = idwithdate;
    }
}
