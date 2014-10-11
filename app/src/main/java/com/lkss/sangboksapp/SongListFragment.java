package com.lkss.sangboksapp;

import android.app.Activity;
import android.app.ListFragment;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import songs.Song;
import songs.SongList;

/**
 * Created by Daniel on 2014-09-20.
 */
public class SongListFragment extends ListFragment{
    private String appDataDirectory;
    private SongList songList;
    private SoundPlayer player;
    private double note_duration;


    public void setData(String appDataDirectory, SongList songList, SoundPlayer player, double note_duration){
        this.appDataDirectory=appDataDirectory;
        this.songList=songList;
        this.player=player;
        this.note_duration=note_duration;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.song_list, container, false);

        final SongListAdapter adapter = new SongListAdapter(getActivity(), R.layout.activity_main, songList.getList());

        ListView listView = (ListView) v.findViewById(android.R.id.list);
        listView.setAdapter(adapter);

        return v;
    }
    public void openSongFile(Song song){
        File f = new File(appDataDirectory, song.getSongFile());

        Intent target = new Intent(Intent.ACTION_VIEW);
        target.setDataAndType(Uri.fromFile(f),"application/pdf");
        target.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        try {
            startActivity(target);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getActivity(), "Installera en PDF-läsare", Toast.LENGTH_SHORT).show();
        }
    }

    public void clickListener(View v) {
        ListView list = getListView();
        final int position = list.getPositionForView(v);
        if (position != ListView.INVALID_POSITION) {
            Song song = (Song) list.getItemAtPosition(position);
            player.playNotes(song.getStartTones(), note_duration, (ImageView)v, getActivity());
            //final ImageView iv = (ImageView)v;
            //final Activity a = getActivity();
            //TimerTask timerTask = new TimerTask() {

            //    @Override
            //    public void run() {
            //        a.runOnUiThread(new Runnable() {
            //            @Override
            //            public void run() {
            //                iv.setImageDrawable(iv.getResources().getDrawable(R.drawable.gaffel));
            //            }
            //        });
            //    }
            //};

            //iv.setImageDrawable(iv.getResources().getDrawable(R.drawable.gaffel_klang));
            //Timer timer = new Timer();
            //double d = song.getStartTones().length*note_duration*1000;
            //long time = (long) d;
            //timer.schedule(timerTask, time);

                /*To call a function in the adapter at this point use the following line:
                     SongListAdapter adapter = (SongListAdapter)list.getAdapter();
                 and then adapter.functionName()*/
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        final Song song = (Song) l.getItemAtPosition(position);
        openSongFile(song);
    }

}
