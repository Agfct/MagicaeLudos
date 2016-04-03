/*
 * Copyright (c) 2015 Anders Lunde, Robin Sjøvoll, Kristian Huse. All rights reserved.
 */

package com.magicaeludos.mobile.magicaeludos.framework;

/**
 * Created by Kristian on 13/04/2015. Modified by Anders 02/04/2016
 * BGM: Back Ground Music.
 */
public interface BGM {

    public void play();

    public void stop();

    public void pause();

    public void setLooping(Boolean isLooping);

    public boolean isLooping();

    public void setVolume(float db);

    public boolean isPlaying();

    public void dispose();


}
