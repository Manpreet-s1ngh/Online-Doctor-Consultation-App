package com.example.doctorconsultantapp;

public class CategoryDetails
{
    String Category;
    int imgid;

    public CategoryDetails()
    {

    }

    public CategoryDetails(String category, int imgid) {
        Category = category;
        this.imgid = imgid;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public int getImgid() {
        return imgid;
    }

    public void setImgid(int imgid) {
        this.imgid = imgid;
    }
}
