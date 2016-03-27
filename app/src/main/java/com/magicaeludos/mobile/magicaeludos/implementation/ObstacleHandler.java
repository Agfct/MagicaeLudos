package com.magicaeludos.mobile.magicaeludos.implementation;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.magicaeludos.mobile.magicaeludos.R;
import com.magicaeludos.mobile.magicaeludos.framework.ObstacleType;
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

//    hitbox sizes
    private final int hitboxWidthWater = 110;
    private final int hitboxHeightWater = 0;
    private final int hitboxWidthStone = 110;
    private final int hitboxHeightStone = 0;
    private final int hitboxWidthLog = 110;
    private final int hitboxHeightLog = 0;



    public ObstacleHandler(GameContent content){
        this.content = content;
    }

    public void addObstacle(){
        // test until the probability is done
        double rand = Math.random();
        if (rand < 0.04) {
            int lane = (int) (3 * Math.random()) + 1;
            Obstacle o;
            if (rand < 0.01) {
                o = createWaterDrop(lane);
            }
            else if (rand < 0.02) {
                o = createPuddle(lane);
            }
            else if (rand < 0.03) {
                o = createLog(lane);
            }
            else {
                o = createStone(lane);
            }
            obstacles.add(o);
        }
    }

    public void moveObstacles(){
        for (Iterator<Obstacle> iterator = obstacles.iterator(); iterator.hasNext(); ) {
            Obstacle o = iterator.next();
            o.setY(o.getY()+content.getSpeed());
            o.sprite.setY((int)o.getY());
            Log.v("Y", ""+o.getY()+" speed: "+content.getSpeed());
            if (o.sprite.getY()>content.getGrid().getScreenHeight()) {
                iterator.remove();
            }

//            o.sprite.setY(o.sprite.getY()+o.getDy());
//            if (o.sprite.getY()>content.getGrid().getScreenHeight()) {
//                iterator.remove();
//            }
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
                    content.water.addWaterAmount(content.getWaterDropAmount());
                    break;
                case PUDDLE:
                    content.water.addWaterAmount(content.getWaterDropAmount()/10);
                    break;
                case STONE:
                    if (!obstacle.getCollition()) {
                        try {
                            Thread.sleep(200);  //TODO: FIX               //1000 milliseconds is one second.
                        } catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }
                        obstacle.setCollition(true);
                        content.water.addWaterAmount(-50);
                    }
                case LOG:
                    if (!obstacle.getCollition()) {
                        try {
                            Thread.sleep(200);  //TODO: FIX               //1000 milliseconds is one second.
                        } catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }
                        obstacle.setCollition(true);
                        content.water.addWaterAmount(-50);
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

    private Obstacle createWaterDrop(int lane){
        Obstacle o = new Obstacle(content,
                BitmapFactory.decodeResource(content.getActivity().getResources(),
                        R.drawable.teardrop), lane, ObstacleType.WATER_DROP);
        o.setHitBoxDifferences(hitboxWidthWater, hitboxHeightWater);
        return o;
    }

    private Obstacle createStone(int lane){
        Obstacle o = new Obstacle(content,
                BitmapFactory.decodeResource(content.getActivity().getResources(),
                        R.drawable.stone_smal),lane, ObstacleType.STONE);
        o.sprite.setWidth(o.sprite.getWidth()*2);
        o.setHitBoxDifferences(hitboxWidthStone, hitboxHeightStone);
        return o;
    }

    private Obstacle createLog(int lane){
//        TODO:: create log obstacle
        Obstacle o = new Obstacle(content,
                BitmapFactory.decodeResource(content.getActivity().getResources(),
                        R.mipmap.ic_launcher),lane, ObstacleType.LOG);
        o.sprite.setWidth(content.getGrid().getColWidth()*2);
        o.setHitBoxDifferences(hitboxWidthLog, hitboxHeightLog);
        return o;
    }

    private Obstacle createPuddle(int lane){
        Obstacle o = new Obstacle(content,
                BitmapFactory.decodeResource(content.getActivity().getResources(),
                        R.mipmap.ic_launcher),lane, ObstacleType.PUDDLE);
        o.sprite.setHeight(o.sprite.getHeight()*3);
        o.setHitBoxDifferences(hitboxWidthLog, hitboxHeightLog);
        return o;
    }
}
