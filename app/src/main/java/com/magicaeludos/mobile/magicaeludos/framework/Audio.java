/*
 * Copyright (c) 2015 Anders Lunde, Robin Sjøvoll, Kristian Huse. All rights reserved.
 */

package com.magicaeludos.mobile.magicaeludos.framework;

import android.content.Context;
/**
 * Created by Kristian on 13/04/2015. Modified by Anders 02/04/2016
 * Interface for creating either Background music (BGM) or Sound Effect (SFX).
 */
public interface Audio {

    public BGM createMusic(Context c, int resId);

    public SFX createSound(Context c, int resId, int db);

}
