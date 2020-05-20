package com.example.doctorconsultantapp;

public class ReviewDetails
{
    String ReviewId;
    String P_key;
    String D_key;
    String Rating;
    String Comment;
    String Date;
    Long Time;
    String D_Name;
    String Category;

    public ReviewDetails()
    {

    }

    public ReviewDetails(String reviewId, String p_key, String d_key, String rating, String comment, String date, Long time, String d_Name, String category) {
        ReviewId = reviewId;
        P_key = p_key;
        D_key = d_key;
        Rating = rating;
        Comment = comment;
        Date = date;
        Time = time;
        D_Name = d_Name;
        Category = category;
    }

    public String getReviewId() {
        return ReviewId;
    }

    public void setReviewId(String reviewId) {
        ReviewId = reviewId;
    }

    public String getP_key() {
        return P_key;
    }

    public void setP_key(String p_key) {
        P_key = p_key;
    }

    public String getD_key() {
        return D_key;
    }

    public void setD_key(String d_key) {
        D_key = d_key;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public Long getTime() {
        return Time;
    }

    public void setTime(Long time) {
        Time = time;
    }

    public String getD_Name() {
        return D_Name;
    }

    public void setD_Name(String d_Name) {
        D_Name = d_Name;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }
}
