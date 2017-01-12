package com.example.android.newsnow;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Class responsible for inflate news_feed layout with all news fetched from request to the api
 * This adapter gets all news and inflates the list view with only the needed news to display
 */
class NewsAdapter extends ArrayAdapter<News> {

    NewsAdapter(Context context, ArrayList<News> news) {
        super(context, 0, news);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
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
/*
        WebView image = (WebView) listItemView.findViewById(R.id.image);
        image.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //image.layout(0,0,5,5);
        image.loadUrl(currentNews.getmSourceImage());
*/
        return listItemView;
    }

    /**
     * Return the formatted date string (i.e. "03-10-2016") from a String returned
     */
    private String formatDate(String date) {
        return date.substring(0, 10);
    }

    /**
     * Return the formatted date string (i.e. "14:30") from a String returned.
     */
    private String formatTime(String date) {
        return date.substring(11, 16);
    }
}