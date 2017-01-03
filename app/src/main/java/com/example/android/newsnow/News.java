package com.example.android.newsnow;

public class News {

    private static final int NO_IMAGE = -1;
    private String mTitle, mDescription, mSource;
    private int mImageResourceId = NO_IMAGE;

    public News(String title, String description, String source, int resourceID) {
        mTitle = title;
        mDescription = description;
        mSource = source;
        mImageResourceId = resourceID;
    }

    public String getDescription() {
        return mDescription;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

    public String getTitle() {
        return mTitle;
    }

    public boolean hasImage() {
        return mImageResourceId != NO_IMAGE;
    }

    public String getSource() {
        return mSource;
    }
}
