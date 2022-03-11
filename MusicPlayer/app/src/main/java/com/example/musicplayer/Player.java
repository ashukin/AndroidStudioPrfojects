package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class Player extends AppCompatActivity {

    ImageView playBtn;
    ImageView prevBtn;
    ImageView nextBtn;
    ImageView shuffle;
    SeekBar mSeekbar;
    TextView mSongTitle;
    int position;
    ArrayList<Word> songs;
    TextView currentTime;
    TextView totalTime;

    /**
     * Handles playback of all the sound files
     */
    private MediaPlayer mMediaPlayer;

    /**
     * Handles audio focus when playing a sound file
     */
    private AudioManager mAudioManager;

    /**
     * This listener gets triggered whenever the audio focus changes
     * (i.e., we gain or lose audio focus because of another app or device).
     */
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.

                // Pause playback and reset player to the start of the file. That way, we can
                // play the word from the beginning when we resume playback.
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }
        }
    };


    /**
     * This listener gets triggered when the {@link MediaPlayer} has completed
     * playing the audio file.
     */
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
            playBtn.setImageResource(R.drawable.play_b);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player);

        // Setting resources
        mSongTitle = findViewById(R.id.song_title);
        playBtn = findViewById(R.id.play);
        prevBtn = findViewById(R.id.previous);
        nextBtn = findViewById(R.id.next);
        mSeekbar = findViewById(R.id.seekbar);
        currentTime = findViewById(R.id.currentTimer);
        totalTime = findViewById(R.id.totalTimer);
        shuffle = findViewById(R.id.shuffle);

        // Create and setup the {@link AudioManager} to request audio focus
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

//        ArrayList<Word> songs = (ArrayList<Word>) getIntent().getParcelableExtra("songs_list");
        // Create an array of words
        final ArrayList<Word> songs = new ArrayList<Word>();
        songs.add(new Word("Ishq Bulaava", "Sanam Puri, Shipra Goyal, Vishal-Shekhar", R.raw.ring));
//        songs.add(new Word("Baakhuda Tumhi Ho", "Alka Yagnik and Atif Aslam", R.raw.ring2));
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


        position = getIntent().getIntExtra("position",0);

        initPlayer(position,songs);


        playBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // When play pause button is clicked
            play();
        }
    });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(position < songs.size() - 1) {
                    position++;
                }else{
                    position = 0;
                }
                initPlayer(position,songs);

            }
        });
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (position <= 0) {
                    position = songs.size() - 1;
                } else {
                    position--;
                }
                initPlayer(position,songs);

            }
        });
        shuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.shuffle(songs);
                shuffle.setBackgroundColor(Color.parseColor("#FF8C19"));
            }
        });
    }



    private void initPlayer(int position,ArrayList<Word> songs) {

        // Release the media player if it currently exists because we are about to
        // play a different sound file
        releaseMediaPlayer();

        // Get the {@link Word} object at the given position the user clicked on
        Word song = songs.get(position);

        // Request audio focus so in order to play the audio file. The app needs to play a
        // short audio file, so we will request audio focus with a short amount of time
        // with AUDIOFOCUS_GAIN_TRANSIENT.
        int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            // We have audio focus now.

            // Create and setup the {@link MediaPlayer} for the audio resource associated
            // with the current word
            mMediaPlayer = MediaPlayer.create(Player.this, song.getRecordingId());

            // Set seekbar to maximum duration
            mSeekbar.setMax(mMediaPlayer.getDuration());

            String total = createTimerLabel(mMediaPlayer.getDuration());
            totalTime.setText(total);

            // Start the audio file
            mMediaPlayer.start();

            //Set icon to pause
            playBtn.setImageResource(R.drawable.pause);

            String name = song.getSongName();
            mSongTitle.setText(name);

            // Setup a listener on the media player, so that we can stop and release the
            // media player once the sound has finished playing.
            mMediaPlayer.setOnCompletionListener(mCompletionListener);
        }

        // Setting up the seek bar
        mSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if(fromUser) {
                    mMediaPlayer.seekTo(progress);
                    mSeekbar.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //setup seekbar to change with duration

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mMediaPlayer!=null) {
                    try{
                        if (mMediaPlayer.isPlaying()) {
                            Message message = new Message();
                            message.what = mMediaPlayer.getCurrentPosition();
                            handler.sendMessage(message);
                            Thread.sleep(1000);
                        }
                    }catch(InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }



    // create handler to set progress

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            currentTime.setText(createTimerLabel(msg.what));
            mSeekbar.setProgress(msg.what);
        }
    };

    private void play() {
        if (mMediaPlayer!=null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            playBtn.setImageResource(R.drawable.play_b);
        }else {
            mMediaPlayer.start();
            playBtn.setImageResource(R.drawable.pause);
        }

    }


    @Override
    protected void onStop() {
        super.onStop();
        //When the activity is paused release the mediaplayer coz we wont be needing it then
        releaseMediaPlayer();
    }

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
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

    public String createTimerLabel(int duration) {
        String timeLabel = "";
        int min = duration / 1000 / 60;
        int sec = duration / 1000 % 60;

        timeLabel += min + ":";
        if (sec < 10) timeLabel += "0";
        timeLabel += sec;

        return timeLabel;


    }

}

