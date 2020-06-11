package com.example.taskmassages.classes;

public class User {
    private String fullName;
    private String phoneNumber;
    private String email;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public User() {
    }

    public User(String fullName, String phoneNumber,String email) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email=email;
    }
}
