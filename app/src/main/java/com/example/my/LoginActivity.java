package com.example.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button signInButton;
    private TextView signUpText;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db; // For Firestore

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        signInButton = findViewById(R.id.signInButton);
        signUpText = findViewById(R.id.signUpText);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance(); // Initialize Firestore

        // Sign In Button click listener
        signInButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please enter both email and password.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Try to sign in with the provided credentials
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(LoginActivity.this, task -> {
                        if (task.isSuccessful()) {
                            // Sign-in successful, navigate to HomeActivity
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            finish();
                        } else {
                            // If sign-in fails, show error
                            Toast.makeText(LoginActivity.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        });

        // Sign up TextView click listener
        signUpText.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please enter both email and password.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create a new account with the provided email and password
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(LoginActivity.this, task -> {
                        if (task.isSuccessful()) {
                            // Account created successfully, get the user ID
                            FirebaseUser user = mAuth.getCurrentUser();

                            // Add additional user information to Firestore
                            if (user != null) {
                                String userId = user.getUid();  // Unique user ID
                                User userProfile = new User(email);  // A simple User object with email

                                // Save to Firestore
                                db.collection("users").document(userId) // Store under users collection
                                        .set(userProfile)
                                        .addOnSuccessListener(aVoid -> {
                                            // Navigate to HomeActivity after successful account creation
                                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                            finish();
                                        })
                                        .addOnFailureListener(e -> {
                                            // If Firestore save fails, show error
                                            Toast.makeText(LoginActivity.this, "Error saving user data: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                        });
                            }
                        } else {
                            // If account creation fails, show error
                            Toast.makeText(LoginActivity.this, "Account creation failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        });
    }

    // Simple User class to represent user data in Firestore
    public static class User {
        private String email;

        public User(String email) {
            this.email = email;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
