package com.example.finalprojectwmb.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.finalprojectwmb.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProfileActivity extends AppCompatActivity {
    private static final int PICK_IMAGE = 1; // Request code for picking an image
    private static final int CAPTURE_IMAGE = 2; // Request code for capturing an image
    private static final int REQUEST_CAMERA_PERMISSION = 3; // Request code for camera permission
    private EditText usernameDisplay, emailDisplay, passwordDisplay; // Removed aboutMeDisplay
    private ImageView profileImage; // ImageView to display the profile image
    private Button changeProfilePicture, saveButton; // Buttons for changing and saving
    private Uri imageUri; // URI for the captured image

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize views
        usernameDisplay = findViewById(R.id.usernameEdit); // Update to match EditText ID
        emailDisplay = findViewById(R.id.emailEdit); // Update to match EditText ID
        passwordDisplay = findViewById(R.id.passwordEdit); // Update to match EditText ID
        profileImage = findViewById(R.id.profileImage);
        changeProfilePicture = findViewById(R.id.changeProfilePicture);
        saveButton = findViewById(R.id.saveButton);

        // Retrieve user data from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", ""); // Default to empty if not found
        String email = sharedPreferences.getString("email", ""); // Default to empty if not found
        String password = sharedPreferences.getString("password", ""); // You might want to handle this differently

        // Set the data to the EditTexts
        usernameDisplay.setText(username);
        emailDisplay.setText(email); // Set the email to the EditText
        passwordDisplay.setText(password);

        // Set up button listeners
        changeProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageOptions();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfileData();
            }
        });
    }

    private void showImageOptions() {
        // Show options to choose between camera and gallery
        String[] options = {"Camera", "Gallery"};
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Choose Profile Picture")
                .setItems(options, (dialog, which) -> {
                    if (which == 0) {
                        // Check for camera permission
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            // Request camera permission
                            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                        } else {
                            // Open camera
                            openCamera();
                        }
                    } else {
                        // Open gallery
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, PICK_IMAGE);
                    }
                })
                .show();
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            // Create a file to save the image
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Handle error
                Toast.makeText(this, "Error creating file", Toast.LENGTH_SHORT).show();
            }
            if (photoFile != null) {
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(cameraIntent, CAPTURE_IMAGE);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(null);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        imageUri = FileProvider.getUriForFile(this,
                getApplicationContext().getPackageName() + ".fileprovider",
                image);
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            profileImage.setImageURI(selectedImageUri); // Set the selected image to the ImageView
        } else if (requestCode == CAPTURE_IMAGE && resultCode == RESULT_OK) {
            // Set the captured image to the ImageView
            profileImage.setImageURI(imageUri);
        } else {
            Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveProfileData() {
        // Here you would save the profile data to your database or shared preferences
        String username = usernameDisplay.getText().toString();
        String email = emailDisplay.getText().toString();
        String password = passwordDisplay.getText().toString();

        // Save to SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.putString("email", email);
        editor.putString("password", password); // Be cautious with storing passwords
        editor.apply();

        // Example: Show a toast message (replace with actual save logic)
        Toast.makeText(this, "Profile saved:\nUsername: " + username + "\nEmail: " + email, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, open camera
                openCamera();
            } else {
                // Permission denied
                Toast.makeText(this, "Camera permission is required to take pictures", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
