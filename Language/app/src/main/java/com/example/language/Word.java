package com.example.language;

public class Word {
    /** Default translation for the word */
    private String mDefaultTranslation;

    /** Miwok translation for the word */
    private String mMiwokTranslation;

    /** Store id of image as int */
    private int mImageResourceId = NO_IMAGE_PROVIDED;

    /** Constant value that represents no image was provided for this word */
    private static final int NO_IMAGE_PROVIDED = -1;

    /** Store id of voice recording as int*/
    private int mRecordingId;



    /**
     * Create a new Word object.
     * @param defaultTranslation is the word in a language that the user is already familiar with
     *                           (such as English)
     * @param marathiTranslation is the word in the Miwok language
     */
    public Word(String defaultTranslation, String marathiTranslation, int recording) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = marathiTranslation;
        mRecordingId = recording;
    }
    // Creating second constructor to take in image resource id as well

    public Word(String defaultTranslation, String marathiTranslation, int imageResourceId, int recording) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = marathiTranslation;
        mImageResourceId = imageResourceId;
        mRecordingId=recording;
    }

    /**
     * Get the default translation of the word.
     */
    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }

    /**
     * Get the Marathi translation of the word.
     */
    public String getMarathiTranslation() {
        return mMiwokTranslation;
    }

    /** get the image resource id */
    public int getImageResourceId() {
        return mImageResourceId;
    }

    /**
     * Returns whether or not there is an image for this word.
     */
    public boolean hasImage() {
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }

    /** Get recording resource id*/
    public int getRecordingId() {
        return mRecordingId;
    }
}
