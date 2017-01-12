package com.example.android.newsnow;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NewsActivity extends AppCompatActivity {

    // Adapter created to aid the inflate of data in list view
    private NewsAdapter mAdapter;
    // Url parts needed to make news request for a specific category
    private static final String URL_HEADER = "https://newsapi.org/v1/articles?source=";
    private static final String URL_API_KEY = "&apiKey=e84cf03af6eb4b839d0fb49db1cc641a";

    /**
     * Set content in news_item layout
     * Get all extra params from intent creation
     * Creates new Adapter instance and sets it to listView
     * Creates an array of source's urls in order to make every source request and fill the final news array with all news
     * Starts AsyncTask to make API request
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_item);

        Intent intent = getIntent();
        ArrayList<String> mAllSources = intent.getStringArrayListExtra("sources");
        ArrayList<String> mSourceImage = intent.getStringArrayListExtra("sourcesImages");

        ListView listView = (ListView) findViewById(R.id.list);
        mAdapter = new NewsAdapter(this, new ArrayList<News>());
        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri newsUri = Uri.parse(mAdapter.getItem(position).getUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

        String urls[] = new String[mAllSources.size() * 2];
        for (int i = 0; i < mAllSources.size() * 2; i += 2) {
            urls[i] = makeUrl(mAllSources.get(i / 2));
            urls[i + 1] = mSourceImage.get(i / 2);
        }

        NewsAsyncTask task = new NewsAsyncTask();
        task.execute(urls);
    }

    /**
     * Build URL for make News Request to the API
     * @return URL
     */
    private String makeUrl(String sourceId) {
        return URL_HEADER + sourceId + URL_API_KEY;
    }

    /**
     * NewsAsyncTask is a asynchronous thread that is responsible to make the request to the API
     *
     */
    private class NewsAsyncTask extends AsyncTask<String, Void, List<News>> {

        /**
         * makes the request and fetches the data from the request response for every url in the urls array(every source for specific category)
         * sorts the result by date (from newest to oldest)
         * @param urls
         * @return
         */
        @Override
        protected List<News> doInBackground(String... urls) {
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            List<News> result = NewsRequest.fetchNews(urls[0], urls[1]);

            for (int i = 2; i < urls.length; i += 2) {
                result.addAll(NewsRequest.fetchNews(urls[i], urls[i + 1]));
            }
            Collections.sort(result, new Comparator<News>() {
                @Override
                public int compare(News o1, News o2) {
                    return o2.getDate().compareTo(o1.getDate());
                }
            });
            return result;
        }

        /**
         * Makes sure mAdapter is empty and then pastes result fetched from request in the adapter
         * @param data
         */
        @Override
        protected void onPostExecute(List<News> data) {
            mAdapter.clear();
            if (data != null && !data.isEmpty()) {
                mAdapter.addAll(data);
            }
        }
    }
}
