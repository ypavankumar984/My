package com.example.my;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.analytics.FirebaseAnalytics; // Import Firebase Analytics

public class MainActivity extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;  // Firebase Analytics instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Analytics
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        // Initialize the button
        Button btnGoToLogin = findViewById(R.id.btnGoToLogin);

        // Log an event when the MainActivity is created (User engagement event)
        logMainActivityOpenedEvent();

        // Set an OnClickListener on the button to navigate to LoginActivity
        btnGoToLogin.setOnClickListener(v -> {
            // Log an event when the login button is clicked (for Firebase Analytics)
            logLoginButtonClickedEvent();

            // Navigate to LoginActivity
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    // Method to log event when MainActivity is opened
    private void logMainActivityOpenedEvent() {
        Bundle bundle = new Bundle();
        bundle.putString("activity_name", "MainActivity");
        mFirebaseAnalytics.logEvent("main_activity_opened", bundle);
    }

    // Method to log event when Login button is clicked
    private void logLoginButtonClickedEvent() {
        Bundle bundle = new Bundle();
        bundle.putString("button_name", "GoToLoginButton");
        mFirebaseAnalytics.logEvent("login_button_clicked", bundle);
    }
}
