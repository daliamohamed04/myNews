package com.example.news;



import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;

import java.util.ArrayList;
import java.util.List;

public class NewsObject extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    public static final String LOG_TAG = NewsObject.class.getName();

    /**

     * URL for news data from the Guardian

     */

    private static final String REQUEST_URL =

            "http://content.guardianapis.com/search";

    /**

     * Constant value for the news loader ID. We can choose any integer.

     * This really only comes into play if you're using multiple loaders.

     */

    private static final int LOADER_ID = 1;

    /**

     * TextView that is displayed when the list is empty

     */

    private TextView mEmptyStateTextView;

    /**

     * Adapter for the list of news

     */

    private Adapter mAdapter;



    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_news);

        // Get a reference to the LoaderManager, in order to interact with loaders.

        //LoaderManager loaderManager = getLoaderManager();



        // Initialize the loader. Pass in the int ID constant defined above and pass in null for

        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid

        // because this activity implements the LoaderCallbacks interface).

        //loaderManager.initLoader(LOADER_ID, null, this);



        // Find a reference to the {@link ListView} in the layout

        ListView newsListView = (ListView) findViewById(R.id.list);



        // Create a new adapter that takes an empty list of news as input

        mAdapter = new Adapter(this, new ArrayList<News>());



        // Set the adapter on the {@link ListView}

        // so the list can be populated in the user interface

        newsListView.setAdapter(mAdapter);



        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);

        newsListView.setEmptyView(mEmptyStateTextView);



        // Set an item click listener on the ListView, which sends an intent to a web browser

        // to open a website with more information about the selected news.

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override

            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                // Find the current news that was clicked on

                News currentNews = mAdapter.getItem(position);



                // Convert the String URL into a URI object (to pass into the Intent constructor)

                Uri newsUri = Uri.parse(currentNews.getUrl());



                // Create a new intent to view the News URI

                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);



                // Send the intent to launch a new activity

                startActivity(websiteIntent);

            }

        });

        // Get a reference to the ConnectivityManager to check state of network connectivity

        ConnectivityManager connMgr = (ConnectivityManager)

                getSystemService(Context.CONNECTIVITY_SERVICE);



        // Get details on the currently active default data network

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();



        // If there is a network connection, fetch data

        if (networkInfo != null && networkInfo.isConnected()) {

            // Get a reference to the LoaderManager, in order to interact with loaders.
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for

            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid

            // because this activity implements the LoaderCallbacks interface).

            LoaderManager.getInstance(this).initLoader(LOADER_ID, null, this);

        } else {

            // Otherwise, display error

            // First, hide loading indicator so error message will be visible

            View loadingIndicator = findViewById(R.id.loading_indicator);

            loadingIndicator.setVisibility(View.GONE);



            // Update empty state with no connection error message

            mEmptyStateTextView.setText(R.string.no_internet_connection);

        }





    }



    //Then we need to override the three methods specified in the LoaderCallbacks interface.

    // We need onCreateLoader(), for when the LoaderManager has determined that the loader with our specified ID isn't running,

    // so we should create a new one.

    @Override

    public androidx.loader.content.Loader<List<News>> onCreateLoader(int i, Bundle bundle) {





        // parse breaks apart the URI string that's passed into its parameter

        Uri baseUri = Uri.parse(REQUEST_URL);



        // buildUpon prepares the baseUri that we just parsed so we can add query parameters to it

        Uri.Builder uriBuilder = baseUri.buildUpon();



        // Append query parameter and its value.

        uriBuilder.appendQueryParameter(getString(R.string.parameter1), getString(R.string.value1));

        uriBuilder.appendQueryParameter(getString(R.string.parameter2), getString(R.string.value2));



        // Return the completed uri

        return new Loader(this, uriBuilder.toString());

    }



    @Override
    public void onLoadFinished(androidx.loader.content.Loader<List<News>> loader, List<News> news) {
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "No earthquakes found."
        mEmptyStateTextView.setText(R.string.no_internet_connection);

        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (news != null && !news.isEmpty()) {
            mAdapter.addAll(news);
        }
    }

    @Override
    public void onLoaderReset(androidx.loader.content.Loader<List<News>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

