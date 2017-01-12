package com.example.android.newsnow;

/**
 * News Source Object Class
 * Gets id and a logo params that are fetched from API request
 */
class NewsSource {

    private String mId, mSmallLogo;

    NewsSource(String id) {
        mId = id;
    }

    public String getId() {
        return mId;
    }

    void setSLogo(String small) {
        mSmallLogo = small;
    }

    String getmSmallLogo() {
        return mSmallLogo;
    }

}
