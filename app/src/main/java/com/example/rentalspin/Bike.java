package com.example.rentalspin;

import android.location.Location;

public class Bike {
    private String id;
    private String type;
    private boolean isReserved;
    private String stationName;
    private int imageResourceId;
    private String name;
    private Location location; // Add this field

    // Existing constructor
    public Bike(String id, String type, boolean isReserved, String stationName, int imageResourceId, String name) {
        this.id = id;
        this.type = type;
        this.isReserved = isReserved;
        this.stationName = stationName;
        this.imageResourceId = imageResourceId;
        this.name = name;
        this.location = null; // Initialize location, you'll set it later
    }

    // New constructor that includes location
    public Bike(String id, String type, boolean isReserved, String stationName, int imageResourceId, String name, double latitude, double longitude) {
        this.id = id;
        this.type = type;
        this.isReserved = isReserved;
        this.stationName = stationName;
        this.imageResourceId = imageResourceId;
        this.name = name;
        this.location = new Location(""); // Empty provider
        this.location.setLatitude(latitude);
        this.location.setLongitude(longitude);
    }

    // Getter for the location
    public Location getLocation() {
        return location;
    }

    // Setter for the location (optional, if you need to update it later)
    public void setLocation(Location location) {
        this.location = location;
    }

    // Existing getters and setters...
    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean reserved) {
        isReserved = reserved;
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
}