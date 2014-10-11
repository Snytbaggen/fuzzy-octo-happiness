package com.lkss.sangboksapp;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by Daniel on 2014-10-11.
 */
public class SetPreferenceActivity extends PreferenceActivity {
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new PrefsFragment()).commit();
    }
}
