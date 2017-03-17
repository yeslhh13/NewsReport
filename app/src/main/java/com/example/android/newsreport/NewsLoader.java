package com.example.android.newsreport;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by gowon on 2017-02-26.
 */

public class NewsLoader extends AsyncTaskLoader<List<News>> {
    private String mUrl;

    public NewsLoader(Context context, String mUrl) {
        super(context);
        this.mUrl = mUrl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     * @return {@link News} data from requested Url.
     */
    @Override
    public List<News> loadInBackground() {
        if (mUrl == null)
            return null;

        return QueryUtils.fetchNewsData(mUrl);
    }
}
