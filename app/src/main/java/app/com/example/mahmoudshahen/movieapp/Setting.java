package app.com.example.mahmoudshahen.movieapp;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

/**
 * Created by mahmoud shahen on 8/15/2016.
 */

public class Setting extends PreferenceActivity implements Preference.OnPreferenceChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout.activity_setting);
           //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
             //setSupportActionBar(toolbar);
        addPreferencesFromResource(R.xml.king);

        onPreferenceChange(findPreference("kind"), MainActivity.sort);
        bindPreferenceSummaryToValue(findPreference("kind"));

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(MainActivity.sort.equals("favourite"))
            DetailsFragment.pos = 0;

    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String stringValue = newValue.toString();
        if(preference instanceof ListPreference)
        {
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(stringValue);
            if(prefIndex >= 0)
            {
                preference.setSummary(listPreference.getEntries()[prefIndex]);
                if(MainActivity.sort.equals(stringValue))
                {
                    MainActivityFragment.getData= false;
                }
                else
                {
                    MainActivityFragment.getData = true;
                }

                MainActivity.sort = stringValue;
            }
            else
            {
                preference.setSummary(stringValue);
            }
        }
        return true;
    }
    private void bindPreferenceSummaryToValue(Preference preference) {
        preference.setOnPreferenceChangeListener(this);
        onPreferenceChange(preference,
                PreferenceManager.getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }
}
