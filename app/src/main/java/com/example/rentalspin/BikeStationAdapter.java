package com.example.rentalspin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class BikeStationAdapter extends RecyclerView.Adapter<BikeStationAdapter.ViewHolder> {

    private List<BikeStation> bikeStations;
    private OnBikeReservedListener onBikeReservedListener;

    public BikeStationAdapter(List<BikeStation> bikeStations) {
        this.bikeStations = bikeStations;
    }

    public void setOnBikeReservedListener(OnBikeReservedListener listener) {
        this.onBikeReservedListener = listener;
    }

    public void updateList(List<BikeStation> newList) {
        bikeStations = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bike_station, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BikeStation station = bikeStations.get(position);
        holder.textViewName.setText(station.getStationName());

        List<Bike> bikes = station.getBikes();
        if (bikes != null && !bikes.isEmpty()) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(holder.recyclerViewStationBikes.getContext(), LinearLayoutManager.HORIZONTAL, false);
            holder.recyclerViewStationBikes.setLayoutManager(layoutManager);
            StationBikeAdapter stationBikeAdapter = new StationBikeAdapter(holder.recyclerViewStationBikes.getContext(), bikes);
            stationBikeAdapter.setOnBikeReservedListener(reservedBike -> {
                if (onBikeReservedListener != null) {
                    onBikeReservedListener.onBikeReserved(reservedBike);
                }
            });
            holder.recyclerViewStationBikes.setAdapter(stationBikeAdapter);
            holder.recyclerViewStationBikes.setVisibility(View.VISIBLE);
        } else {
            holder.recyclerViewStationBikes.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return bikeStations.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        RecyclerView recyclerViewStationBikes;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewStationName);
            recyclerViewStationBikes = itemView.findViewById(R.id.recyclerViewStationBikes);
        }
    }

    public interface OnBikeReservedListener {
        void onBikeReserved(Bike bike);
    }
}