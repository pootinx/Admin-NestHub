package com.example.admin_nesthub.models;


public class UserModel {
    private String userId;  // Add this field
    private String fullName;
    private String email;
    private String number;

    public UserModel() {
        // Default constructor required for Firebase
    }

    public UserModel(String fullName, String email,String userId, String number) {
        this.fullName = fullName;
        this.email = email;
        this.number = number;
        this.userId = userId;
    }

    public String getfullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getNumber() {
        return number;
    }
    public String getUserId() {
        return userId;
    }


}
