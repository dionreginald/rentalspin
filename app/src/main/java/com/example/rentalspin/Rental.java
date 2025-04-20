package com.example.rentalspin;

import java.util.Date;

public class Rental {
    private String bikeName; // Or bike ID
    private String stationName;
    private Date startTime;
    private Date endTime; // Null if currently rented

    public Rental(String bikeName, String stationName, Date startTime, Date endTime) {
        this.bikeName = bikeName;
        this.stationName = stationName;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getBikeName() {
        return bikeName;
    }

    public String getStationName() {
        return stationName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}