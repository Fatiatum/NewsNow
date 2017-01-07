package com.example.android.newsnow;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    private SourceAdapter mAdapter;

    /** URL for earthquake data from the USGS dataset */
    private static final String REQUEST_URL =
            "https://newsapi.org/v1/sources?language=en&category=";
    private String mCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        mCategory = intent.getStringExtra("category");
        /*
        ListView listView = (ListView) findViewById(R.id.list);
        mAdapter = new SourceAdapter(this, new ArrayList<NewsSource>());
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                NewsSource currentSource = mAdapter.getItem(position);
                Intent newsIntent = new Intent(CategoryActivity.this, NewsActivity.class);
                newsIntent.putExtra("sourceId", currentSource.getId());
                startActivity(newsIntent);
            }
        });
*/
        // Start the AsyncTask to fetch the sources data
        CategoryAsyncTask task = new CategoryAsyncTask();
        task.execute(makeUrl());
    }

    private String makeUrl() {
        return REQUEST_URL + mCategory;
    }

    private class CategoryAsyncTask extends AsyncTask<String, Void, List<NewsSource>>{

        @Override
        protected List<NewsSource> doInBackground(String... urls) {
            if(urls.length < 1 || urls[0] == null)
                return null;

            List<NewsSource> result = SourceRequest.fetchSources(urls[0]);
            return result;
        }

        @Override
        protected void onPostExecute(List<NewsSource> sources) {
            Intent newsIntent = new Intent(CategoryActivity.this, NewsActivity.class);
            ArrayList<String> allSources = new ArrayList<>();
            for(int i = 0; i < sources.size(); i++){
                allSources.add(sources.get(i).getId());
            }
            newsIntent.putExtra("sources", allSources);
            startActivity(newsIntent);
        }
    }
}
