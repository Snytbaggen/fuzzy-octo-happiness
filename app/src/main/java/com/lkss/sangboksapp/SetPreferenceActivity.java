package com.lkss.sangboksapp;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.widget.Toast;

/**
 * Created by Daniel on 2014-10-11.
 */
public class SetPreferenceActivity extends PreferenceActivity{
    protected void onCreate(Bundle savedInstanceState) {
        PrefsFragment prefsFragment = new PrefsFragment();
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().replace(android.R.id.content,
                prefsFragment).commit();



        getActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
