package com.lkss.sangboksapp;

import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import songs.Notes;
import songs.Song;

/**
 * Created by Daniel on 2014-09-20.
 */
public class SongListAdapter extends ArrayAdapter<Song>{

    public SongListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public SongListAdapter(Context context, int resource, List<Song> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;
        LayoutInflater vi = LayoutInflater.from(getContext());
        if (v==null)
            v = vi.inflate(R.layout.song_list_element, null);

        Song s = getItem(position);

        if (s!=null){
            TextView name = (TextView) v.findViewById(R.id.list_title_text);
            TextView page = (TextView) v.findViewById(R.id.list_page_nr);
            TextView subtitle = (TextView) v.findViewById(R.id.list_subtitle_text);
            TextView notes = (TextView) v.findViewById(R.id.list_start_tones);
            ImageView infoButton = (ImageView) v.findViewById(R.id.list_song_info_icon);
            //LinearLayout noteLayout = (LinearLayout)v.findViewById(R.id.list_notes);

            if (name != null)
                name.setText(s.getName());

            if (page != null)
                page.setText(PageToString(s.getPage()));

            //if (subtitle != null)
                //subtitle.setText(":)");
            //    subtitle.setText(s.getSubtitle());
            //else if (subtitle != null)
            //    subtitle.setVisibility(View.INVISIBLE);

            if (notes != null){
                Notes n = new Notes();
                notes.setText(n.notesToString(s.getStartTones()));
            }

            //if (infoButton != null)
            //    infoButton.setOnClickListener(this);

            /*if (noteLayout != null) {
                TextView note = (TextView) noteLayout.findViewById(R.id.list_note_button);
                if (note == null) {
                    Notes n = new Notes();
                /*for (double note : s.getStartTones()) {
                    TextView noteView = (TextView) vi.inflate(R.layout.note_button, null);
                    noteView.setText(n.noteToString(note));
                    noteLayout.addView(noteView);
                }
                    TextView noteView = (TextView) vi.inflate(R.layout.note_button, null);
                    noteView.setText(position + "");
                    noteLayout.addView(noteView);
                }
                String tag = (String) noteLayout.getTag();
                if (tag.equalsIgnoreCase("visible"))
                    noteLayout.setVisibility(View.VISIBLE);
                else
                    noteLayout.setVisibility(View.GONE);
            }*/
        }

        return v;
    }

    private String PageToString(int page){
        String ret = "";

        if (page < 100)
            ret += "0";

        if (page < 10)
            ret += "0";

        return ret += page;
    }

    public void infoButtonClicked(View v, Song s){
        SoundPlayer p = new SoundPlayer();
        p.playNotes(s.getStartTones(), 1);

        /*Log.d("Click", "Info button was clicked for song " + s.getName());
        LinearLayout notesLayout = null;
        TextView songName = null;


        if (v != null) {
            notesLayout = (LinearLayout) v.findViewById(R.id.list_notes);
            songName = (TextView) v.findViewById(R.id.list_title_text);
        }

        if (notesLayout != null && songName != null && s.getName().equalsIgnoreCase(songName.getText().toString())){
            if (notesLayout.getVisibility() == View.VISIBLE){
                notesLayout.setVisibility(View.GONE);
                notesLayout.setTag("gone");
            }else{
                notesLayout.setVisibility(View.VISIBLE);
                notesLayout.setTag("visible");
            }
        }*/
    }

    public void noteButtonClicked(View v, Song song){
    }
}
