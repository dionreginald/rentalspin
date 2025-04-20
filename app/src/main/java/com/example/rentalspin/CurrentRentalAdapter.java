package com.example.rentalspin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class CurrentRentalAdapter extends RecyclerView.Adapter<CurrentRentalAdapter.CurrentRentalViewHolder> {

    private List<Rental> currentRentals;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

    public CurrentRentalAdapter(List<Rental> currentRentals) {
        this.currentRentals = currentRentals;
    }

    @NonNull
    @Override
    public CurrentRentalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Destination: Inflate the layout for a single current rental item
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_current_rental, parent, false);
        return new CurrentRentalViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrentRentalViewHolder holder, int position) {
        // Destination: Get the Rental object at the current position
        Rental rental = currentRentals.get(position);
        // Destination: Bind the rental data to the views in the ViewHolder
        holder.textViewBikeName.setText("Bike: " + rental.getBikeName());
        holder.textViewStationName.setText("Station: " + rental.getStationName());
        holder.textViewStartTime.setText("Started: " + dateFormat.format(rental.getStartTime()));
    }

    @Override
    public int getItemCount() {
        // Destination: Return the total number of current rentals
        return currentRentals.size();
    }

    // Destination: ViewHolder class to hold the views for each item
    public static class CurrentRentalViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewBikeName;
        public TextView textViewStationName;
        public TextView textViewStartTime;

        public CurrentRentalViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewBikeName = itemView.findViewById(R.id.textViewBikeName);
            textViewStationName = itemView.findViewById(R.id.textViewStationName);
            textViewStartTime = itemView.findViewById(R.id.textViewStartTime);
        }
    }
}