/*
 * Copyright (c) 2016 Anders Lunde
 */

package com.magicaeludos.mobile.magicaeludos.framework;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
/**
 * Created by Anders on 27.01.2016.
 * The sprite class is supposed to contain all necessary information to draw a sprite.
 */
public class Sprite {

    private int x,y;
    private int width, height;
    private Bitmap bitmap;
    private Rect dstBounds;
    private Rect srcBounds;
    private Paint paint;

    public Sprite(int x, int y, int width, int height, Bitmap bitmap) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.bitmap = bitmap;
        this.dstBounds = new Rect(x,y,x+width,y+height);
        this.srcBounds = new Rect(0,0,bitmap.getWidth(),bitmap.getHeight());
        paint = new Paint();
    }
    /**
     * Creates a sprite with set source and destination bounds
     * @param x
     * @param y
     * @param width
     * @param height
     * @param bitmap
     * @param srcBounds
     */
    public Sprite(int x, int y, int width, int height, Bitmap bitmap, Rect srcBounds) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.bitmap = bitmap;
        this.dstBounds = new Rect(x,y,x+width,y+height);
        this.srcBounds = srcBounds;
        paint = new Paint();
    }

    public void draw(Canvas canvas){
        setDstBounds(new Rect(x,y,x+width,y+height));
        canvas.drawBitmap(bitmap, srcBounds, getDstBounds(),paint);
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
        return srcBounds;
    }
    public void setSrcBounds(Rect srcBounds) {
        this.srcBounds = srcBounds;
    }
    public Paint getPaint() {
        return paint;
    }
    public void setPaint(Paint paint) {
        this.paint = paint;
    }
}
