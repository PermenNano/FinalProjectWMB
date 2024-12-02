package com.example.finalprojectwmb;

public class Destination {
    private String id;
    private String name;
    private int imageResId;

    public Destination(String id, String name, int imageResId) {
        this.id = id;
        this.name = name;
        this.imageResId = imageResId;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public int getImageResId() { return imageResId; }
}
