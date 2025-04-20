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

        // You might need to fetch the Bike object using the bikeId to get the bike type
        // For simplicity, we'll assume the bike type is stored in the Reservation object or can be retrieved.
        // If not, you'll need to modify your Reservation class or perform a database join.
        holder.textViewBikeType.setText("Bike ID: " + reservation.getBikeId()); // Placeholder

        holder.textViewStationName.setText("Station: " + reservation.getStationName());

        // Format the reservation start time
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault());
        String formattedDate = sdf.format(new Date(reservation.getReservationStartTime()));
        holder.textViewReservationTime.setText("Reserved at: " + formattedDate);
    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewBikeType;
        TextView textViewStationName;
        TextView textViewReservationTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewBikeType = itemView.findViewById(R.id.textViewBikeType);
            textViewStationName = itemView.findViewById(R.id.textViewStationName);
            textViewReservationTime = itemView.findViewById(R.id.textViewReservationTime);
        }
    }
}