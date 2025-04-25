package com.example.rentalspin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ViewHolder> {

    private List<Reservation> reservations;

    public ReservationAdapter(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_reservation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Reservation reservation = reservations.get(position);

        holder.textViewBikeId.setText("Bike ID: " + reservation.getBikeId()); // Keeping Bike ID for now

        holder.textViewStationName.setText("Station: " + reservation.getStationName());

        // Format the reservation start time
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault());
        String formattedStartTime = sdf.format(new Date(reservation.getReservationStartTime()));
        holder.textViewReservationTime.setText("Reserved at: " + formattedStartTime);
    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewBikeId;
        TextView textViewStationName;
        TextView textViewReservationTime;
        TextView textViewReturnTime; // Added for return time

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewBikeId = itemView.findViewById(R.id.textViewBikeId);
            textViewStationName = itemView.findViewById(R.id.textViewStationName);
            textViewReservationTime = itemView.findViewById(R.id.textViewReservationTime);
            textViewReturnTime = itemView.findViewById(R.id.textViewReturnTime); // Find the new TextView in item_reservation.xml
        }
    }
}