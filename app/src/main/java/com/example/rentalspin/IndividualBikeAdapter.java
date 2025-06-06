package com.example.rentalspin;

import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class IndividualBikeAdapter extends RecyclerView.Adapter<IndividualBikeAdapter.ViewHolder> {

    private List<Bike> bikes;
    private Location userLocation;

    public IndividualBikeAdapter(List<Bike> bikes, Location userLocation) {
        this.bikes = bikes;
        this.userLocation = userLocation;
    }

    public void updateList(List<Bike> newList) {
        this.bikes = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_individual_bike, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bike bike = bikes.get(position);

        // Ensure your Bike class has getType() and getLocation() methods
        if (bike.getType() != null) {
            holder.textViewBikeType.setText(bike.getType() + " Bike");
        } else {
            holder.textViewBikeType.setText("Bike Type Unavailable");
        }

        if (bike.getStationName() != null) {
            holder.textViewStationName.setText("at " + bike.getStationName());
        } else {
            holder.textViewStationName.setText("Station Name Unavailable");
        }

        if (userLocation != null && bike.getLocation() != null) {
            float distance = userLocation.distanceTo(bike.getLocation()) / 1000; // Convert to kilometers
            holder.textViewDistance.setText(String.format(Locale.getDefault(), "%.2f km away", distance));
        } else {
            holder.textViewDistance.setText("Distance unavailable");
            // Optional: You could check if location permissions are granted here
            // and display a more informative message if not.
        }
    }

    @Override
    public int getItemCount() {
        return bikes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewBikeType;
        TextView textViewDistance;
        TextView textViewStationName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewBikeType = itemView.findViewById(R.id.textViewBikeType);
            textViewDistance = itemView.findViewById(R.id.textViewDistance);
            textViewStationName = itemView.findViewById(R.id.textViewStationName);
        }
    }

    // Optional: Add an OnItemClickListener interface and implementation here if needed.
}