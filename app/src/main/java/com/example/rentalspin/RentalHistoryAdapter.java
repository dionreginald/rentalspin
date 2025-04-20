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

public class RentalHistoryAdapter extends RecyclerView.Adapter<RentalHistoryAdapter.RentalHistoryViewHolder> {

    private List<Rental> rentalHistory;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

    public RentalHistoryAdapter(List<Rental> rentalHistory) {
        this.rentalHistory = rentalHistory;
    }

    @NonNull
    @Override
    public RentalHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Destination: Inflate the layout for a single rental history item
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rental_history, parent, false);
        return new RentalHistoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RentalHistoryViewHolder holder, int position) {
        // Destination: Get the Rental object at the current position
        Rental rental = rentalHistory.get(position);
        // Destination: Bind the rental data to the views in the ViewHolder
        holder.textViewBikeName.setText("Bike: " + rental.getBikeName());
        holder.textViewStationName.setText("Station: " + rental.getStationName());
        holder.textViewStartTime.setText("Started: " + dateFormat.format(rental.getStartTime()));
        // Destination: Bind the end time if it exists
        if (rental.getEndTime() != null) {
            holder.textViewEndTime.setText("Ended: " + dateFormat.format(rental.getEndTime()));
        } else {
            holder.textViewEndTime.setText("Ended: -");
        }
    }

    @Override
    public int getItemCount() {
        // Destination: Return the total number of rental history items
        return rentalHistory.size();
    }

    // Destination: ViewHolder class to hold the views for each item
    public static class RentalHistoryViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewBikeName;
        public TextView textViewStationName;
        public TextView textViewStartTime;
        public TextView textViewEndTime;

        public RentalHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            // Destination: Find the TextViews in the item layout
            textViewBikeName = itemView.findViewById(R.id.textViewBikeName);
            textViewStationName = itemView.findViewById(R.id.textViewStationName);
            textViewStartTime = itemView.findViewById(R.id.textViewStartTime);
            textViewEndTime = itemView.findViewById(R.id.textViewEndTime);
        }
    }
}