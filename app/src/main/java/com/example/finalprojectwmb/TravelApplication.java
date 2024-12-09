package com.example.finalprojectwmb;

public class TravelApplication {
    private String name;
    private String email;
    private String phone;
    private String userId;

    public TravelApplication() {
    }

    public TravelApplication(String name, String email, String phone, String userId) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.userId = userId;
    }

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
