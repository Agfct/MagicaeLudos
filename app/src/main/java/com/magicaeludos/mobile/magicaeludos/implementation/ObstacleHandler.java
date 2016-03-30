package com.magicaeludos.mobile.magicaeludos.implementation;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

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
        prop = content.getProp();
        ObstacleProb oProb = prop.sendObstacles();
        if (oProb != null) {
            Obstacle o;
            switch (oProb.getName()) {
                case WATER_DROP:
                    o = createWaterDrop(oProb.getLane());
                    obstacles.add(o);
                    break;
                case STONE:
                    o = createStone(oProb.getLane());
                    obstacles.add(o);
                    break;
                case LOG:
                    o = createLog(oProb.getLane());
                    obstacles.add(o);
                default:
                    break;
            }
        }
    }

    public void moveObstacles(){
        for (Iterator<Obstacle> iterator = obstacles.iterator(); iterator.hasNext(); ) {
            Obstacle o = iterator.next();
            o.setY(o.getY()+content.getSpeed());
            o.sprite.setY((int)o.getY());
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
                    CollectableHit(obstacle.getType());
                    break;
                case PUDDLE:
                    CollectableHit(obstacle.getType());
                    break;
                case STONE:
                    if (!obstacle.getCollition()) {
                        ObstacleHit(obstacle.getType());
                        obstacle.setCollition(true);
                    }
                    break;
                case LOG:
                    if (!obstacle.getCollition()) {

                        if (!obstacle.getCollition()) {
                            ObstacleHit(obstacle.getType());
                            obstacle.setCollition(true);
                        }
                    }
                    break;
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
                        R.drawable.teardrop), lane, 1, ObstacleType.WATER_DROP);
        o.setHitBoxDifferences(hitboxWidthWater, hitboxHeightWater);
        return o;
    }

    private Obstacle createStone(int lane){
        Obstacle o = new Obstacle(content,
                BitmapFactory.decodeResource(content.getActivity().getResources(),
                        R.drawable.stone_smal),lane, 1, ObstacleType.STONE);
//        o.sprite.setWidth(o.sprite.getWidth()*2);
        o.setHitBoxDifferences(hitboxWidthStone, hitboxHeightStone);
        return o;
    }

    private Obstacle createLog(int lane){
//        TODO:: create log obstacle
        Obstacle o = new Obstacle(content,
                BitmapFactory.decodeResource(content.getActivity().getResources(),
                        R.drawable.log),lane, 2, ObstacleType.LOG);
        o.sprite.setWidth(content.getGrid().getColWidth() * 2);
        o.sprite.setHeight(o.sprite.getHeight()*2);
        o.setHitBoxDifferences(hitboxWidthLog, hitboxHeightLog);
        return o;
    }

    private Obstacle createPuddle(int lane){
        Obstacle o = new Obstacle(content,
                BitmapFactory.decodeResource(content.getActivity().getResources(),
                        R.mipmap.ic_launcher),lane, 2, ObstacleType.PUDDLE);
        o.sprite.setHeight(o.sprite.getHeight() * 3);
        o.setHitBoxDifferences(hitboxWidthLog, hitboxHeightLog);
        return o;
    }

    private void ObstacleHit(ObstacleType oType){
        try {
            Thread.sleep(200);  //TODO: FIX               //1000 milliseconds is one second.
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        content.incrementHitCounter();
        content.water.addCleanWater(-50); //TODO: Create a variable
    }

    private void CollectableHit(ObstacleType oType){
        switch (oType){
            case WATER_DROP:
                content.water.addCleanWater(5);
                break;
            case PUDDLE:
                content.water.addCleanWater(content.getWaterDropAmount() / 10);
                break;
        }
    }
}
