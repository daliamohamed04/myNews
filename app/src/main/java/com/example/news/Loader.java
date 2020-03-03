package com.example.news;


import androidx.loader.content.AsyncTaskLoader;
import android.content.Context;
import java.util.List;

public class Loader extends AsyncTaskLoader<List<News>> {



    /** Tag for log messages */

    private static final String LOG_TAG = Loader.class.getName();



    /** Query URL */

    private String mUrl;




    public Loader(Context context, String url) {

        super(context);

        mUrl = url;

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



        // Perform the network request, parse the response, and extract a list of news.

        List<News> news = QueryUtils.fetchNewsData(mUrl);

        return news;

    }



}