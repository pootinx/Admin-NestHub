package com.example.admin_nesthub;

public class ReservationItem {
    private String userId;
    private String url_image;
    private String fullName;
    private String number;
    private String email;
    private String title;
    private String location;

    // Add a default (no-argument) constructor
    public ReservationItem() {
        // Default constructor required for Firebase
    }

    public ReservationItem(String url_image, String fullName, String number, String email, String title, String location) {
        this.url_image = url_image;
        this.fullName = fullName;
        this.number = number;
        this.email = email;
        this.title = title;
        this.location = location;
    }

    public String getUrl_image() {
        return url_image;
    }

    public void setUrl_image(String url_image) {
        this.url_image = url_image;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getnumber() {
        return number;
    }

    public void setnumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    // Constructor, getters, and setters
}
