package com.example.finalprojectwmb.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText; // Change TextView to EditText
import android.widget.ImageView;
import android.widget.Toast;

import com.example.finalprojectwmb.R;

public class ProfileActivity extends AppCompatActivity {
    private static final int PICK_IMAGE = 1; // Request code for picking an image
    private EditText usernameDisplay, emailDisplay, passwordDisplay, aboutMeDisplay; // Change to EditText
    private ImageView profileImage; // ImageView to display the profile image
    private Button changeProfilePicture, saveButton; // Buttons for changing and saving

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize views
        usernameDisplay = findViewById(R.id.usernameEdit); // Update to match EditText ID
        emailDisplay = findViewById(R.id.emailEdit); // Update to match EditText ID
        passwordDisplay = findViewById(R.id.passwordEdit); // Update to match EditText ID
        aboutMeDisplay = findViewById(R.id.aboutMeEdit); // Update to match EditText ID
        profileImage = findViewById(R.id.profileImage);
        changeProfilePicture = findViewById(R.id.changeProfilePicture);
        saveButton = findViewById(R.id.saveButton);
        // Retrieve user data from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "Guest"); // Default to "Guest" if not found
        String email = sharedPreferences.getString("email", ""); // Default to empty if not found
        String password = sharedPreferences.getString("password", ""); // Default to empty if not found
        String aboutMe = sharedPreferences.getString("aboutMe", "");


        // Set the data to the EditTexts
        usernameDisplay.setText(username);
        emailDisplay.setText(email);
        passwordDisplay.setText(password);
        aboutMeDisplay.setText(aboutMe);

        // Set up button listeners
        changeProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfileData();
            }
        });
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            profileImage.setImageURI(imageUri); // Set the selected image to the ImageView
        } else {
            Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveProfileData() {
        // Here you would save the profile data to your database or shared preferences
        String username = usernameDisplay.getText().toString();
        String email = emailDisplay.getText().toString();
        String password = passwordDisplay.getText().toString();
        String aboutMe = aboutMeDisplay.getText().toString();

        // Save to SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.putString("email", email);
        editor.putString("password", password); // Be cautious with storing passwords
        editor.putString("aboutMe", aboutMe);
        editor.apply();

        // Example: Show a toast message (replace with actual save logic)
        Toast.makeText(this, "Profile saved:\nUsername: " + username + "\nEmail: " + email, Toast.LENGTH_SHORT).show();
    }
}