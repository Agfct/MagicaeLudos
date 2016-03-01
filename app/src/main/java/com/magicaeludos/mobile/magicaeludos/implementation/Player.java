package com.magicaeludos.mobile.magicaeludos.implementation;

import android.graphics.Bitmap;
import android.renderscript.ScriptGroup;

/**
 * Created by MortenAlver on 29.02.2016.
 */
public class Player extends GameObject {
//    private double x;
//    private double y;
//    private int width;
//    private int height;
//    private Bitmap sprite;

    public Player(double x, double y, int width, int height, Bitmap sprite){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.sprite = sprite;
    }
}
