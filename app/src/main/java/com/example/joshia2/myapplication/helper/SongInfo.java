package com.example.joshia2.myapplication.helper;

/**
 * Created by joshia2 on 3/23/2015.
 */
public class SongInfo {
    int id;
    String name;
    String artist;


    public SongInfo(int id, String name, String artist) {
        this.id = id;
        this.name = name;
        this.artist = artist;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }


}
