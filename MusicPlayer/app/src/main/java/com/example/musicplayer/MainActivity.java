package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
    /**
     * Handles playback of all the sound files
     */
    private MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create an array of words
        final ArrayList<Word> songs = new ArrayList<Word>();
        songs.add(new Word("Ishq Bulaava", "Sanam Puri, Shipra Goyal, Vishal-Shekhar", R.raw.ring));

        songs.add(new Word("Ahista-ahista", "Lucky Ali and Shreya Goshal", R.raw.ahista));
        songs.add(new Word("Boom clap", "lala", R.raw.boom));
//        songs.add(new Word("Baawara Mann", "Jubin Nautiyal and Neeti Mohan", R.raw.bawara));
        songs.add(new Word("Darkhaast", "Mithoon", R.raw.dar));
//        songs.add(new Word("Ghar more pardesiya", "Shreya Goshal", R.raw.kalank));
//        songs.add(new Word("Capital letters", "BloodPop and Hailee Steinfeld", R.raw.cap));
//        songs.add(new Word("Tumse hi tumse", "Caralisa Monteiro and Shekhar Ravjiani", R.raw.tumse));
        songs.add(new Word("Replay", "Iyaz", R.raw.replay));
//        songs.add(new Word("Juju on that beat", "Zay Hilfigerrr and Zayion McCall", R.raw.juju));


        // Sorting the songs before displaying
        Collections.sort(songs, new Comparator<Word>(){
            public int compare(Word a, Word b){
                return a.getSongName().compareTo(b.getSongName());
            }
        });
        


        WordAdapter adapter = new WordAdapter(this, songs);

        ListView listView = (ListView) findViewById(R.id.song_list);

        listView.setAdapter(adapter);

/** Set on click listener*/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                // Create a new intent to view the player
                Intent player = new Intent(MainActivity.this, Player.class);
                // Send the intent to launch a new activity
//                player.putExtra("songs_list", songs);
                player.putExtra("position", position);

                startActivity(player);

            }


        });





    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        // play the music here
//        mMediaPlayer.start();
//    }



    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;

            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
//            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}




