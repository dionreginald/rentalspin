package com.example.rentalspin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerViewBikeStations;
    private BikeStationAdapter bikeStationAdapter;
    private List<BikeStation> bikeStationList;
    private List<BikeStation> originalBikeStationList;
    private TextView textViewEmptyStations;
    private TextView textViewGreeting;
    private String firstName;
    private Toolbar toolbar;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String firstName) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString("firstName", firstName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            firstName = getArguments().getString("firstName");
        } else {
            firstName = "User";
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        setHasOptionsMenu(true);

        toolbar = view.findViewById(R.id.toolbar);
        if (getActivity() != null) {
            ((androidx.appcompat.app.AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        }

        textViewGreeting = view.findViewById(R.id.textViewGreeting);
        textViewEmptyStations = view.findViewById(R.id.textViewEmptyStations);
        recyclerViewBikeStations = view.findViewById(R.id.recyclerViewBikeStations);
        recyclerViewBikeStations.setLayoutManager(new LinearLayoutManager(getActivity()));

        textViewGreeting.setText("Hi " + firstName + "!");

        // Sample data for bike stations and bikes with images
        originalBikeStationList = new ArrayList<>();

        BikeStation centralStation = new BikeStation("Central Station", 5, 6.9271, 79.8612);
        List<Bike> centralBikes = new ArrayList<>();
        centralBikes.add(new Bike("1", "Standard", 0, 0, "Central Station", R.drawable.ic_standard_bike, "Standard Bike 1"));
        centralBikes.add(new Bike("2", "Electric", 0, 0, "Central Station", R.drawable.ic_electric_bike, "Electric Bike 1"));
        centralBikes.add(new Bike("3", "Standard", 0, 0, "Central Station", R.drawable.ic_standard_bike, "Standard Bike 2"));
        centralBikes.add(new Bike("4", "Cruiser", 0, 0, "Central Station", R.drawable.ic_cruiser_bike, "Cruiser Bike 1"));
        centralBikes.add(new Bike("5", "Electric", 0, 0, "Central Station", R.drawable.ic_electric_bike, "Electric Bike 2"));
        centralStation.setBikes(centralBikes);
        originalBikeStationList.add(centralStation);

        BikeStation parkAvenue = new BikeStation("Park Avenue Hub", 4, 6.9300, 79.8590);
        List<Bike> parkBikes = new ArrayList<>();
        parkBikes.add(new Bike("6", "Cruiser", 0, 0, "Park Avenue Hub", R.drawable.ic_cruiser_bike, "Cruiser Bike 1"));
        parkBikes.add(new Bike("7", "Standard", 0, 0, "Park Avenue Hub", R.drawable.ic_standard_bike, "Standard Bike 1"));
        parkBikes.add(new Bike("8", "Electric", 0, 0, "Park Avenue Hub", R.drawable.ic_electric_bike, "Electric Bike 1"));
        parkBikes.add(new Bike("9", "Standard", 0, 0, "Park Avenue Hub", R.drawable.ic_standard_bike, "Standard Bike 2"));
        parkAvenue.setBikes(parkBikes);
        originalBikeStationList.add(parkAvenue);

        BikeStation lakesideStation = new BikeStation("Lakeside Station", 3, 6.9350, 79.8550);
        List<Bike> lakesideBikes = new ArrayList<>();
        lakesideBikes.add(new Bike("10", "Electric", 0, 0, "Lakeside Station", R.drawable.ic_electric_bike, "Electric Bike 1"));
        lakesideBikes.add(new Bike("11", "Cruiser", 0, 0, "Lakeside Station", R.drawable.ic_cruiser_bike, "Cruiser Bike 1"));
        lakesideBikes.add(new Bike("12", "Standard", 0, 0, "Lakeside Station", R.drawable.ic_standard_bike, "Standard Bike 1"));
        lakesideStation.setBikes(lakesideBikes);
        originalBikeStationList.add(lakesideStation);

        bikeStationList = new ArrayList<>(originalBikeStationList);
        bikeStationAdapter = new BikeStationAdapter(bikeStationList);
        recyclerViewBikeStations.setAdapter(bikeStationAdapter);

        updateEmptyState();

        return view;
    }

    private void filterBikeStations(String query) {
        List<BikeStation> filteredList = new ArrayList<>();
        for (BikeStation station : originalBikeStationList) {
            if (station.getStationName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(station);
            }
        }
        bikeStationList = filteredList;
        bikeStationAdapter.updateList(bikeStationList);
        updateEmptyState();
    }

    private void updateEmptyState() {
        if (bikeStationList.isEmpty()) {
            recyclerViewBikeStations.setVisibility(View.GONE);
            textViewEmptyStations.setVisibility(View.VISIBLE);
        } else {
            recyclerViewBikeStations.setVisibility(View.VISIBLE);
            textViewEmptyStations.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_home, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterBikeStations(newText);
                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }
    public class HomeFragment extends Fragment implements StationBikeAdapter.OnBikeReservedListener {

        // ... other variables ...

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // ... existing onCreateView code ...

            bikeStationAdapter = new BikeStationAdapter(bikeStationList);
            recyclerViewBikeStations.setAdapter(bikeStationAdapter);

            // Set the OnBikeReservedListener on the StationBikeAdapter
            bikeStationAdapter.setOnBikeReservedListener(this);

            updateEmptyState();

            return view;
        }

        @Override
        public void onBikeReserved(String bikeId) {
            // Find the Bike object in our list and mark it as reserved
            for (BikeStation station : originalBikeStationList) {
                for (Bike bike : station.getBikes()) {
                    if (bike.getId().equals(bikeId) && !bike.isReserved()) {
                        bike.setReserved(true);
                        // Optionally, update the UI immediately if needed
                        bikeStationAdapter.notifyDataSetChanged(); // Or notifyItemChanged for the specific station
                        return;
                    }
                }
            }
            // Handle case where bike ID is not found or already reserved
        }

        // ... rest of your HomeFragment code ...
    }
}