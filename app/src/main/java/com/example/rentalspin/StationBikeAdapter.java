package com.example.rentalspin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class StationBikeAdapter extends RecyclerView.Adapter<StationBikeAdapter.ViewHolder> {

    private Context context;
    private List<Bike> bikes;
    private OnBikeReservedListener onBikeReservedListener;

    public StationBikeAdapter(Context context, List<Bike> bikes) {
        this.context = context;
        this.bikes = bikes;
    }

    public void setOnBikeReservedListener(OnBikeReservedListener listener) {
        this.onBikeReservedListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_station_bike, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bike bike = bikes.get(position);
        holder.textViewBikeName.setText(bike.getName());
        holder.imageViewBike.setImageResource(bike.getImageResourceId());

        if (bike.isReserved()) {
            holder.buttonReserveBike.setText("Reserved");
            holder.buttonReserveBike.setEnabled(false);
        } else {
            holder.buttonReserveBike.setText("Reserve");
            holder.buttonReserveBike.setEnabled(true);
        }

        holder.buttonReserveBike.setOnClickListener(v -> {
            if (!bike.isReserved()) {
                // Simulate reservation (you'll replace this with database logic)
                if (onBikeReservedListener != null) {
                    onBikeReservedListener.onBikeReserved(bike); // Pass the Bike object
                    bike.setReserved(true);
                    updateReservationUI(holder, bike);
                }
                // For now, just update the UI here
            }
        });
    }

    private void updateReservationUI(ViewHolder holder, Bike bike) {
        if (bike.isReserved()) {
            holder.buttonReserveBike.setText("Reserved");
            holder.buttonReserveBike.setEnabled(false);
        } else {
            holder.buttonReserveBike.setText("Reserve");
            holder.buttonReserveBike.setEnabled(true);
        }
    }

    @Override
    public int getItemCount() {
        return bikes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewBike;
        TextView textViewBikeName;
        Button buttonReserveBike;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewBike = itemView.findViewById(R.id.imageViewBike);
            textViewBikeName = itemView.findViewById(R.id.textViewBikeName);
            buttonReserveBike = itemView.findViewById(R.id.buttonReserveBike);
        }
    }

    public interface OnBikeReservedListener {
        void onBikeReserved(Bike bike); // Expecting a Bike object here as well
    }
}