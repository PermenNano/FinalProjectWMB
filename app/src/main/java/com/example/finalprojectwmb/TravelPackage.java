package com.example.finalprojectwmb;

public class TravelPackage {
    private String name;
    private String price;
    private String details;
    private int imageResource; // Add this line for the image resource

    public TravelPackage(String name, String price, String details, int imageResource) {
        this.name = name;
        this.price = price;
        this.details = details;
        this.imageResource = imageResource; // Initialize the image resource
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getDetails() {
        return details;
    }

    public int getImageResource() { // Add this getter method
        return imageResource;
    }
}
