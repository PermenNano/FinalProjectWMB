package com.example.finalprojectwmb;

public class TravelPackage {
    private String title;
    private String price;
    private String details;
    private int imageResource;

    public TravelPackage(String title, String price, String details, int imageResource) {
        this.title = title;
        this.price = price;
        this.details = details;
        this.imageResource = imageResource;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getDetails() {
        return details;
    }

    public int getImageResource() {
        return imageResource;
    }
}
