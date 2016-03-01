package com.magicaeludos.mobile.magicaeludos.implementation;

import android.graphics.Bitmap;

/**
 * Created by MortenAlver on 29.02.2016.
 */
public class Obstacle extends GameObject {
    public Obstacle(double x, double y, int width, int height, Bitmap sprite){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.sprite = sprite;
    }
}
