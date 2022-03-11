package com.example.musicplayer;

public class Word {
    private String song;
    private String artist;
    private int mRecordingId;

    public Word(String song_name, String artist_name, int recording) {
        song = song_name;
        artist = artist_name;
        mRecordingId = recording;
    }

    /** Get song name*/
    public String getSongName() {
        return song;
    }

    /** Get artist name */
    public String getArtistName() {
        return artist;
    }

    /** Get recording resource id*/
    public int getRecordingId() {
        return mRecordingId;
    }
}

