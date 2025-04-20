package com.example.rentalspin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast; // Import Toast

public class ProfileFragment extends Fragment {

    private TextView textViewName;
    private TextView textViewEmail;
    private Button buttonLogout;
    private Button buttonEditProfile;
    private Button buttonPaymentMethods;
    private Button buttonSettings; // Add the Settings button

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize Views
        textViewName = view.findViewById(R.id.textViewName);
        textViewEmail = view.findViewById(R.id.textViewEmail);
        buttonLogout = view.findViewById(R.id.buttonLogout);
        buttonEditProfile = view.findViewById(R.id.buttonEditProfile);
        buttonPaymentMethods = view.findViewById(R.id.buttonPaymentMethods);
        buttonSettings = view.findViewById(R.id.buttonSettings); // Initialize Settings button

        // Retrieve user data (Adapt to your data storage)
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        String userName = sharedPreferences.getString("fullName", "John Doe");
        String userEmail = sharedPreferences.getString("email", "john.doe@example.com");

        // Display user data
        textViewName.setText(userName);
        textViewEmail.setText(userEmail);

        // Set OnClickListener for the Logout button
        buttonLogout.setOnClickListener(v -> logoutUser());

        // Set OnClickListener for the Edit Profile button
        buttonEditProfile.setOnClickListener(v -> navigateToEditProfile());

        // Set OnClickListener for the Payment Methods button
        buttonPaymentMethods.setOnClickListener(v -> navigateToPaymentMethods());

        // Set OnClickListener for the Settings button
        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement navigation to the Settings screen
                navigateToSettings();
            }
        });

        return view;
    }

    private void logoutUser() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        Intent intent = new Intent(getActivity(), LoginActivity.class); // Replace with your login activity
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();
    }

    private void navigateToEditProfile() {
        Intent intent = new Intent(getActivity(), EditProfileActivity.class);
        startActivity(intent);
    }

    private void navigateToPaymentMethods() {
        Intent intent = new Intent(getActivity(), PaymentMethodsActivity.class);
        startActivity(intent);
    }

    private void navigateToSettings() {
        // For now, let's just show a Toast message as a placeholder
        Toast.makeText(getActivity(), "Navigating to Settings...", Toast.LENGTH_SHORT).show();

        // In a real application, you would typically:
        // 1. Start a new Activity for app settings:
        //    Intent intent = new Intent(getActivity(), SettingsActivity.class);
        //    startActivity(intent);
        //
        // 2. Show a Fragment (within the MainActivity or a new Activity) to display the settings options.
    }
}