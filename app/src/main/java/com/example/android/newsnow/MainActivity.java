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

        // Create a new intent to open the SportsActivity
        final Intent categoryIntent = new Intent(MainActivity.this, CategoryActivity.class);

        // Find the View that shows the sports category
        TextView business = (TextView) findViewById(R.id.business);
        business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryIntent.putExtra("category","business");
                startActivity(categoryIntent);
            }
        });

        // Find the View that shows the sports category
        TextView entertainment = (TextView) findViewById(R.id.entertainment);
        entertainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryIntent.putExtra("category","entertainment");
                startActivity(categoryIntent);
            }
        });

        // Find the View that shows the sports category
        TextView gaming = (TextView) findViewById(R.id.gaming);
        gaming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryIntent.putExtra("category","gaming");
                startActivity(categoryIntent);
            }
        });

        // Find the View that shows the sports category
        TextView general = (TextView) findViewById(R.id.general);
        general.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryIntent.putExtra("category","general");
                startActivity(categoryIntent);
            }
        });

        // Find the View that shows the sports category
        TextView music = (TextView) findViewById(R.id.music);
        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryIntent.putExtra("category","music");
                startActivity(categoryIntent);
            }
        });

        // Find the View that shows the sports category
        TextView san = (TextView) findViewById(R.id.science_and_nature);
        san.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryIntent.putExtra("category","science-and-nature");
                startActivity(categoryIntent);
            }
        });

        // Find the View that shows the sports category
        TextView sports = (TextView) findViewById(R.id.sport);
        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryIntent.putExtra("category","sport");
                startActivity(categoryIntent);
            }
        });

        // Find the View that shows the sports category
        TextView technology = (TextView) findViewById(R.id.technology);
        technology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryIntent.putExtra("category","technology");
                startActivity(categoryIntent);
            }
        });
    }
}