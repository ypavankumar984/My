package com.example.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.analytics.FirebaseAnalytics; // Import Firebase Analytics

public class MainActivity extends AppCompatActivity {

    private Button btnGoToLogin;
    private FirebaseAnalytics mFirebaseAnalytics;  // Firebase Analytics instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Analytics
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        // Initialize the button
        btnGoToLogin = findViewById(R.id.btnGoToLogin);

        // Log an event when the MainActivity is created (User engagement event)
        mFirebaseAnalytics.logEvent("main_activity_opened", null);

        // Set an OnClickListener on the button to navigate to LoginActivity
        btnGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Log an event when the login button is clicked (for Firebase Analytics)
                mFirebaseAnalytics.logEvent("login_button_clicked", null);

                // Navigate to LoginActivity
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
