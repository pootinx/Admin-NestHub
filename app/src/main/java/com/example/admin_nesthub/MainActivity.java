package com.example.admin_nesthub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null) {
            // If the user is not logged in, navigate to the LoginActivity
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish(); // finish() ensures the user cannot navigate back to MainActivity using the back button
        }

        CardView addPropertyButton = findViewById(R.id.addbtn);
        addPropertyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the button click to open the AddProperty activity
                Intent intent = new Intent(MainActivity.this, AddProperty.class);
                startActivity(intent);
            }
        });

        CardView viewPropertyButton = findViewById(R.id.viewproperties);
        viewPropertyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the button click to open the AddProperty activity
                Intent intent = new Intent(MainActivity.this, ViewProperties.class);
                startActivity(intent);
            }
        });
    }
}
