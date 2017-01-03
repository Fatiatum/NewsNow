package com.example.android.newsnow;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SportActivity extends AppCompatActivity {

    private NewsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_item);

        // create a list of news
        ArrayList<News> news = new ArrayList<News>();

        news.add(new News("First News", "description", "ABC", R.drawable.news_img));
        news.add(new News("Second News", "description", "CBS", R.drawable.news_img));
        news.add(new News("Third News", "description", "SkySports", R.drawable.news_img));
        news.add(new News("Forth News", "description", "CNN", R.drawable.news_img));

        Log.d("news test", news.get(0).getTitle());

        // find the listView object in view of this Activity
        ListView textView = (ListView) findViewById(R.id.list);
        // create an adapter whose data is a list of news
        mAdapter = new NewsAdapter(this, news);

        // allows the listView to use the adapter created above
        textView.setAdapter(mAdapter);


        Log.d("passou 4", "debug on SportActivity : line 38");
    }
}
