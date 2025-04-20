package com.example.rentalspin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StationBikeAdapter extends RecyclerView.Adapter<StationBikeAdapter.ViewHolder> {

    private List<Bike> bikes;
    private Context context;
    private DatabaseHelper dbHelper;
    private OnBikeReservedListener onBikeReservedListener;

    public interface OnBikeReservedListener {
        void onBikeReserved(String bikeId);
    }

    public void setOnBikeReservedListener(OnBikeReservedListener listener) {
        this.onBikeReservedListener = listener;
    }

    public StationBikeAdapter(Context context, List<Bike> bikes) {
        this.context = context;
        this.bikes = bikes;
        this.dbHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_station_bike, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bike bike = bikes.get(position);
        holder.textViewBikeType.setText(bike.getType());
        holder.imageViewBike.setImageResource(bike.getImageResourceId());

        if (bike.isReserved()) {
            holder.buttonReserveBike.setEnabled(false);
            holder.buttonReserveBike.setText("Reserved");
        } else {
            holder.buttonReserveBike.setEnabled(true);
            holder.buttonReserveBike.setText("Reserve");
        }

        holder.buttonReserveBike.setOnClickListener(v -> {
            if (!bike.isReserved()) { // Only allow reservation if the bike is not already reserved
                String bikeId = bike.getId();
                String bikeType = bike.getType();
                String stationName = bike.getStationName();
                String userId = "user123"; // Replace with actual user ID retrieval

                long reservationStartTime = System.currentTimeMillis();
                long reservationEndTime = 0;

                Reservation reservation = new Reservation(userId, bikeId, reservationStartTime, reservationEndTime, stationName);
                long reservationId = dbHelper.addReservation(reservation);

                if (reservationId != -1) {
                    Toast.makeText(v.getContext(), bikeType + " reserved successfully! Reservation ID: " + reservationId, Toast.LENGTH_SHORT).show();
                    if (onBikeReservedListener != null) {
                        onBikeReservedListener.onBikeReserved(bikeId);
                        // Immediately update the UI to reflect the reservation
                        bike.setReserved(true);
                        notifyItemChanged(position); // Only update the current item
                    }
                } else {
                    Toast.makeText(v.getContext(), "Failed to reserve " + bikeType, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(v.getContext(), bike.getType() + " is already reserved.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return bikes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewBike;
        TextView textViewBikeType;
        Button buttonReserveBike;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewBike = itemView.findViewById(R.id.imageViewBike);
            textViewBikeType = itemView.findViewById(R.id.textViewBikeType);
            buttonReserveBike = itemView.findViewById(R.id.buttonReserveBike);
        }
    }
}