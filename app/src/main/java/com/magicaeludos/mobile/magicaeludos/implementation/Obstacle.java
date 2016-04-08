package com.magicaeludos.mobile.magicaeludos.implementation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import com.magicaeludos.mobile.magicaeludos.framework.Grid;
import com.magicaeludos.mobile.magicaeludos.framework.ObstacleType;
import com.magicaeludos.mobile.magicaeludos.framework.Sprite;
import com.magicaeludos.mobile.magicaeludos.implementation.activities.GameContent;
public class Obstacle extends GameObject {
//    private Bitmap image;
    private double hitBoxDifferenceFactorWidth, hitBoxDifferenceFactorHeight;
    private double speed;
    private Rect srcRect;
    private Rect destRect;
    private Grid grid;
    private int nr;
    private ObstacleType obstacleType;
    private boolean collition = false;


    public Obstacle(GameContent content, Bitmap spriteSheet, int nr, int nrOfLanes, int nrOfRows, ObstacleType obstacleType){
        super( content, new Point(content.getGrid().getLane(nr).x+(int)((content.getGrid().getColWidth()-content.getGrid().getInnerWidth())/2),content.getGrid().getLane(nr).y-content.getGrid().getInnerHeight()*nrOfRows), content.getGrid().getColWidth()*(nrOfLanes-1)+content.getGrid().getInnerWidth(), content.getGrid().getInnerHeight()* nrOfRows, spriteSheet);
        this.nr = nr;
        speed = content.getSpeed();
        this.obstacleType = obstacleType;
    }

    public void draw(Canvas canvas) {
        sprite.draw(canvas);

        if(content.getActivity().isDeveloperModeOn()) {
            canvas.drawRect(getHitBox(),new Paint());
        }
    }

    public Sprite getImage(){return sprite;}

    public Rect getScrRect(){return srcRect;}

    public Rect getDestRect(){return destRect;}

    public double getObstacleY(){return getY();}

    public void setObstacleY(double y){setY(y);}

    public ObstacleType getType(){return obstacleType;}

    public void setHitBoxDifferences(double hitBoxDifferenceFactorWidth, double hitBoxDifferenceFactorHeight){
        this.hitBoxDifferenceFactorWidth = hitBoxDifferenceFactorWidth;
        this.hitBoxDifferenceFactorHeight = hitBoxDifferenceFactorHeight;
    }

    public boolean getCollition(){return collition;}

    public void setCollition(boolean collition){this.collition = collition;}

    //TODO: Adjust hitBoxSize
    @Override
    public Rect getHitBox(){
        return new Rect(sprite.getX()+(int)(hitBoxDifferenceFactorWidth*sprite.getWidth()),
                sprite.getY()+(int)(hitBoxDifferenceFactorHeight*sprite.getHeight()),
                sprite.getX()+sprite.getWidth()-(int)(hitBoxDifferenceFactorWidth*sprite.getWidth()),
                sprite.getY()+sprite.getHeight()-(int)(hitBoxDifferenceFactorHeight*sprite.getHeight()));
    }
}
