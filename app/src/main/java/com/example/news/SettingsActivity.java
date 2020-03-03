package com.example.news;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragment;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.settings_activity);

        // setting up toolbar

        Toolbar toolbar = findViewById(R.id.settings_toolbar);

        setSupportActionBar(toolbar);

        // setting up back arrow on toolbar

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // replacing frame layout with fragment

        getFragmentManager().beginTransaction().replace(R.id.frame_layout, new NewsSettingsFragment()).commit();

    }



    // setting up NewsSettingsFragment class

    public static class NewsSettingsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

        @Override

        public void onCreate(final Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.root_preferences);



            // binding preference values to settings titles

            Preference sectionsKey = findPreference(getString(R.string.sections_key));

            bindPreferenceSummaryToValue(sectionsKey);

            Preference searchKey = findPreference(getString(R.string.search_key));

            bindPreferenceSummaryToValue(searchKey);

            Preference requestsKey = findPreference(getString(R.string.requests_key));

            bindPreferenceSummaryToValue(requestsKey);

        }

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        }


        // a method from the course to update values on settings headings

        @Override

        public boolean onPreferenceChange(Preference preference, Object newValue) {



            String stringValue = newValue.toString();

            preference.setSummary(stringValue);

            if (preference instanceof ListPreference) {

                ListPreference listPreference = (ListPreference) preference;

                int prefIndex = listPreference.findIndexOfValue(stringValue);

                if (prefIndex >= 0) {

                    CharSequence[] labels = listPreference.getEntries();

                    preference.setSummary(labels[prefIndex]);

                } else {

                    preference.setSummary(stringValue);

                }

            }

            return true;

        }



        // a method from the course to bind values to settings headings

        private void bindPreferenceSummaryToValue(Preference preference) {

            preference.setOnPreferenceChangeListener(this);

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());

            String preferenceString = preferences.getString(preference.getKey(), "");

            onPreferenceChange(preference, preferenceString);



        }



    }



}