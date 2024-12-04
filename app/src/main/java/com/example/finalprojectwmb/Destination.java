package com.example.finalprojectwmb;

public class Destination {
    private String id;
    private String name;
    private int imageResource;

    public Destination(String id, String name, int imageResource) {
        this.id = id;
        this.name = name;
        this.imageResource = imageResource;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getImageResource() {
        return imageResource;
    }
}

