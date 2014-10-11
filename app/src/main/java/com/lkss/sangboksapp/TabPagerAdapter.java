package com.lkss.sangboksapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import songs.SongList;

public class TabPagerAdapter extends FragmentStatePagerAdapter {
    private SongListFragment alphaFragment, numFragment, currentFragment;

    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
        // TODO Auto-generated constructor stub
    }

    public void updateDuration(double duration){
        alphaFragment.updateDuration(duration);
        numFragment.updateDuration(duration);
    }

    public void setData(String path, SongList a, SongList n, SoundPlayer p, double duration){
        alphaFragment = new SongListFragment();
        numFragment = new SongListFragment();
        alphaFragment.setData(path, a, p, duration);
        numFragment.setData(path, n, p, duration);
    }

    @Override
    public Fragment getItem(int i) {
        SongListFragment f = null;
        switch (i) {
            case 0:
                //Fragement for numerical list
                return numFragment;
            case 1:
                //Fragment for alphabetical list
                return alphaFragment;
        }
        return f;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 2; //No of Tabs
    }
}
