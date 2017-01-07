package com.example.android.newsnow;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity{

    private String mSourceId;

    private NewsAdapter mAdapter;

    private static final String URL_HEADER =
            "https://newsapi.org/v1/articles?source=";
    private static final String URL_APIKEY = "&apiKey=e84cf03af6eb4b839d0fb49db1cc641a";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_item);

        Intent intent = getIntent();
        mSourceId = intent.getStringExtra("sourceId");

        ListView listView = (ListView) findViewById(R.id.list);
        mAdapter = new NewsAdapter(this, new ArrayList<News>());
        listView.setAdapter(mAdapter);
        NewsAsyncTask task = new NewsAsyncTask();
        task.execute(URL_HEADER + mSourceId + URL_APIKEY);
    }

    private class NewsAsyncTask extends AsyncTask<String, Void, List<News>>{
        @Override
        protected List<News> doInBackground(String... urls) {
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            List<News> result = NewsRequest.fetchNews(urls[0], mSourceId);
            return result;
        }

        @Override
        protected void onPostExecute(List<News> data) {
            mAdapter.clear();
            if(data != null && !data.isEmpty()) {
                mAdapter.addAll(data);
            }
        }
    }
}
