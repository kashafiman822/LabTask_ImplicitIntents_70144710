package com.example.uolhelpdesk;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RequestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        TextView tvGreeting = findViewById(R.id.tvGreeting);
        EditText etPhone = findViewById(R.id.etPhone);
        Button btnCallNumber = findViewById(R.id.btnCallNumber);
        EditText etLink = findViewById(R.id.etLink);
        Button btnOpenLink = findViewById(R.id.btnOpenLink);

        // Step 2: read the name, fall back to "Student"
        String name = getIntent().getStringExtra(MainActivity.EXTRA_NAME);
        if (name == null || name.trim().isEmpty()) {
            name = "Student";
        }
        tvGreeting.setText("Hello, " + name + "! How can we help you today?");

        // Step 3: validate phone (11 digits, starts with 03), then dial
        btnCallNumber.setOnClickListener(v -> {
            String number = etPhone.getText().toString().trim();
            if (!number.matches("03\\d{9}")) {
                etPhone.setError("Enter 11 digits starting with 03");
                return;
            }
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));
            MainActivity.openIntent(RequestActivity.this, intent);
        });

        // Step 4: validate link, add https:// if missing, then open
        btnOpenLink.setOnClickListener(v -> {
            String link = etLink.getText().toString().trim();
            if (link.isEmpty()) {
                etLink.setError("Please enter a link");
                return;
            }
            String lower = link.toLowerCase();
            if (!lower.startsWith("http://") && !lower.startsWith("https://")) {
                link = "https://" + link;
            }
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
            MainActivity.openIntent(RequestActivity.this, intent);
        });
    }
}
