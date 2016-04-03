/*
 * Copyright (c) 2015 Anders Lunde, Robin Sjøvoll, Kristian Huse. All rights reserved.
 */

package com.magicaeludos.mobile.magicaeludos.implementation;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;

import com.magicaeludos.mobile.magicaeludos.framework.Audio;
import com.magicaeludos.mobile.magicaeludos.framework.BGM;
import com.magicaeludos.mobile.magicaeludos.framework.SFX;

/**
 * Created by Kristian on 13/04/2015. Modified by Anders 02/04/2016
 */
public class GameAudio implements Audio {

    private AssetManager assets;
    private SoundPool soundPool;

    //Create a class that keeps track of SFX, and can create BGM and SFX
    public GameAudio(Activity activity){
        activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        assets = activity.getAssets();
        soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0); // (int maxStreams, int streamType, int srcQuality)
    }

    @Override
    public BGM createMusic(Context c, int resId) {
            return new GameBGM(c, resId);

    }

    @Override
    public SFX createSound(Context c, int resId, int db) {
        int sfxId = soundPool.load(c, resId, 0);
        return new GameSFX(soundPool, sfxId, db);
    } //TODO: Implement code


    //Sound pool constructor deprecated


/**
 *  Verify if the sdk version is Lollipop so you can use the code for it, if not do it the same old way.
 * Verify device's API before to load soundpool
 * @return
 */

//    @SuppressWarnings("deprecation")
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    private SoundPool buildSoundPool() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            AudioAttributes audioAttributes = new AudioAttributes.Builder()
//                    .setUsage(AudioAttributes.USAGE_GAME)
//                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
//                    .build();
//
//            soundPool = new SoundPool.Builder()
//                    .setMaxStreams(25)
//                    .setAudioAttributes(audioAttributes)
//                    .build();
//        } else {
//            buildBeforeAPI21();
//        }
//        return soundPool;
//    }
//    public void buildBeforeAPI21() {
//
//        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
//        actVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
//        maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
//        volume = actVolume / maxVolume;
//
//        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
//
//        counter = 0;
//
//        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
//        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
//            @Override
//            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
//                loaded = true;
//            }
//        });
//
//        soundID = soundPool.load(this, R.raw.fodasse, 1);
//    }

}
