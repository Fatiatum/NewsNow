package com.example.android.newsnow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by fc__j on 04/01/2017.
 */
public class SourceAdapter extends ArrayAdapter<NewsSource> {

    public SourceAdapter(Context context, ArrayList<NewsSource> sources) {
        super(context, 0, sources);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.source_list, parent, false);
        }

        // Get the News object located at this position in the list
        NewsSource currentSource = getItem(position);

        TextView nameTextView = (TextView) listItemView.findViewById(R.id.source_name_view);
        nameTextView.setText(currentSource.getName());

        TextView descriptionTextView = (TextView) listItemView.findViewById(R.id.source_description_view);
        descriptionTextView.setText(currentSource.getDescription());

        TextView countryTextView = (TextView) listItemView.findViewById(R.id.source_country_view);
        countryTextView.setText(currentSource.getCountry());

        // Find the ImageView in the source_list.xml layout with the ID image.
        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image);
        // Check if an image is provided for this word or not
        if (currentSource.hasImage()) {
            // If an image is available, display the provided image based on the resource ID
            imageView.setImageResource(currentSource.getImageResourceId());
            // Make sure the view is visible
            imageView.setVisibility(View.VISIBLE);
        } else {
            // Otherwise hide the ImageView (set visibility to GONE)
            imageView.setVisibility(View.GONE);
        }

        return listItemView;
    }
}
