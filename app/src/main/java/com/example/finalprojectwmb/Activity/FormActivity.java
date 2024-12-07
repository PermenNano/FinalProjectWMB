package com.example.finalprojectwmb.Activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.finalprojectwmb.R;
import com.example.finalprojectwmb.TravelApplication;
import com.google.firebase.auth.FirebaseAuth; // Import FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore;

public class FormActivity extends AppCompatActivity {

    private EditText nameEditText, emailEditText, phoneEditText;
    private Button submitButton;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize views
        nameEditText = findViewById(R.id.nameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        submitButton = findViewById(R.id.submitButton);

        // Set up submit button click listener
        submitButton.setOnClickListener(v -> submitForm());
    }

    private void submitForm() {
        String name = nameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String phone = phoneEditText.getText().toString();

        // Validate input
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get the current user's ID
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Create a new travel application object
        TravelApplication application = new TravelApplication(name, email, phone, userId);

        // Add a new document with a generated ID
        db.collection("applications")
                .add(application)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Application submitted successfully", Toast.LENGTH_SHORT).show();
                    finish(); // Close the form activity
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error submitting application", Toast.LENGTH_SHORT).show();
                });
    }
}