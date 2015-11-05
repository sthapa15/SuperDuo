package it.jaschke.alexandria;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by saj on 27/01/15.
 * Modified by Sukriti
 */
public class SettingsActivity extends PreferenceActivity
        implements Preference.OnPreferenceChangeListener {
    ListPreference listPreference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        listPreference = (ListPreference) findPreference(getString(R.string.pref_startScreen_key));

        // For all preferences, attach an OnPreferenceChangeListener
        bindPreferenceSummaryToValue(listPreference);
    }

    /**
     * Attaches a listener so that the summary is updated with the preference value.
     * Fires the listener once, to initialize the summary
     */
    private void bindPreferenceSummaryToValue(Preference preference) {
        // Set listener to watch for value change.
        preference.setOnPreferenceChangeListener(this);

        // Trigger the listener with the preference's current value.
        onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {
        String stringValue = value.toString();

        if (preference instanceof ListPreference) {
            // For list preferences, look up the correct display value in
            // the preference's 'entries' list
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(stringValue);
            if (prefIndex >= 0) {
                preference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        } else {
            // For other preferences, set summary to the value's string representation.
            preference.setSummary(stringValue);
        }
        return true;
    }

    // source: http://stackoverflow.com/questions/26509180/no-actionbar-in-preferenceactivity-after-upgrade-to-support-library-v21
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        LinearLayout root = (LinearLayout) findViewById(android.R.id.list).getParent().getParent().getParent();
        Toolbar bar = (Toolbar) LayoutInflater.from(this).inflate(R.layout.settings_toolbar, root, false);
        root.addView(bar, 0); //insert on top
        bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}