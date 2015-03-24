package com.example.joshia2.myapplication.Services;

import android.app.Service;
import android.content.ContentUris;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;

import com.example.joshia2.myapplication.helper.SongInfo;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by joshia2 on 3/23/2015.
 */
public class MusicService extends Service implements MediaPlayer.OnCompletionListener,MediaPlayer.OnErrorListener,MediaPlayer.OnPreparedListener {

    MediaPlayer mediaPlayer;
    ArrayList<SongInfo> playlist;
    int songPos;
    private final IBinder musicBind = new MusicBinder();
    @Override
    public void onCreate(){
        super.onCreate();
        songPos = 0;
        mediaPlayer = new MediaPlayer();
    }

    //TO INITIALIZE THE MEDIA PLAYER
    public void initMediaPlayer()
    {
        mediaPlayer.setWakeMode(getApplicationContext(),
                PowerManager.PARTIAL_WAKE_LOCK);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnErrorListener(this);
    }
    public void setList(ArrayList<SongInfo> theSongs){
        playlist=theSongs;
    }
    public IBinder onBind(Intent intent) {
        return musicBind;
    }
    public boolean onUnbind(Intent intent){
        mediaPlayer.stop();
        mediaPlayer.release();
        return false;
    }
    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();

    }

    public class MusicBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }


    //added methods
    public void playSong(){
        mediaPlayer.reset();
        SongInfo playSong = playlist.get(songPos);
        int songId = playSong.getId();
        Uri trackUri = ContentUris.withAppendedId(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                songId);
        try {
            mediaPlayer.setDataSource(getApplicationContext(),trackUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.prepareAsync();
    }

    public void setSong(int songIndex){
        songPos=songIndex;
    }
}
