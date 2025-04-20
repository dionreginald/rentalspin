package com.example.rentalspin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BikeImageAdapter extends RecyclerView.Adapter<BikeImageAdapter.ViewHolder> {

    private List<Bike> bikes;

    public BikeImageAdapter(List<Bike> bikes) {
        this.bikes = bikes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bike_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bike bike = bikes.get(position);
        holder.imageViewBike.setImageResource(bike.getImageResourceId());
        // You might want to set a content description for accessibility
    }

    @Override
    public int getItemCount() {
        return bikes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewBike;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewBike = itemView.findViewById(R.id.imageViewBike);
        }
    }
}