package com.example.doctorconsultantapp;

public class GalleryDetails
{
    String image;
    String caption;
    String galleryid;


  public  GalleryDetails()
    {

    }

    public GalleryDetails(String image, String caption, String galleryid) {
        this.image = image;
        this.caption = caption;
        this.galleryid = galleryid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getGalleryid() {
        return galleryid;
    }

    public void setGalleryid(String galleryid) {
        this.galleryid = galleryid;
    }
}
