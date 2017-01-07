package com.example.android.newsnow;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<News>{

    public NewsAdapter(Context context, ArrayList<News> news) {
        super(context, 0, news);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.news_feed, parent, false);
        }

        // Get the News object located at this position in the list
        News currentNews = getItem(position);

        // Find the TextView in the news_feed.xml layout with the ID news_title_view.
        TextView titleTextView = (TextView) listItemView.findViewById(R.id.news_title_view);
        // Get the news title from the currentNews object and set this text on the title TextView.
        titleTextView.setText(currentNews.getTitle());

        // Find the TextView in the news_feed.xml layout with the ID news_source_view.
        TextView sourceTextView = (TextView) listItemView.findViewById(R.id.news_source_view);
        // Get the news title from the currentNews object and set this text on the title TextView.
        sourceTextView.setText(currentNews.getSource());

        // Find the TextView in the news_feed.xml layout with the ID news_description_view.
        TextView descriptionTextView = (TextView) listItemView.findViewById(R.id.news_description_view);
        // Get the news description from the currentNews object and set this text on the title TextView.
        descriptionTextView.setText(currentNews.getDescription());

        // Find the ImageView in the news_feed.xml layout with the ID image.
        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image);
        // Check if an image is provided for this word or not
        if (currentNews.hasImage()) {
            // If an image is available, display the provided image based on the resource ID
            imageView.setImageResource(currentNews.getImageResourceId());
            // Make sure the view is visible
            imageView.setVisibility(View.VISIBLE);
        } else {
            // Otherwise hide the ImageView (set visibility to GONE)
            imageView.setVisibility(View.GONE);
        }

        return listItemView;
    }
}
