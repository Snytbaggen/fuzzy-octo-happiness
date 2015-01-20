package com.lkss.sangboksapp;

import android.app.ActionBar;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.view.View;

/**
 * Created by Daniel on 2014-10-22.
 */
public class GuideActivity extends FragmentActivity {
    private int currentLayoutId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guidelayout);
        ActionBar a = getActionBar();
        if (a != null)
            a.hide();

        GuideFragment fragment = new GuideFragment();
        fragment.setLayoutId(R.layout.guidefragment1);
        currentLayoutId = R.layout.guidefragment1;

        getSupportFragmentManager().beginTransaction().replace(R.id.guide_container,
                fragment).commit();
    }

    @Override
    public void onBackPressed() {
        if (currentLayoutId == R.layout.guidefragment2){
            GuideFragment fragment = new GuideFragment();
            fragment.setLayoutId(R.layout.guidefragment1);
            currentLayoutId = R.layout.guidefragment1;
            getSupportFragmentManager().beginTransaction().replace(R.id.guide_container,
                    fragment).commit();
        }else{
            finish();
        }
    }

    public void showGuide(View v){
        GuideFragment f = new GuideFragment();
        f.setLayoutId(R.layout.guidefragment2);
        currentLayoutId = R.layout.guidefragment2;
        getSupportFragmentManager().beginTransaction().replace(R.id.guide_container,
                f).commit();
    }

    public void okay(View v){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.edit().putBoolean("firststart", false).putBoolean("pdfenabled", true).commit();
        finish();
    }

    public void disablePdf(View v){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.edit().putBoolean("firststart", false).putBoolean("pdfenabled", false).commit();
        finish();
    }
}
