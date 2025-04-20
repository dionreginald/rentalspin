package com.example.rentalspin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyReservationsFragment extends Fragment {

    private RecyclerView recyclerViewMyReservations;
    private TextView textViewNoReservations;
    private DatabaseHelper dbHelper;
    private ReservationAdapter reservationAdapter;
    private String userId = "user123"; // Replace with actual user ID retrieval

    public MyReservationsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_reservations, container, false);

        recyclerViewMyReservations = view.findViewById(R.id.recyclerViewMyReservations);
        textViewNoReservations = view.findViewById(R.id.textViewNoReservations);
        recyclerViewMyReservations.setLayoutManager(new LinearLayoutManager(getContext()));

        dbHelper = new DatabaseHelper(getContext());
        loadReservations();

        return view;
    }

    private void loadReservations() {
        List<Reservation> reservations = dbHelper.getUserReservations(userId);

        if (reservations.isEmpty()) {
            recyclerViewMyReservations.setVisibility(View.GONE);
            textViewNoReservations.setVisibility(View.VISIBLE);
        } else {
            recyclerViewMyReservations.setVisibility(View.VISIBLE);
            textViewNoReservations.setVisibility(View.GONE);
            reservationAdapter = new ReservationAdapter(reservations);
            recyclerViewMyReservations.setAdapter(reservationAdapter);
        }
    }
}