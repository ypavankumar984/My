package com.example.my;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private TextView signUpTextView;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        signUpTextView = findViewById(R.id.signUpTextView);

        loginButton.setOnClickListener(v -> handleLogin());

        // This line makes sure clicking on "Sign Up" opens the SignUpActivity
        signUpTextView.setOnClickListener(v -> {
            // Navigate to SignUpActivity
            startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
        });
    }

    private void handleLogin() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Please enter both email and password.", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null && user.isEmailVerified()) {
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            finish();
                        } else if (user != null) {
                            user.sendEmailVerification()
                                    .addOnSuccessListener(aVoid -> Toast.makeText(LoginActivity.this, "Verification email sent. Please check your inbox.", Toast.LENGTH_LONG).show())
                                    .addOnFailureListener(e -> Toast.makeText(LoginActivity.this, "Error sending verification email: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                            mAuth.signOut();
                            Toast.makeText(LoginActivity.this, "Please verify your email to log in.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        String errorMessage = task.getException() != null ? task.getException().getMessage() : "Unknown error";
                        Toast.makeText(LoginActivity.this, "Login failed: " + errorMessage, Toast.LENGTH_LONG).show();
                    }
                });
    }
}
