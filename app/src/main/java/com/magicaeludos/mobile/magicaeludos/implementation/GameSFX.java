/*
 * Copyright (c) 2015 Anders Lunde, Robin Sjøvoll, Kristian Huse. All rights reserved.
 */

package com.magicaeludos.mobile.magicaeludos.implementation;

import android.media.SoundPool;
import android.util.Log;

import com.magicaeludos.mobile.magicaeludos.framework.SFX;

/**
 * Created by Kristian on 13/04/2015. Modified by Anders 02/04/2016
 */
public class GameSFX implements SFX {

    private int sfxId;
    private SoundPool soundPool;
    private float db;
    private boolean isLooping;
    private final int maxSteps = 100;

    public GameSFX(SoundPool soundPool, int sfxId, int db) {
        this.soundPool = soundPool;
        this.sfxId = sfxId;
        this.db = db;
    }

    /**
     * Play the chosen SFX from the soundpool
     */
    @Override
    public void play() {
        soundPool.play(sfxId, db, db, 0,0,1);
    }

    public void loopedPlay() {
        if(!isLooping) {
            isLooping = true;
            soundPool.play(sfxId, db, db, 0, -1, 1);
        }
    }

    public void stop() {  //Stops this sfx in the current soundPool
        soundPool.stop(sfxId);
        isLooping = false;
    }

    @Override
    public void setVolume(float db) {
        this.db = db;
        float log1=(float)(Math.log(maxSteps-db)/Math.log(maxSteps));
        soundPool.setVolume(sfxId,1-log1, 1-log1);
    }

    @Override
    public void dispose() {
        soundPool.unload(sfxId);
    }
}
