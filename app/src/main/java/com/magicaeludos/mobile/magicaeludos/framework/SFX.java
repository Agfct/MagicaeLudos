/*
 * Copyright (c) 2015 Anders Lunde, Robin Sjøvoll, Kristian Huse. All rights reserved.
 */

package com.magicaeludos.mobile.magicaeludos.framework;

/**
 * Created by Kristian on 13/04/2015. Modified by Anders 02/04/2016
 * SFX: Sound Effects.
 */
public interface SFX {

    public void play();

    public void loopedPlay();

    public void stop();

    public void setVolume(float db); // Consider setting the volume directly in play(float db);

    public void dispose();

}
