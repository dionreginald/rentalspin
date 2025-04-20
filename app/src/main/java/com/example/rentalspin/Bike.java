package com.example.rentalspin;

import android.location.Location;

public class Bike {
    private String id; // Unique ID for the bike
    private String type; // Type of bike (e.g., "Standard", "Electric", "Cruiser")
    private Location location; // Latitude and longitude of the bike
    private String stationName; // Name of the station the bike belongs to
    private int imageResourceId; // Resource ID for the bike's image
    private String name; // Name of the bike (e.g., "City Rider", "Power Surge")
    private boolean isReserved; // Flag to indicate if the bike is reserved

    public Bike(String id, String type, double latitude, double longitude, String stationName, int imageResourceId, String name) {
        this.id = id;
        this.type = type;
        this.location = new Location("");
        this.location.setLatitude(latitude);
        this.location.setLongitude(longitude);
        this.stationName = stationName;
        this.imageResourceId = imageResourceId;
        this.name = name;
        this.isReserved = false; // Initially, all bikes are not reserved
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Location getLocation() {
        return location;
    }

    public String getStationName() {
        return stationName;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public String getName() {
        return name;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }

    // You might want to add setters for other properties if they can change
}