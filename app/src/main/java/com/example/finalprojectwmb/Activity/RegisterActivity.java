package com.example.finalprojectwmb.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalprojectwmb.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private Button register;
    private EditText userName, passWord, emailField;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userName = findViewById(R.id.username);
        emailField = findViewById(R.id.email);
        passWord = findViewById(R.id.password);
        register = findViewById(R.id.register);

        // Initialize Firebase Auth and Firestore
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Register button listener
        register.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String email = emailField.getText().toString().trim();
        String password = passWord.getText().toString().trim();
        String username = userName.getText().toString().trim();

        // Check for empty fields
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(username)) {
            Toast.makeText(RegisterActivity.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidEmail(email)) {
            Toast.makeText(RegisterActivity.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 6) {
            Toast.makeText(RegisterActivity.this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create user with Firebase Auth
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(RegisterActivity.this, task -> {
                    if (task.isSuccessful()) {
                        // Registration successful
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            createUserDocument(user, username);
                        }
                    } else {
                        // Registration failed
                        Toast.makeText(RegisterActivity.this, "Registration Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void createUserDocument(FirebaseUser user, String username) {
        String userId = user.getUid(); // Get the user's UID
        Map<String, Object> userData = new HashMap<>();
        userData.put("email", user.getEmail()); // Set the user's email
        userData.put("username", username); // Set the username provided during registration
        userData.put("profileImage", ""); // Initialize with an empty string or a default image URL

        db.collection("users").document(userId)
                .set(userData)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(RegisterActivity.this, "User document created successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(RegisterActivity.this, "Error creating user document: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    // Helper method to validate email format
    private boolean isValidEmail(CharSequence email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
