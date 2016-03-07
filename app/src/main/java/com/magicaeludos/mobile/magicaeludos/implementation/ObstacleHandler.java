package com.magicaeludos.mobile.magicaeludos.implementation;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.magicaeludos.mobile.magicaeludos.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by MortenAlver on 07.03.2016.
 */
public class ObstacleHandler {
    List<Obstacle> obstacles = new ArrayList<Obstacle>();
    private GameContent content;

    public ObstacleHandler(GameContent content){
        this.content = content;
    }

    public void addObstacle(){
        // test until the probability is done
        double rand = Math.random();
        if (rand < 0.04) {
            int lane = (int) (3 * Math.random()) + 1;
            Obstacle o;
            if (rand < 0.03) {
                o = new Obstacle(content,
                        BitmapFactory.decodeResource(content.getActivity().getResources(),
                                R.drawable.teardrop), lane);
            }
            else {
                o = new Obstacle(content,
                        BitmapFactory.decodeResource(content.getActivity().getResources(),
                                R.mipmap.ic_launcher),lane);
            }
            obstacles.add(o);
        }
    }

    public void moveObstacles(){
        for (Iterator<Obstacle> iterator = obstacles.iterator(); iterator.hasNext(); ) {
            Obstacle o = iterator.next();
            o.sprite.setY(o.sprite.getY()+o.getDy());
            if (o.sprite.getY()>content.getGrid().getScreenHeight()) {
                iterator.remove();
            }
        }
    }

    public boolean checkCollision(){
        Rect playerBox = content.player.getHitBox();
        int pHeight = playerBox.right;
        int pWidth = playerBox.bottom;
        for (Obstacle o: obstacles){
            Rect oBox = o.getHitBox();
            int oHeight = oBox.bottom;
            int oWidth = oBox.right;
            if (oBox.top+oHeight>playerBox.top){
                if (oBox.top<playerBox.top+pHeight){
                    if (oBox.left+oWidth>playerBox.left){
                        if (oBox.left<playerBox.left+pWidth){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public void update(){
        addObstacle();

        moveObstacles();
        if (checkCollision()){
            try {
                Thread.sleep(1000);                 //1000 milliseconds is one second.
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void draw(Canvas canvas){
        for (Obstacle o : obstacles){
            o.draw(canvas);
        }
    }
}
