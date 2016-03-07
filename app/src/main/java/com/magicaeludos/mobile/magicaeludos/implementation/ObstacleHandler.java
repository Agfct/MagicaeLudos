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

    public boolean checkCollition(){
        Rect playerBox = content.player.getHitBox();
        for (Obstacle o: obstacles){
            // TODO:: Check if player collides with obstacle
//            Rect oBox = o.getHitBox();
//            if (oBox.top+oBox.height()>playerBox.top){
//                if (oBox.top>playerBox.top+playerBox.height()){
//                    if (oBox.left+oBox.width()<playerBox.left){
//                       if (oBox.left>playerBox.left+playerBox.bottom){
//                            return true;
//                        }
//                    }
//                }
//            }
        }
        return false;
    }

    public void update(){
        addObstacle();

        moveObstacles();
        checkCollition();
    }

    public void draw(Canvas canvas){
        for (Obstacle o : obstacles){
            o.draw(canvas);
        }
    }
}
