package com.example.android.newsnow;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final String REQUEST_URL = "https://newsapi.org/v1/sources?language=en&category=";
    private String mCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);

        // Find the View that shows the sports category
        TextView business = (TextView) findViewById(R.id.business);
        business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCategory = "business";
                MainActivity.CategoryAsyncTask task = new MainActivity.CategoryAsyncTask();
                task.execute(makeUrl());
            }
        });

        // Find the View that shows the sports category
        TextView entertainment = (TextView) findViewById(R.id.entertainment);
        entertainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCategory = "entertainment";
                MainActivity.CategoryAsyncTask task = new MainActivity.CategoryAsyncTask();
                task.execute(makeUrl());
            }
        });

        // Find the View that shows the sports category
        TextView gaming = (TextView) findViewById(R.id.gaming);
        gaming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCategory = "gaming";
                MainActivity.CategoryAsyncTask task = new MainActivity.CategoryAsyncTask();
                task.execute(makeUrl());
            }
        });

        // Find the View that shows the sports category
        TextView general = (TextView) findViewById(R.id.general);
        general.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCategory = "general";
                MainActivity.CategoryAsyncTask task = new MainActivity.CategoryAsyncTask();
                task.execute(makeUrl());
            }
        });

        // Find the View that shows the sports category
        TextView music = (TextView) findViewById(R.id.music);
        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCategory = "music";
                MainActivity.CategoryAsyncTask task = new MainActivity.CategoryAsyncTask();
                task.execute(makeUrl());
            }
        });

        // Find the View that shows the sports category
        TextView san = (TextView) findViewById(R.id.science_and_nature);
        san.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCategory = "science-and-nature";
                MainActivity.CategoryAsyncTask task = new MainActivity.CategoryAsyncTask();
                task.execute(makeUrl());
            }
        });

        // Find the View that shows the sports category
        TextView sports = (TextView) findViewById(R.id.sport);
        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCategory = "sport";
                MainActivity.CategoryAsyncTask task = new MainActivity.CategoryAsyncTask();
                task.execute(makeUrl());
            }
        });

        // Find the View that shows the sports category
        TextView technology = (TextView) findViewById(R.id.technology);
        technology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCategory = "technology";
                MainActivity.CategoryAsyncTask task = new MainActivity.CategoryAsyncTask();
                task.execute(makeUrl());
            }
        });

    }

    private String makeUrl() {
        return REQUEST_URL + mCategory;
    }

    private class CategoryAsyncTask extends AsyncTask<String, Void, List<NewsSource>> {

        @Override
        protected List<NewsSource> doInBackground(String... urls) {
            if (urls.length < 1 || urls[0] == null)
                return null;

            List<NewsSource> result = SourceRequest.fetchSources(urls[0]);
            return result;
        }

        @Override
        protected void onPostExecute(List<NewsSource> sources) {
            Intent newsIntent = new Intent(MainActivity.this, NewsActivity.class);
            ArrayList<String> allSources = new ArrayList<>();
            ArrayList<String> sourcesImages = new ArrayList<>();
            for (int i = 0; i < sources.size(); i++) {
                allSources.add(sources.get(i).getId());
                sourcesImages.add(sources.get(i).getmSmallLogo());
            }
            newsIntent.putExtra("sources", allSources);
            newsIntent.putExtra("sourcesImages", sourcesImages);
            startActivity(newsIntent);
        }
    }
}