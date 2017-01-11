package com.example.android.newsnow;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(Context context, ArrayList<News> news) {
        super(context, 0, news);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.news_feed, parent, false);
        }

        News currentNews = getItem(position);

        TextView titleTextView = (TextView) listItemView.findViewById(R.id.news_title_view);
        titleTextView.setText(currentNews.getTitle());

        TextView sourceTextView = (TextView) listItemView.findViewById(R.id.news_source_view);
        sourceTextView.setText(currentNews.getSource());

        if (currentNews.getDate() != "null") {
            TextView dateTextView = (TextView) listItemView.findViewById(R.id.news_date_view);
            dateTextView.setText(formatDate(currentNews.getDate()));

            TextView timeTextView = (TextView) listItemView.findViewById(R.id.news_time_view);
            timeTextView.setText(formatTime(currentNews.getDate()));
        }

        WebView image = (WebView) listItemView.findViewById(R.id.image);
        image.layout(0,0,5,5);
        image.loadUrl(currentNews.getmSourceImage());

        return listItemView;
    }

    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(String date) {
        return date.substring(0, 10);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(String date) {
        return date.substring(11, 16);
    }
}