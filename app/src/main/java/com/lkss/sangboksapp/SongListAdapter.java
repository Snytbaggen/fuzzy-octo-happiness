package com.lkss.sangboksapp;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import songs.Song;

/**
 * Created by Daniel on 2014-09-20.
 */
public class SongListAdapter extends ArrayAdapter<Song> {

    public SongListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public SongListAdapter(Context context, int resource, List<Song> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;

        if (v==null){
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.song_list_element, null);
        }

        Song s = getItem(position);

        if (s!=null){
            TextView name = (TextView) v.findViewById(R.id.list_title_text);
            TextView page = (TextView) v.findViewById(R.id.list_page_nr);
            TextView subtitle = (TextView) v.findViewById(R.id.list_subtitle_text);

            if (name != null)
                name.setText(s.getName());

            if (page != null)
                page.setText(PageToString(s.getPage()));

            //if (subtitle != null)
            //    subtitle.setText(s.getSubtitle());
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
