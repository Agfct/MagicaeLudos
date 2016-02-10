/*
 * Copyright (c) 2016 Anders Lunde
 */

package com.magicaeludos.mobile.magicaeludos.framework;
import android.graphics.Canvas;
/**
 * Created by Anders on 11.01.2016.
 * Forces the class to have update and draw methods.
 * Content interface is used so that any content class can be sent as an argument to the Layout class.
 */
public interface Content {

    public void update();

    public void draw(Canvas canvas);

}
