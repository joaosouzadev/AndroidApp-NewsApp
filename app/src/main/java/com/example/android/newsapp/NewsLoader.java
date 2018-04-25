package com.example.android.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;

import java.util.List;

/**
 * Created by JOAO on 25-Apr-18.
 */

public class NewsLoader extends AsyncTaskLoader<List<News>> {

    /** Query URL */
    private String mUrl;

    public NewsLoader(Context context, String requestUrl) {
        super(context);
        mUrl = requestUrl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<News> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of earthquakes.
        List<News> news = QueryUtils.fetchEarthquakeData(mUrl);
        return news;
    }
}
