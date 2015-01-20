package com.lkss.sangboksapp;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.widget.Toast;

/**
 * Created by Daniel on 2014-10-11.
 */
public class PrefsFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {
    private int numberOfClicks;
    long timestamp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
        Preference firstStart = getPreferenceManager().findPreference("firststart");
        Preference about = getPreferenceManager().findPreference("about");
        about.setOnPreferenceClickListener(this);
        getPreferenceScreen().removePreference(firstStart);
        timestamp = System.currentTimeMillis();
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if (preference.getKey().equalsIgnoreCase("about")){

            if (System.currentTimeMillis() - timestamp < 500){
                numberOfClicks++;
                if (numberOfClicks >= 10){
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), GameActivity.class);
                    startActivityForResult(intent, 0);
                    numberOfClicks=0;
                }
            }else{
                numberOfClicks=0;
            }
            timestamp = System.currentTimeMillis();
        }
        return true;
    }
}
