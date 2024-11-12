package com.example.my;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button signUpButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        signUpButton = findViewById(R.id.signUpButton);

        signUpButton.setOnClickListener(v -> handleSignUp());
    }

    private void handleSignUp() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(SignUpActivity.this, "Please enter both email and password.", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            // Send verification email
                            user.sendEmailVerification()
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(SignUpActivity.this, "Verification email sent. Please check your inbox.", Toast.LENGTH_LONG).show();
                                        Toast.makeText(SignUpActivity.this, "Account created successfully!", Toast.LENGTH_SHORT).show();
                                        // Redirect to LoginActivity
                                        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                        finish();
                                    })
                                    .addOnFailureListener(e -> Toast.makeText(SignUpActivity.this, "Error sending verification email: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                        }
                    } else {
                        Toast.makeText(SignUpActivity.this, "Sign-up failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
