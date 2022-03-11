package com.example.musicplayer;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;

import androidx.annotation.Nullable;

import java.util.ArrayList;


public class service extends Service implements
        MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener {

    //media player
    private MediaPlayer mMediaplayer;
    //song list
    private ArrayList<Word> songs;
    //current position
    private int position;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
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

    }

    public void onCreate(){

        super.onCreate();
        position=0;
        mMediaplayer = new MediaPlayer();

        initMusicPlayer();
    }

    public void initMusicPlayer(){
        mMediaplayer.setWakeMode(getApplicationContext(),
                PowerManager.PARTIAL_WAKE_LOCK);
        mMediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        mMediaplayer.setOnPreparedListener(this);
        mMediaplayer.setOnCompletionListener(this);
        mMediaplayer.setOnErrorListener(this);
    }

    public void setList(ArrayList<Word> theSongs){
        songs=theSongs;
    }

    public class MusicBinder extends Binder {
        service getService() {
            return service.this;
        }
    }
}
