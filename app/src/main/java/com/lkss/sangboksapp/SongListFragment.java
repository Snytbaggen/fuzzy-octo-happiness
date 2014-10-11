package com.lkss.sangboksapp;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ListFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import songs.Song;
import songs.SongList;

/**
 * Created by Daniel on 2014-09-20.
 */
public class SongListFragment extends ListFragment {
    private String appDataDirectory;
    private SongList songList;
    private SoundPlayer player;
    private double note_duration;
    List<Song> songsSearched = new ArrayList<Song>();

    public void setData(String appDataDirectory, SongList songList, SoundPlayer player, double note_duration){
        this.appDataDirectory=appDataDirectory;
        this.songList=songList;
        this.player=player;
        this.note_duration=note_duration;
    }

    public void updateDuration(double duration){
        note_duration = duration;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.song_list, container, false);

        final SongListAdapter adapter = new SongListAdapter(getActivity(), R.layout.activity_main, songList.getList());

        final ListView listView = (ListView) v.findViewById(android.R.id.list);
        listView.setAdapter(adapter);

        TextView searchField = (TextView) v.findViewById(R.id.list_search);
        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                songsSearched.clear();
                String name, subtitle, search;
                for (Song s : songList.getList()){
                    name = s.getName().toLowerCase();
                    subtitle = s.getSubtitle().toLowerCase();
                    search = charSequence.toString().toLowerCase();

                    if (name.contains(search) || subtitle.contains(search)){
                        songsSearched.add(s);
                    }
                }listView.setAdapter(new SongListAdapter(getActivity(), R.layout.activity_main, songsSearched));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

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
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        final Song song = (Song) l.getItemAtPosition(position);
        openSongFile(song);
    }

}
