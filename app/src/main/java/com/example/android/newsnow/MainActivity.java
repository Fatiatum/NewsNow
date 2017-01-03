package com.example.android.newsnow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);

        // Find the View that shows the sports category
        TextView sports = (TextView) findViewById(R.id.sport);

        // Set a click listener on that View
        sports.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the sports category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the SportsActivity
                Intent sportsIntent = new Intent(MainActivity.this, SportActivity.class);

                // Start the new activity
                startActivity(sportsIntent);
            }
        });
    }
}