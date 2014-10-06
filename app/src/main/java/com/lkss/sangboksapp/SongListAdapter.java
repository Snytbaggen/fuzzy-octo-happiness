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

    SoundPlayer player = new SoundPlayer();

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

            if (name != null)
                name.setText(s.getName());

            if (page != null)
                page.setText(PageToString(s.getPage()));

            if (subtitle != null) {
                if (!s.getSubtitle().equalsIgnoreCase("")) {
                    subtitle.setText(s.getSubtitle());
                    subtitle.setVisibility(View.VISIBLE);
                }
                else
                    subtitle.setVisibility(View.GONE);
            }
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
}
