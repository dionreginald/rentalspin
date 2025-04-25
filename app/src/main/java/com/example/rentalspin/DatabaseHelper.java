package com.example.rentalspin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log; // For potential error logging

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "BikeRental.db";
    private static final int DATABASE_VERSION = 1;

    // Table name
    private static final String TABLE_RESERVATIONS = "reservations";

    // Table Columns
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_BIKE_ID = "bike_id";
    private static final String COLUMN_RESERVATION_START_TIME = "start_time";
    private static final String COLUMN_RESERVATION_END_TIME = "end_time";
    private static final String COLUMN_STATION_NAME = "station_name";

    // SQL query to create the reservations table
    private static final String CREATE_RESERVATIONS_TABLE =
            "CREATE TABLE " + TABLE_RESERVATIONS + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_USER_ID + " TEXT NOT NULL,"
                    + COLUMN_BIKE_ID + " TEXT NOT NULL,"
                    + COLUMN_RESERVATION_START_TIME + " INTEGER NOT NULL,"
                    + COLUMN_RESERVATION_END_TIME + " INTEGER,"
                    + COLUMN_STATION_NAME + " TEXT NOT NULL"
                    + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_RESERVATIONS_TABLE);
        Log.i("DatabaseHelper", "Reservations table created.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades if needed
        Log.w("DatabaseHelper", "Upgrading database from version " + oldVersion + " to " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESERVATIONS);
        onCreate(db);
    }

    // Method to add a new reservation
    public long addReservation(Reservation reservation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, reservation.getUserId());
        values.put(COLUMN_BIKE_ID, reservation.getBikeId());
        values.put(COLUMN_RESERVATION_START_TIME, reservation.getReservationStartTime());
        values.put(COLUMN_RESERVATION_END_TIME, reservation.getReservationEndTime());
        values.put(COLUMN_STATION_NAME, reservation.getStationName());

        long id = db.insert(TABLE_RESERVATIONS, null, values);
        db.close();
        Log.i("DatabaseHelper", "Reservation added with ID: " + id);
        return id;
    }

    // Method to get all reservations for a specific user
    public List<Reservation> getUserReservations(String userId) {
        List<Reservation> reservations = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null; // Initialize cursor to null

        try {
            cursor = db.query(TABLE_RESERVATIONS, null, COLUMN_USER_ID + "=?", new String[]{userId}, null, null, COLUMN_RESERVATION_START_TIME + " DESC");

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Reservation reservation = new Reservation();
                    reservation.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                    reservation.setUserId(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_ID)));
                    reservation.setBikeId(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BIKE_ID)));
                    reservation.setReservationStartTime(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_RESERVATION_START_TIME)));
                    reservation.setReservationEndTime(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_RESERVATION_END_TIME)));
                    reservation.setStationName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STATION_NAME)));
                    reservations.add(reservation);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error fetching user reservations: " + e.getMessage());
            // Consider re-throwing the exception or handling it as appropriate for your app
        } finally {
            // Ensure the cursor is always closed
            if (cursor != null) {
                cursor.close();
            }
            // Ensure the database connection is closed
            db.close();
        }
        Log.i("DatabaseHelper", "Retrieved " + reservations.size() + " reservations for user: " + userId);
        return reservations;
    }

    // Method to delete a reservation by its ID
    public boolean deleteReservation(int reservationId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(TABLE_RESERVATIONS, COLUMN_ID + "=?", new String[]{String.valueOf(reservationId)});
        db.close();
        if (rowsAffected > 0) {
            Log.i("DatabaseHelper", "Reservation deleted with ID: " + reservationId);
            return true;
        } else {
            Log.w("DatabaseHelper", "Failed to delete reservation with ID: " + reservationId);
            return false;
        }
    }

    // Method to update the end time of a reservation (e.g., when a user returns a bike)
    public boolean updateReservationEndTime(int reservationId, long endTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_RESERVATION_END_TIME, endTime);
        int rowsAffected = db.update(TABLE_RESERVATIONS, values, COLUMN_ID + "=?", new String[]{String.valueOf(reservationId)});
        db.close();
        if (rowsAffected > 0) {
            Log.i("DatabaseHelper", "Reservation end time updated for ID: " + reservationId + " to " + endTime);
            return true;
        } else {
            Log.w("DatabaseHelper", "Failed to update reservation end time for ID: " + reservationId);
            return false;
        }
    }

    // Method to get a specific reservation by its ID
    public Reservation getReservation(int reservationId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null; // Initialize cursor to null
        Reservation reservation = null;

        try {
            cursor = db.query(TABLE_RESERVATIONS, null, COLUMN_ID + "=?", new String[]{String.valueOf(reservationId)}, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                reservation = new Reservation();
                reservation.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                reservation.setUserId(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_ID)));
                reservation.setBikeId(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BIKE_ID)));
                reservation.setReservationStartTime(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_RESERVATION_START_TIME)));
                reservation.setReservationEndTime(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_RESERVATION_END_TIME)));
                reservation.setStationName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STATION_NAME)));
                Log.i("DatabaseHelper", "Retrieved reservation with ID: " + reservationId);
            } else {
                Log.w("DatabaseHelper", "Reservation not found with ID: " + reservationId);
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error fetching reservation: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return reservation;
    }
}