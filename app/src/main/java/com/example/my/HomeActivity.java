package com.example.my;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView userEmailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        userEmailTextView = findViewById(R.id.userEmailTextView);

        // Check if the user is signed in
        if (mAuth.getCurrentUser() != null) {
            userEmailTextView.setText("Welcome, " + mAuth.getCurrentUser().getEmail());
        } else {
            // If no user is signed in, navigate to LoginActivity
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();
        }
    }
}
