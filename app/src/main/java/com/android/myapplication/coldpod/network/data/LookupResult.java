package com.android.myapplication.coldpod.network.data;

import com.google.gson.annotations.SerializedName;

public class LookupResult {

    @SerializedName("feedUrl")
    private String mFeedUrl;

    public String getFeedUrl() {
        return mFeedUrl;
    }

    public void setFeedUrl(String feedUrl) {
        mFeedUrl = feedUrl;
    }
}