package com.example.finalprojectwmb.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalprojectwmb.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private Button login;
    private EditText userName, passWord;
    private FirebaseAuth mAuth;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = findViewById(R.id.username);
        passWord = findViewById(R.id.password);
        login = findViewById(R.id.login);
        TextView registerLink = findViewById(R.id.register);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        // Load saved email if it exists
        String savedEmail = sharedPreferences.getString("email", "");
        userName.setText(savedEmail);

        // Register link listener
        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        // Login button listener
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = userName.getText().toString().trim();
                String password = passWord.getText().toString().trim();

                if (!isValidEmail(email)) {
                    Toast.makeText(MainActivity.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(MainActivity.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Sign in with Firebase Auth
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity.this, task -> {
                            if (task.isSuccessful()) {
                                // Login successful
                                Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                                // Save email to SharedPreferences
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("email", email);
                                editor.apply();

                                // Start SearchActivity on successful login
                                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                                startActivity(intent);
                                finish();  // Optional: Close MainActivity so user can't go back to login screen
                            } else {
                                // Login failed
                                Toast.makeText(MainActivity.this, "Login Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    // Helper method to validate email format
    private boolean isValidEmail(CharSequence email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
