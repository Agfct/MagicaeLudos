/*
 * Copyright (c) 2016 Anders Lunde
 */

package com.magicaeludos.mobile.magicaeludos.framework;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Anders on 27.01.2016.
 * The sprite class is supposed to contain all necessary information to draw a sprite.
 */
public class Sprite {

    private int x,y;
    private int width, height;
    private Bitmap bitmap;
    private Rect dstBounds;
    private ArrayList<Rect> srcBounds;
//    private Rect srcBounds;
    private Paint paint;
    private int srcBound = 0;

    //Animation
    private boolean animated = false;
    private int nrOfFrames;
    private int nrOfTypes;
    private int frameLength;
    private int frameHeight;
    private int animationLength = 8; //Number of ms between next frame
    private int animationCounter = 8; //counter.


    public Sprite(int x, int y, int width, int height, Bitmap bitmap) {
        this.srcBounds = new ArrayList<>();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.bitmap = bitmap;
        this.dstBounds = new Rect(x,y,x+width,y+height);
        this.srcBounds.add(new Rect(0,0,bitmap.getWidth(),bitmap.getHeight()));
        paint = new Paint();
    }

    //Constructor for animated sprites
    public Sprite(int x, int y, int width, int height, Bitmap bitmap , int nrOfFrames) {
        this.srcBounds = new ArrayList<>();
        this.animated = true;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.bitmap = bitmap;
        this.nrOfFrames = nrOfFrames;
        this.nrOfTypes = 1;
        this.frameLength = bitmap.getWidth()/nrOfFrames;
        this.frameHeight = bitmap.getHeight()/nrOfTypes;
        this.dstBounds = new Rect(x,y,x+width,y+height);
        this.srcBounds.add(new Rect(0,0,frameLength,bitmap.getHeight()));
        paint = new Paint();
    }

    //Constructor for animated sprites with several types of animations (run, jump, and so on)
    public Sprite(int x, int y, int width, int height, Bitmap bitmap , int nrOfFrames, int nrOfAnimationTypes) {
        this.srcBounds = new ArrayList<>();
        this.animated = true;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.bitmap = bitmap;
        this.animated = true;
        this.nrOfFrames = nrOfFrames;
        this.nrOfTypes = nrOfAnimationTypes;
        this.frameLength = bitmap.getWidth()/nrOfFrames;
        this.frameHeight = bitmap.getHeight()/nrOfTypes;
        this.dstBounds = new Rect(x,y,x+width,y+height);
        for (int i = 0; i < nrOfAnimationTypes; i++){
            this.srcBounds.add(new Rect(0, i * frameHeight, frameLength, frameHeight + frameHeight * i));
        }
        paint = new Paint();
    }

    //Like an update() method but purely animation focused
    public void animate(){
//        Log.w("Sprite", "Animated (1)");
        if(animated){
            if(animationCounter <= 0){
                animationCounter = animationLength;
                //If at the end of the animation we reset
                if((srcBounds.get(srcBound).right+frameLength) > bitmap.getWidth()){
                    setSrcBounds(new Rect(0, srcBound * frameHeight, frameLength, frameHeight));

                }else{
                    setSrcBounds(new Rect(srcBounds.get(srcBound).left + frameLength, srcBounds.get(srcBound).top, srcBounds.get(srcBound).right + frameLength, srcBounds.get(srcBound).bottom));
                }
            }else {
                animationCounter -=1;
            }


        }
    }

    public void animateJump(){
        Log.w("Sprite", "Animated Jump (2)" + srcBound);
        if(animated){
            if(animationCounter <= 0){
                animationCounter = animationLength;
                //If at the end of the animation we reset
                Log.w("Sprite", "Animated Jump (3)"+ srcBound);
                if((srcBounds.get(srcBound).right+frameLength) > bitmap.getWidth()){
                    Log.w("Sprite", "Animated Jump (IF)"+ srcBound);
                    setSrcBounds(new Rect(0, srcBound * frameHeight, frameLength, frameHeight + (srcBound * frameHeight)));
                }else{
                    Log.w("Sprite", "Animated Jump (else)"+ srcBound);
                    setSrcBounds(new Rect(srcBounds.get(srcBound).left + frameLength, srcBounds.get(srcBound).top, srcBounds.get(srcBound).right + frameLength, srcBounds.get(srcBound).bottom));
                }
            }else {
                animationCounter -=1;
            }


        }
    }

    public void draw(Canvas canvas){
        setDstBounds(new Rect(x, y, x + width, y + height));
        if (nrOfTypes == 2) {
            Log.w("Sprite", "SrcBounds: " + srcBounds.get(srcBound) + " src: " + srcBound);
        }
        canvas.drawBitmap(bitmap, srcBounds.get(srcBound), getDstBounds(),paint);
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public Bitmap getBitmap() {
        return bitmap;
    }
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
    public Rect getDstBounds() {
        return dstBounds;
    }
    public void setDstBounds(Rect dstBounds) {
        this.dstBounds = dstBounds;
    }
    public Rect getSrcBounds() {
        return srcBounds.get(srcBound);
    }
    public void setSrcBounds(Rect srcBounds) {
        this.srcBounds.add(srcBound,srcBounds);
    }
    public Paint getPaint() {
        return paint;
    }
    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public void setSrcBound(int srcBound) {
        this.srcBound = srcBound;

        //Resets the Animation to default
        for (int i = 0; i < nrOfTypes; i++){
            this.srcBounds.set(i, new Rect(0, i * frameHeight, frameLength, frameHeight + frameHeight * i));
        }
    }

    public void setAnimationTime(int animationTime) {
        this.animationLength = animationTime;
        this.animationCounter = animationTime;
    }

}
