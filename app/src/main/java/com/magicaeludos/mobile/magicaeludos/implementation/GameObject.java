package com.magicaeludos.mobile.magicaeludos.implementation;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;

import com.magicaeludos.mobile.magicaeludos.framework.Sprite;
import com.magicaeludos.mobile.magicaeludos.implementation.activities.GameContent;
public class GameObject {

    GameContent content;
    private double x;
    private double y;
    protected int width;
    protected int height;
    protected Sprite sprite;

    public GameObject(){}

    public GameObject(GameContent content, Point point, int width, int height, Bitmap sprite){
        this.content = content;
        this.x = point.x;
        this.y = point.y;
        //To account for the village taking the whole screen
        if(width > content.getGrid().getScreenWidth()){
            this.width = content.getGrid().getColWidth()*4;
        }else {
            this.width = width;
        }
        this.height = height;
        this.sprite = new Sprite((int)x,(int)y,this.width,height,sprite);
    }

    //Game object using animations
    public GameObject(GameContent content, Point point, int width, int height, Bitmap sprite, int frames){
        this.content = content;
        this.x = point.x;
        this.y = point.y;
        this.width = width;
        this.height = height;
        this.sprite = new Sprite((int)x,(int)y,width,height,sprite, frames);
    }

    //Game object using several animations
    public GameObject(GameContent content, Point point, int width, int height, Bitmap sprite, int frames, int animationTypes){
        this.content = content;
        this.x = point.x;
        this.y = point.y;
        this.width = width;
        this.height = height;
        this.sprite = new Sprite((int)x,(int)y,width,height,sprite, frames, animationTypes);
    }


    public void setSpriteImage(Bitmap image){sprite.setBitmap(image);}
    public Sprite getSprite(){return sprite;}
    public double getX(){return x;}
    public void getX(double x){this.x = x;}
    public double getY(){return y;}
    public void getY(double y){this.y = y;}

    public Rect getHitBox(){
        return new Rect(sprite.getX(),sprite.getY(),sprite.getHeight(),sprite.getWidth());
    }

    public void setX(double x) {
        this.x = x;
        sprite.setX((int)x);
    }

    public void setY(double y) {
        this.y = y;
        sprite.setY((int) y);
    }
}
