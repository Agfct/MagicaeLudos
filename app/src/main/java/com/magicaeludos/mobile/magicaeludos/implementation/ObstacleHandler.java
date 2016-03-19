package com.magicaeludos.mobile.magicaeludos.implementation;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.magicaeludos.mobile.magicaeludos.R;
import com.magicaeludos.mobile.magicaeludos.framework.ObstacleProb;
import com.magicaeludos.mobile.magicaeludos.framework.ObstacleType;
import com.magicaeludos.mobile.magicaeludos.framework.Probability;
import com.magicaeludos.mobile.magicaeludos.implementation.activities.GameContent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by MortenAlver on 07.03.2016.
 */
public class ObstacleHandler {
    List<Obstacle> obstacles = new ArrayList<Obstacle>();
    private GameContent content;
    private Probability prop;

    public ObstacleHandler(GameContent content){
        this.content = content;
    }

    public void addObstacle(){
        prop = content.getProp();
        ObstacleProb oProb = prop.sendObstacles();
        if (oProb != null){
            switch (oProb.getName()) {
                case WATER_DROP:
                    obstacles.add(new Obstacle(content,
                            BitmapFactory.decodeResource(content.getActivity().getResources(),
                                    R.drawable.teardrop), oProb.getLane(), ObstacleType.WATER_DROP));
                    break;
                case STONE:
                    obstacles.add(new Obstacle(content,
                            BitmapFactory.decodeResource(content.getActivity().getResources(),
                                    R.mipmap.ic_launcher), oProb.getLane(), ObstacleType.STONE));
                default:
            }
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

    public Obstacle checkCollision(){
        Rect playerBox = content.getPlayer().getHitBox();
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
                            return o;
                        }
                    }
                }
            }
        }
        return null;
    }

    public void update(){
        addObstacle();

        moveObstacles();
        Obstacle obstacle = checkCollision();
        if (obstacle != null){
            switch (obstacle.getType()) {
                case WATER_DROP:
                    obstacles.remove(obstacle);
                    content.water.addWaterAmount(20);
                    break;
                case STONE:
                    try {
                        Thread.sleep(100);  //TODO: FIX               //1000 milliseconds is one second.
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                    break;
                default:
                    try {
                        Thread.sleep(100);                 //1000 milliseconds is one second.
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
            }
        }
    }

    public void draw(Canvas canvas){
        for (Obstacle o : obstacles){
            o.draw(canvas);
        }
    }
}
