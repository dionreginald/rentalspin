package com.example.rentalspin;

import java.util.List;

public class BikeStation {
    private String stationName;
    private int availableBikes;
    private List<Bike> bikes;
    // Add latitude and longitude if needed
    private double latitude;
    private double longitude;

    public BikeStation(String stationName, int availableBikes) {
        this.stationName = stationName;
        this.availableBikes = availableBikes;
        this.bikes = null; // Initialize as null, will be set later
        // Initialize latitude and longitude with default values
        this.latitude = 0.0;
        this.longitude = 0.0;
    }

    // Constructor with latitude and longitude
    public BikeStation(String stationName, int availableBikes, double latitude, double longitude) {
        this.stationName = stationName;
        this.availableBikes = availableBikes;
        this.bikes = null; // Initialize as null, will be set later
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getStationName() {
        return stationName;
    }

    public int getAvailableBikes() {
        return availableBikes;
    }

    public List<Bike> getBikes() {
        return bikes;
    }

    public void setBikes(List<Bike> bikes) {
        this.bikes = bikes;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    // You might want to add setters for other properties if needed
}