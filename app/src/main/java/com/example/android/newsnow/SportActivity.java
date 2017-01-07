package com.example.android.newsnow;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class SportActivity extends AppCompatActivity {

    private SourceAdapter mAdapter;

    /** URL for earthquake data from the USGS dataset */
    private static final String REQUEST_URL =
            "https://newsapi.org/v1/sources?language=en&category=sport";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_item);

        // find the listView object in view of this Activity
        ListView listView = (ListView) findViewById(R.id.list);
        // create an adapter whose data is a list of news
        mAdapter = new SourceAdapter(this, new ArrayList<NewsSource>());

        // allows the listView to use the adapter created above
        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                NewsSource currentSource = mAdapter.getItem(position);
                Intent newsIntent = new Intent(SportActivity.this, NewsActivity.class);
                newsIntent.putExtra("sourceId", currentSource.getId());
                startActivity(newsIntent);
            }
        });

        // Start the AsyncTask to fetch the sources data
        SportsAsyncTask task = new SportsAsyncTask ();
        task.execute(REQUEST_URL);
    }

    private class SportsAsyncTask extends AsyncTask<String, Void, List<NewsSource>>{
        @Override
        protected List<NewsSource> doInBackground(String... urls) {
            if(urls.length < 1 || urls[0] == null)
                return null;

            List<NewsSource> result = SourceRequest.fetchSources(urls[0]);
            return result;
        }

        @Override
        protected void onPostExecute(List<NewsSource> sources) {
            mAdapter.clear();

            if(sources != null && !sources.isEmpty()){
                mAdapter.addAll(sources);
            }
        }
    }
}
