package com.example.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentReference;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button signInButton;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth and Firestore
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        signInButton = findViewById(R.id.signInButton);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                                // Optionally, store the user data in Firestore (e.g., create a user document if not exists)
                                if (user != null) {
                                    String userId = user.getUid();
                                    DocumentReference userRef = db.collection("users").document(userId);

                                    userRef.get()
                                            .addOnCompleteListener(task1 -> {
                                                if (!task1.isSuccessful() || !task1.getResult().exists()) {
                                                    // If the user doesn't exist in Firestore, create a new document
                                                    userRef.set(new User(user.getEmail()))
                                                            .addOnSuccessListener(aVoid -> {
                                                                Toast.makeText(LoginActivity.this, "User created in Firestore", Toast.LENGTH_SHORT).show();
                                                            })
                                                            .addOnFailureListener(e -> {
                                                                Toast.makeText(LoginActivity.this, "Error creating user in Firestore", Toast.LENGTH_SHORT).show();
                                                            });
                                                }
                                            });
                                }

                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                finish();
                            } else {
                                // If sign-in fails, try to create a new account
                                mAuth.createUserWithEmailAndPassword(email, password)
                                        .addOnCompleteListener(LoginActivity.this, task1 -> {
                                            if (task1.isSuccessful()) {
                                                // Account created successfully, navigate to HomeActivity
                                                FirebaseUser user = mAuth.getCurrentUser();

                                                if (user != null) {
                                                    String userId = user.getUid();
                                                    DocumentReference userRef = db.collection("users").document(userId);

                                                    userRef.set(new User(user.getEmail()))
                                                            .addOnSuccessListener(aVoid -> {
                                                                Toast.makeText(LoginActivity.this, "User created in Firestore", Toast.LENGTH_SHORT).show();
                                                            })
                                                            .addOnFailureListener(e -> {
                                                                Toast.makeText(LoginActivity.this, "Error creating user in Firestore", Toast.LENGTH_SHORT).show();
                                                            });
                                                }

                                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                                finish();
                                            } else {
                                                // If account creation fails, show error
                                                Toast.makeText(LoginActivity.this, "Authentication failed. " + task1.getException().getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        });
                            }
                        });
            }
        });
    }
}
