package com.example.android.newsnow;

public class NewsSource {

    private static final int NO_IMAGE = -1;
    private String mName, mCountry, mDescription, mUrl, mLanguage, mSmallLogo, mMediumLogo, mLargeLogo;
    private boolean mTop = false;
    private boolean mLatest = false;
    private boolean mPopular = false;
    private int mImageResourceId = NO_IMAGE;

    public NewsSource(String name, String description, String url, String country, String language) {
        mName = name;
        mDescription = description;
        mUrl = url;
        mCountry = country;
        mLanguage = language;
    }

    public String getName() {
        return mName;
    }

    public String getCountry() {
        return mCountry;
    }

    public boolean hasImage() {
        return mImageResourceId != NO_IMAGE;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setSLogo(String small) {
        mSmallLogo = small;
    }

    public void setMLogo(String medium) {
        mMediumLogo = medium;
    }

    public void setLLogo(String large) {
        mLargeLogo = large;
    }

    public void setmTop(boolean mTop) {
        this.mTop = mTop;
    }

    public void setmPopular(boolean mPopular) {
        this.mPopular = mPopular;
    }

    public void setmLatest(boolean mLatest) {
        this.mLatest = mLatest;
    }
}
