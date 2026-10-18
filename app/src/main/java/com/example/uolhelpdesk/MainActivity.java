package com.example.uolhelpdesk; // <-- change to YOUR package name

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Key constant used to carry the name to RequestActivity (Scenario 2, step 1)
    public static final String EXTRA_NAME = "com.example.uolhelpdesk.EXTRA_NAME";

    // Scenario 1, step 5: one safe helper used by EVERY implicit Intent
    public static void openIntent(Context context, Intent intent) {
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "No app found for this action", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText etName = findViewById(R.id.etName);
        Button btnWebsite = findViewById(R.id.btnWebsite);
        Button btnCall = findViewById(R.id.btnCall);
        Button btnMap = findViewById(R.id.btnMap);
        Button btnRequest = findViewById(R.id.btnRequest);

        // Step 2: website -> browser
        btnWebsite.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.uol.edu.pk"));
            openIntent(MainActivity.this, intent);
        });

        // Step 3: dialer with number pre-typed (no call is placed)
        btnCall.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:04212345678"));
            openIntent(MainActivity.this, intent);
        });

        // Step 4: maps app
        btnMap.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=University+of+Lahore"));
            openIntent(MainActivity.this, intent);
        });

        // Scenario 2, step 1: validate name, then EXPLICIT intent with putExtra
        btnRequest.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            if (name.isEmpty()) {
                etName.setError("Please enter your name");
                return;
            }
            Intent intent = new Intent(MainActivity.this, RequestActivity.class);
            intent.putExtra(EXTRA_NAME, name);
            startActivity(intent);
        });
    }
}