package com.example.rentalspin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {

    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextDOB;
    private EditText editTextEmailRegister;
    private EditText editTextPasswordRegister;
    private Button buttonRegisterAccount;
    private Button buttonBackToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Initialize UI elements
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextDOB = findViewById(R.id.editTextDOB);
        editTextEmailRegister = findViewById(R.id.editTextEmailRegister);
        editTextPasswordRegister = findViewById(R.id.editTextPasswordRegister);
        buttonRegisterAccount = findViewById(R.id.buttonRegisterAccount);
        buttonBackToLogin = findViewById(R.id.buttonBackToLogin);

        // Set click listener for the Register Account button
        buttonRegisterAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = editTextFirstName.getText().toString();
                String lastName = editTextLastName.getText().toString();
                String dob = editTextDOB.getText().toString();
                String email = editTextEmailRegister.getText().toString();
                String password = editTextPasswordRegister.getText().toString();

                // For now, just show a toast message
                String registrationDetails = "Registered with:\n" +
                        "Name: " + firstName + " " + lastName + "\n" +
                        "DOB: " + dob + "\n" +
                        "Email: " + email + "\n" +
                        "Password: " + password;
                Toast.makeText(RegistrationActivity.this, registrationDetails, Toast.LENGTH_LONG).show();

                // In a real app, you would save this data and navigate to the home screen
                // For now, let's go back to the login screen
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Close the registration activity
            }
        });

        // Set click listener for the Back to Login button
        buttonBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go back to the login screen
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Close the registration activity
            }
        });
    }
}