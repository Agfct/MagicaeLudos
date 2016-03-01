package com.magicaeludos.mobile.magicaeludos.implementation;

import android.graphics.Bitmap;

public class GameObject {
    protected double x;
    protected double y;
    protected int width;
    protected int height;
    protected Bitmap sprite;

    public GameObject(){}

    public GameObject(double x, double y, int width, int height, Bitmap sprite){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.sprite = sprite;
    }

    public void setSprite(Bitmap image){sprite = image;}
    public Bitmap getSprite(){return sprite;}
    public double getX(){return x;}
    public void getX(double x){this.x = x;}
    public double getY(){return y;}
    public void getY(double y){this.y = y;}
}
