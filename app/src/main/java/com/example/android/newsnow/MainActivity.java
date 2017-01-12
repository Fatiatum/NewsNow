package com.example.android.newsnow;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    // URL for make sources request
    private static final String REQUEST_URL = "https://newsapi.org/v1/sources?language=en&category=";
    // Save category selected by user
    private String mCategory;

    /**
     * Get all TextViews in ActivityMain and set a ClickListener in each one to know what category the user wants to see
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);

        TextView business = (TextView) findViewById(R.id.business);
        business.setOnClickListener(this);

        TextView entertainment = (TextView) findViewById(R.id.entertainment);
        entertainment.setOnClickListener(this);

        TextView gaming = (TextView) findViewById(R.id.gaming);
        gaming.setOnClickListener(this);

        TextView general = (TextView) findViewById(R.id.general);
        general.setOnClickListener(this);

        TextView music = (TextView) findViewById(R.id.music);
        music.setOnClickListener(this);

        TextView san = (TextView) findViewById(R.id.science_and_nature);
        san.setOnClickListener(this);

        TextView sports = (TextView) findViewById(R.id.sport);
        sports.setOnClickListener(this);

        TextView technology = (TextView) findViewById(R.id.technology);
        technology.setOnClickListener(this);
    }

    /**
     * Build URL for make Source Request to the API
     * @return URL
     */
    private String makeUrl() {
        return REQUEST_URL + mCategory;
    }

    /**
     * Override OnClick method of ClickListener
     * The state machine defines the mCategory param in order to identify the category selected by user
     * Start AsyncTask in order to make the source request
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.business: {
                mCategory = "business";
                break;
            }
            case R.id.entertainment: {
                mCategory = "entertainment";
                break;
            }

            case R.id.gaming: {
                mCategory = "gaming";
                break;
            }
            case R.id.general: {
                mCategory = "general";
                break;
            }

            case R.id.music: {
                mCategory = "music";
                break;
            }

            case R.id.sport: {
                mCategory = "sport";
                break;
            }
            case R.id.technology: {
                mCategory = "technology";
                break;
            }
            case R.id.science_and_nature: {
                mCategory = "science-and-nature";
                break;
            }
            default:
                break;
        }
        MainActivity.CategoryAsyncTask task = new MainActivity.CategoryAsyncTask();
        task.execute(makeUrl());
    }

    /**
     * CategoryAsyncTask is a asynchronous thread that is responsible to make the request to the API
     *
     */
    private class CategoryAsyncTask extends AsyncTask<String, Void, List<NewsSource>> {
        /**
         * makes the request and fetches the data from the request response
         * @param urls
         * @return
         */
        @Override
        protected List<NewsSource> doInBackground(String... urls) {
            if (urls.length < 1 || urls[0] == null)
                return null;

            List<NewsSource> result = SourceRequest.fetchSources(urls[0]);
            return result;
        }

        /**
         * Creates and starts the new Actiity intent and passes the array of all sources of a specific category and sources images
         * @param sources
         */
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