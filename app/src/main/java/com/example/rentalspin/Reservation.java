package com.example.rentalspin;

public class Reservation {
    private int id; // Primary key for the reservation
    private String userId;
    private String bikeId;
    private long reservationStartTime;
    private long reservationEndTime; // Optional
    private String stationName;

    // Constructors
    public Reservation() {
    }

    public Reservation(String userId, String bikeId, long reservationStartTime, long reservationEndTime, String stationName) {
        this.userId = userId;
        this.bikeId = bikeId;
        this.reservationStartTime = reservationStartTime;
        this.reservationEndTime = reservationEndTime;
        this.stationName = stationName;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getBikeId() {
        return bikeId;
    }

    public long getReservationStartTime() {
        return reservationStartTime;
    }

    public long getReservationEndTime() {
        return reservationEndTime;
    }

    public String getStationName() {
        return stationName;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setBikeId(String bikeId) {
        this.bikeId = bikeId;
    }

    public void setReservationStartTime(long reservationStartTime) {
        this.reservationStartTime = reservationStartTime;
    }

    public void setReservationEndTime(long reservationEndTime) {
        this.reservationEndTime = reservationEndTime;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }
}