package com.magicaeludos.mobile.magicaeludos.implementation;

import android.graphics.Bitmap;
import android.graphics.Point;

public class Obstacle extends GameObject {

    public Obstacle(GameContent content, Point point, int width, int height, Bitmap spriteSheet){
        super( content, point, width, height, spriteSheet);
    }
}
