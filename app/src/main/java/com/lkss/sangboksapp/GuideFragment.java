package com.lkss.sangboksapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Daniel on 2014-10-22.
 */
public class GuideFragment extends Fragment {

    private int layoutId;

    public void setLayoutId(int layoutId){
        this.layoutId = layoutId;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(layoutId, container, false);

    }
}
