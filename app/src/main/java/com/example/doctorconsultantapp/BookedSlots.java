package com.example.doctorconsultantapp;

class BookedSlots {
    String date;
    String start;
    String end;
    String finder; // date with start

    public BookedSlots() {
    }

    public BookedSlots(String date, String start, String end, String finder) {
        this.date = date;
        this.start = start;
        this.end = end;
        this.finder = finder;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getFinder() {
        return finder;
    }

    public void setFinder(String finder) {
        this.finder = finder;
    }
}
