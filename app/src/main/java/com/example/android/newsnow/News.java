package com.example.android.newsnow;

import java.util.Timer;

public class News {

    private static final int NO_IMAGE = -1;
    private String mAuthor, mTitle, mDescription, mUrl, mUrltoImage, mDate, mSource;
    private int mImageResourceId = NO_IMAGE;

    public News(String author, String title, String description, String url, String urlToImage, String date, String source) {
        mAuthor = author;
        mTitle = title;
        mDescription = description;
        mUrl = url;
        mUrltoImage = urlToImage;
        mDate = date;
        mSource = source;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getDate() {
        return mDate;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getUrltoImage() {
        return mUrltoImage;
    }

    public boolean hasImage() {
        return mImageResourceId != NO_IMAGE;
    }

    public String getSource() {
        return mSource;
    }

}
