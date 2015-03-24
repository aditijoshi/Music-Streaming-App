package com.example.joshia2.myapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.joshia2.myapplication.R;
import com.example.joshia2.myapplication.helper.SongInfo;

import java.util.ArrayList;

/**
 * Created by joshia2 on 3/23/2015.
 */
public class SongListAdapter extends BaseAdapter {

    private ArrayList<SongInfo> songs;
    private LayoutInflater songInf;

    public SongListAdapter(ArrayList<SongInfo> songs, Context c) {
        this.songs = songs;
        this.songInf = LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        return songs.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout songLay = (LinearLayout)songInf.inflate(R.layout.song,parent,false);
        TextView songView = (TextView)songLay.findViewById(R.id.song_name);
        TextView artistView = (TextView)songLay.findViewById(R.id.song_artist);
        //get song using position
        SongInfo currSong = songs.get(position);
        //get title and artist strings
        songView.setText(currSong.getName());
        artistView.setText(currSong.getArtist());
        //set position as tag
        songLay.setTag(position);
        return songLay;

    }
}
