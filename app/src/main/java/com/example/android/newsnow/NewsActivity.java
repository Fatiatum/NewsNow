package com.example.android.newsnow;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class NewsActivity extends AppCompatActivity {

    private ArrayList<String> mAllSources;
    private NewsAdapter mAdapter;
    private ArrayList<String> mSourceImage;

    private static final String URL_HEADER = "https://newsapi.org/v1/articles?source=";
    private static final String URL_API_KEY = "&apiKey=e84cf03af6eb4b839d0fb49db1cc641a";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_item);

        Intent intent = getIntent();
        mAllSources = intent.getStringArrayListExtra("sources");
        mSourceImage = intent.getStringArrayListExtra("sourcesImages");

        ListView listView = (ListView) findViewById(R.id.list);
        mAdapter = new NewsAdapter(this, new ArrayList<News>());
        listView.setAdapter(mAdapter);

        String urls[] = new String[mAllSources.size() * 2];
        for (int i = 0; i < mAllSources.size() * 2; i += 2) {
            urls[i] = makeUrl(mAllSources.get(i / 2));
            urls[i + 1] = mSourceImage.get(i / 2);
        }
        NewsAsyncTask task = new NewsAsyncTask();
        task.execute(urls);
    }

    private String makeUrl(String sourceId) {
        return URL_HEADER + sourceId + URL_API_KEY;
    }

    private class NewsAsyncTask extends AsyncTask<String, Void, List<News>> {
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

        @Override
        protected void onPostExecute(List<News> data) {
            mAdapter.clear();
            if (data != null && !data.isEmpty()) {
                mAdapter.addAll(data);
            }
        }
    }
}
