package com.magicaeludos.mobile.magicaeludos.implementation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

import com.magicaeludos.mobile.magicaeludos.framework.Grid;
import com.magicaeludos.mobile.magicaeludos.framework.ObstacleType;
import com.magicaeludos.mobile.magicaeludos.framework.Sprite;
import com.magicaeludos.mobile.magicaeludos.implementation.activities.GameContent;
public class Obstacle extends GameObject {
//    private Bitmap image;
    private GameContent content;
    private int hitBoxDifferenceWidth, hitBoxDifferenceHeight;
    private double y;
    private double speed;
    private Rect srcRect;
    private Rect destRect;
    private Grid grid;
    private int nr;
    private ObstacleType obstacleType;
    private boolean collition = false;


    public Obstacle(GameContent content, Bitmap spriteSheet, int nr, ObstacleType obstacleType){
        super( content, new Point(content.getGrid().getLane(nr).x+content.getGrid().getColWidth()/2-spriteSheet.getWidth()/2,content.getGrid().getLane(nr).y-spriteSheet.getHeight()), spriteSheet.getWidth(), spriteSheet.getHeight(), spriteSheet);
        this.nr = nr;
        speed = content.getSpeed();
        this.obstacleType = obstacleType;
    }

    public void draw(Canvas canvas) {
        sprite.draw(canvas);
    }

    public Sprite getImage(){return sprite;}

    public Rect getScrRect(){return srcRect;}

    public Rect getDestRect(){return destRect;}

    public double getY(){return y;}

    public void setY(double y){this.y = y;}

    public ObstacleType getType(){return obstacleType;}

    public void setHitBoxDifferences(int hitBoxDifferenceWidth, int hitBoxDifferenceHeight){
        this.hitBoxDifferenceWidth = hitBoxDifferenceWidth;
        this.hitBoxDifferenceHeight = hitBoxDifferenceHeight;
    }

    public boolean getCollition(){return collition;}

    public void setCollition(boolean collition){this.collition = collition;}

    @Override
    public Rect getHitBox(){
        return new Rect(sprite.getX()+hitBoxDifferenceWidth/2,
                sprite.getY()+hitBoxDifferenceHeight/2,
                sprite.getWidth()-hitBoxDifferenceWidth,
                sprite.getHeight()-hitBoxDifferenceHeight);
    }
}
