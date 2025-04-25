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
    private DatabaseHelper dbHelper; // Add DatabaseHelper instance

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
        setHasOptionsMenu(true);
        dbHelper = new DatabaseHelper(getContext()); // Initialize DatabaseHelper
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
        originalBikeStationList = generateSampleBikeStations();
        bikeStationList = new ArrayList<>(originalBikeStationList);
        bikeStationAdapter = new BikeStationAdapter(bikeStationList);
        recyclerViewBikeStations.setAdapter(bikeStationAdapter);

        // Set the OnBikeReservedListener on the BikeStationAdapter
        bikeStationAdapter.setOnBikeReservedListener(new BikeStationAdapter.OnBikeReservedListener() {
            @Override
            public void onBikeReserved(Bike bike) {
                // Handle bike reservation here
                String userId = "user123"; // Replace with actual user ID retrieval
                long startTime = System.currentTimeMillis();
                Reservation reservation = new Reservation(userId, bike.getId(), startTime, 0, bike.getStationName());
                long reservationId = dbHelper.addReservation(reservation);

                if (reservationId != -1) {
                    // Reservation successful, update UI
                    for (BikeStation station : originalBikeStationList) {
                        for (Bike b : station.getBikes()) {
                            if (b.getId().equals(bike.getId())) {
                                b.setReserved(true);
                                break;
                            }
                        }
                    }
                    bikeStationAdapter.notifyDataSetChanged(); // Update the list in the UI
                    // Optionally, show a confirmation message to the user
                } else {
                    // Reservation failed, show an error message
                    // Log the error
                }
            }
        });

        updateEmptyState();

        return view;
    }

    private List<BikeStation> generateSampleBikeStations() {
        List<BikeStation> stations = new ArrayList<>();

        BikeStation centralStation = new BikeStation("Central Station", 5, 6.9271, 79.8612);
        List<Bike> centralBikes = new ArrayList<>();
        centralBikes.add(new Bike("1", "Standard", false, "Central Station", R.drawable.ic_standard_bike, "Standard Bike 1"));
        centralBikes.add(new Bike("2", "Electric", false, "Central Station", R.drawable.ic_electric_bike, "Electric Bike 1"));
        centralBikes.add(new Bike("3", "Standard", false, "Central Station", R.drawable.ic_standard_bike, "Standard Bike 2"));
        centralBikes.add(new Bike("4", "Cruiser", false, "Central Station", R.drawable.ic_cruiser_bike, "Cruiser Bike 1"));
        centralBikes.add(new Bike("5", "Electric", false, "Central Station", R.drawable.ic_electric_bike, "Electric Bike 2"));
        centralStation.setBikes(centralBikes);
        stations.add(centralStation);

        BikeStation parkAvenue = new BikeStation("Park Avenue Hub", 4, 6.9300, 79.8590);
        List<Bike> parkBikes = new ArrayList<>();
        parkBikes.add(new Bike("6", "Cruiser", false, "Park Avenue Hub", R.drawable.ic_cruiser_bike, "Cruiser Bike 1"));
        parkBikes.add(new Bike("7", "Standard", false, "Park Avenue Hub", R.drawable.ic_standard_bike, "Standard Bike 1"));
        parkBikes.add(new Bike("8", "Electric", false, "Park Avenue Hub", R.drawable.ic_electric_bike, "Electric Bike 1"));
        parkBikes.add(new Bike("9", "Standard", false, "Park Avenue Hub", R.drawable.ic_standard_bike, "Standard Bike 2"));
        parkAvenue.setBikes(parkBikes);
        stations.add(parkAvenue);

        BikeStation lakesideStation = new BikeStation("Lakeside Station", 3, 6.9350, 79.8550);
        List<Bike> lakesideBikes = new ArrayList<>();
        lakesideBikes.add(new Bike("10", "Electric", false, "Lakeside Station", R.drawable.ic_electric_bike, "Electric Bike 1"));
        lakesideBikes.add(new Bike("11", "Cruiser", false, "Lakeside Station", R.drawable.ic_cruiser_bike, "Cruiser Bike 1"));
        lakesideBikes.add(new Bike("12", "Standard", false, "Lakeside Station", R.drawable.ic_standard_bike, "Standard Bike 1"));
        lakesideStation.setBikes(lakesideBikes);
        stations.add(lakesideStation);

        return stations;
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}