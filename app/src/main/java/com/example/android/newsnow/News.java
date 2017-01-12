package com.example.android.newsnow;

/**
 * News Object Class
 * Gets title, date, source, source image and url params that are fetched from API request
 */
class News {

    private String mTitle, mDate, mSource, mSourceImage, mUrl;

    News(String title, String date, String source, String sourceImage, String url) {

        mTitle = title;
        mDate = date;
        mSource = source;
        mSourceImage = sourceImage;
        mUrl = url;
    }

    String getDate() {
        return mDate;
    }

    String getTitle() {
        return mTitle;
    }

    public String getSource() {
        return mSource;
    }

    String getmSourceImage() {
        return mSourceImage;
    }

    public String getUrl() {
        return mUrl;
    }
}
