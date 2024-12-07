package com.example.finalprojectwmb.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.finalprojectwmb.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {
    private static final int PICK_IMAGE = 1; // Request code for picking an image
    private static final int CAPTURE_IMAGE = 2; // Request code for capturing an image
    private static final int REQUEST_CAMERA_PERMISSION = 3; // Request code for camera permission

    private EditText usernameDisplay, emailDisplay; // EditTexts for user data
    private ImageView profileImage; // ImageView to display the profile image
    private Button changeProfilePicture, saveButton; // Buttons for changing and saving
    private Uri imageUri; // URI for the captured image

    private FirebaseFirestore db; // Firestore instance
    private FirebaseAuth mAuth; // Firebase Auth instance

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize views
        initializeViews(view);

        // Initialize Firestore and Firebase Auth
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        // Load user data from Firestore
        loadUserData();

        // Set up button listeners
        setButtonListeners();

        return view;
    }

    private void initializeViews(View view) {
        usernameDisplay = view.findViewById(R.id.usernameEdit);
        emailDisplay = view.findViewById(R.id.emailEdit);
        profileImage = view.findViewById(R.id.profileImage);
        changeProfilePicture = view.findViewById(R.id.changeProfilePicture);
        saveButton = view.findViewById(R.id.saveButton);
    }

    private void setButtonListeners() {
        changeProfilePicture.setOnClickListener(v -> showImageOptions());
        saveButton.setOnClickListener(v -> saveProfileData());
    }

    private void loadUserData() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid(); // Get the current user's ID
            String email = user.getEmail(); // Get the email from Firebase Authentication
            DocumentReference docRef = db.collection("users").document(userId);
            docRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    if (task.getResult() != null && task.getResult().exists()) {
                        String username = task.getResult().getString("username");
                        String profileImageBase64 = task.getResult().getString("profileImage");

                        // Set the data to the EditTexts
                        usernameDisplay.setText(username);
                        emailDisplay.setText(email); // Set the email from Firebase Auth
                        if (profileImageBase64 != null) {
                            // Decode Base64 string to Bitmap and set it to ImageView
                            byte[] decodedString = Base64.decode(profileImageBase64, Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            profileImage.setImageBitmap(decodedByte);
                        }
                    } else {
                        Toast.makeText(getContext(), "User document does not exist", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Error loading user data: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("ProfileFragment", "Error loading user data", task.getException());
                }
            });
        } else {
            Toast.makeText(getContext(), "No user is logged in", Toast.LENGTH_SHORT).show();
        }
    }

    private void showImageOptions() {
        String[] options = {"Camera", "Gallery"};
        new androidx.appcompat.app.AlertDialog.Builder(getContext())
                .setTitle("Choose Profile Picture")
                .setItems(options, (dialog, which) -> {
                    if (which == 0) {
                        // Check for camera permission
                        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                        } else {
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
        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            try {
                File photoFile = createImageFile();
                if (photoFile != null) {
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(cameraIntent, CAPTURE_IMAGE);
                }
            } catch (IOException ex) {
                Toast.makeText(getContext(), "Error creating file", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(null);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        imageUri = FileProvider.getUriForFile(getContext(), getActivity().getApplicationContext().getPackageName() + ".fileprovider", image);
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            profileImage.setImageURI(selectedImageUri); // Set the selected image to the ImageView
            imageUri = selectedImageUri; // Store the URI for later use
        } else if (requestCode == CAPTURE_IMAGE && resultCode == RESULT_OK) {
            profileImage.setImageURI(imageUri); // Set the captured image to the ImageView
        } else {
            Toast.makeText(getContext(), "Failed to load image", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveProfileData() {
        String username = usernameDisplay.getText().toString();
        String email = emailDisplay.getText().toString();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid(); // Get the current user's ID
            Map<String, Object> userData = new HashMap<>();
            userData.put("username", username);
            userData.put("email", email);

            // Convert image to Base64 and store it
            if (imageUri != null) {
                try {
                    byte[] imageData = getImageData(imageUri);
                    String base64Image = Base64.encodeToString(imageData, Base64.DEFAULT);
                    userData.put("profileImage", base64Image);
                } catch (IOException e) {
                    Toast.makeText(getContext(), "Error processing image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            // Save to Firestore
            db.collection("users").document(userId)
                    .set(userData, SetOptions.merge())
                    .addOnSuccessListener(aVoid -> Toast.makeText(getContext(), "Profile saved", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(getContext(), "Error saving profile: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        } else {
            Toast.makeText(getContext(), "No user is logged in", Toast.LENGTH_SHORT).show();
        }
    }

    private byte[] getImageData(Uri imageUri) throws IOException {
        InputStream inputStream = getActivity().getContentResolver().openInputStream(imageUri);
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

        // Compress the bitmap to reduce its size
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int quality = 100; // Start with the highest quality
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream);

        // Check the size and reduce quality if necessary
        while (byteArrayOutputStream.toByteArray().length > 1024 * 1024) { // 1 MB
            byteArrayOutputStream.reset(); // Clear the output stream
            quality -= 10; // Reduce quality by 10 for more aggressive compression
            if (quality < 0) {
                break; // Prevent quality from going below 0
            }
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream);
        }

        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera(); // Permission granted, open camera
            } else {
                Toast.makeText(getContext(), "Camera permission is required to take pictures", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
