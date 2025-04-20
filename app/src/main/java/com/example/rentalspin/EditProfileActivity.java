package com.example.rentalspin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.android.material.textfield.TextInputEditText;
import android.widget.Toast;

public class EditProfileActivity extends AppCompatActivity {

    private TextInputEditText editTextFullName;
    private TextInputEditText editTextEmail;
    private Button buttonSaveProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Initialize views
        editTextFullName = findViewById(R.id.editTextFullName);
        editTextEmail = findViewById(R.id.editTextEmail);
        buttonSaveProfile = findViewById(R.id.buttonSaveProfile);

        // Load existing user data (Adapt to your data source)
        loadUserProfile();

        // Set click listener for the save button
        buttonSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserProfile();
            }
        });
    }

    private void loadUserProfile() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE);
        String currentFullName = sharedPreferences.getString("fullName", "");
        String currentEmail = sharedPreferences.getString("email", "");

        editTextFullName.setText(currentFullName);
        editTextEmail.setText(currentEmail);
        // Load other profile data into their respective EditText fields
    }

    private void saveUserProfile() {
        String newFullName = editTextFullName.getText().toString().trim();
        String newEmail = editTextEmail.getText().toString().trim();

        // Basic validation (you should add more robust validation)
        if (newFullName.isEmpty() || newEmail.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save the updated user data (Adapt to your data storage)
        SharedPreferences sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("fullName", newFullName);
        editor.putString("email", newEmail);
        editor.apply(); // Or editor.commit()

        Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
        finish(); // Go back to the ProfileFragment
    }
}