/*
 * Copyright (c) 2015 Anders Lunde, Robin Sjøvoll, Kristian Huse. All rights reserved.
 */

package com.magicaeludos.mobile.magicaeludos.implementation;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;

import com.magicaeludos.mobile.magicaeludos.framework.BGM;
/**
 * Created by Kristian on 13/04/2015. Modified by Anders 02/04/2016
 */
public class GameBGM implements BGM, OnCompletionListener, OnSeekCompleteListener, OnPreparedListener {

    private MediaPlayer mediaPlayer;
    private boolean isPrepared;
    private final int maxSteps = 100;

    /**
     *
     * @param c
     * @param resId ID of the resource file to be played (R.raw.[...])
     */
    public GameBGM(Context c, int resId) {
        mediaPlayer = MediaPlayer.create(c, resId);

            isPrepared = true;
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.setOnSeekCompleteListener(this);
            mediaPlayer.setOnPreparedListener(this);

    }


    /**
     * Play the currently loaded BGM
     */
    @Override
    public void play() {
        if (mediaPlayer.isPlaying()) // No sense in playing a playing song. Go through the right channels, and stop it before playing
            return;
        try {
            mediaPlayer.start();
        }catch(IllegalStateException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void stop() {
        if(mediaPlayer.isPlaying())
            mediaPlayer.stop();
    }

    @Override
    public void pause() {
        if(mediaPlayer.isPlaying())
            mediaPlayer.pause();
    }

    @Override
    public void setLooping(Boolean isLooping) {
        mediaPlayer.setLooping(isLooping);
    }

    @Override
    public boolean isLooping() {
        return mediaPlayer.isLooping();
    }

    @Override
    public void setVolume(float db) {
        float log1=(float)(Math.log(maxSteps-db)/Math.log(maxSteps));
        mediaPlayer.setVolume(1-log1, 1-log1);
       // mediaPlayer.setVolume(db, db); //Stereo
    }

    @Override
    public boolean isPlaying() {
        return false;
    }

    @Override
    public void dispose() {
        if(mediaPlayer.isPlaying())
           mediaPlayer.stop();
        mediaPlayer.release();
    }

    @Override
         public void onCompletion(MediaPlayer mp) {
        isPrepared = false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        isPrepared = true;
    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {

    }
}
