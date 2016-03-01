package com.magicaeludos.mobile.magicaeludos.implementation;

import android.graphics.Bitmap;

public class Player extends GameObject {

    public Player(double x, double y, int width, int height, Bitmap sprite){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.sprite = sprite;
    }
}
