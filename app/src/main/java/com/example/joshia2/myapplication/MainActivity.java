package com.example.joshia2.myapplication;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.joshia2.myapplication.Adapters.SongListAdapter;
import com.example.joshia2.myapplication.Services.MusicService;
import com.example.joshia2.myapplication.helper.SongInfo;

import java.util.ArrayList;


public class MainActivity extends Activity {

    ArrayList<SongInfo> songInfoArrayList;
    private MusicService musicSrv;
    private Intent playIntent;
    // flag to track if an activity is bound to a service
    private boolean musicBound=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MediaPlayer myplayer = new MediaPlayer();
        Button play = (Button)findViewById(R.id.play);
        Button stop = (Button)findViewById(R.id.stop);
        ListView songList = (ListView) findViewById(R.id.songList);
        songInfoArrayList = new ArrayList<>();
        getSongList();
        SongListAdapter sadapter = new SongListAdapter(songInfoArrayList,this);
        songList.setAdapter(sadapter);
        play.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

            }
        });

    }

    private ServiceConnection musicConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder)service;
            musicSrv = binder.getService();
            musicSrv.setList(songInfoArrayList);
            musicBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicBound = false;
        }
    };
        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onStart(){
        super.onStart();
        if(playIntent==null)
        {
            playIntent = new Intent(this,MusicService.class);
            bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
            startService(playIntent);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_shuffle:
                //shuffle
                break;
            case R.id.action_end:
                stopService(playIntent);
                musicSrv=null;
                System.exit(0);
                break;
        }
        return super.onOptionsItemSelected(item);
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
//HELPER FUNCTION TO ACCESS SONG DATA FROM SYSTEM ( CONTENT PROVIDER)
    public void getSongList(){

        ContentResolver songResolver = getContentResolver();
        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = songResolver.query(musicUri,null,null,null,"TITLE ASC");
        if(musicCursor==null || musicCursor.getCount()<1)
        {
            Toast.makeText(getApplicationContext(),"Sorry! No music fetched at this point",Toast.LENGTH_LONG);
        }
        else
        {
            int titleIndex = musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int idIndex = musicCursor.getColumnIndex(MediaStore.Audio.Media._ID);
            int artistIndex = musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            musicCursor.moveToFirst();
           do
           {
               int id = musicCursor.getInt(idIndex);
               String title = musicCursor.getString(titleIndex);
               String artist = musicCursor.getString(artistIndex);
               songInfoArrayList.add(new SongInfo(id,title,artist));

           }while(musicCursor.moveToNext());
        }


    }

    public void songPicked(View view){
        musicSrv.setSong(Integer.parseInt(view.getTag().toString()));
        musicSrv.playSong();
    }
}
