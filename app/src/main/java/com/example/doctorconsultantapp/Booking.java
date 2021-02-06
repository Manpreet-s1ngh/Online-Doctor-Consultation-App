package com.example.doctorconsultantapp;

public class Booking implements Comparable
{
    String P_Key,P_Name,P_Mobile;
    String SlotDay,Start,End;
    String DoctorId,Name,Category,PhoneNo;
    String Problem,Status,Date,BookingKey;

    public Booking()
    {

    }

    public Booking(String p_Key, String p_Name, String p_Mobile, String slotDay, String start, String end, String doctorId, String name, String category, String phoneNo, String problem, String status, String date, String bookingKey) {
        P_Key = p_Key;
        P_Name = p_Name;
        P_Mobile = p_Mobile;
        SlotDay = slotDay;
        Start = start;
        End = end;
        DoctorId = doctorId;
        Name = name;
        Category = category;
        PhoneNo = phoneNo;
        Problem = problem;
        Status = status;
        Date = date;
        BookingKey = bookingKey;
    }

    public String getP_Key() {
        return P_Key;
    }

    public void setP_Key(String p_Key) {
        P_Key = p_Key;
    }

    public String getP_Name() {
        return P_Name;
    }

    public void setP_Name(String p_Name) {
        P_Name = p_Name;
    }

    public String getP_Mobile() {
        return P_Mobile;
    }

    public void setP_Mobile(String p_Mobile) {
        P_Mobile = p_Mobile;
    }

    public String getSlotDay() {
        return SlotDay;
    }

    public void setSlotDay(String slotDay) {
        SlotDay = slotDay;
    }

    public String getStart() {
        return Start;
    }

    public void setStart(String start) {
        Start = start;
    }

    public String getEnd() {
        return End;
    }

    public void setEnd(String end) {
        End = end;
    }

    public String getDoctorId() {
        return DoctorId;
    }

    public void setDoctorId(String doctorId) {
        DoctorId = doctorId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
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

    public String getProblem() {
        return Problem;
    }

    public void setProblem(String problem) {
        Problem = problem;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getBookingKey() {
        return BookingKey;
    }

    public void setBookingKey(String bookingKey) {
        BookingKey = bookingKey;
    }

   /* public int compareTo(Booking o) {
        return this.getStart().compareTo(o.getStart());
    }*/

    @Override
    public int compareTo(Object o) {
        Booking o2  = (Booking) o;
        return this.getStart().compareTo(o2.getStart());
       // return 0;
    }

}
