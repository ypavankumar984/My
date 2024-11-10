package com.example.my;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//master rey 123
        // Find the TextView in the layout and set its text to "Hello, World!"
        TextView helloTextView = findViewById(R.id.helloTextView);
        helloTextView.setText("Roshan123!");
    }
}