package com.example.finalprojectwmb;

public class TravelApplication {
    private String name;
    private String email;
    private String phone;
    private String userId; // Add this field

    // Default constructor (required for Firestore)
    public TravelApplication() {
    }

    // Constructor with parameters
    public TravelApplication(String name, String email, String phone, String userId) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.userId = userId; // Initialize userId
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getUserId() {
        return userId;
    }

}
