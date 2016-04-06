package com.magicaeludos.mobile.magicaeludos.implementation;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Vibrator;
import android.util.Log;

import com.magicaeludos.mobile.magicaeludos.R;
import com.magicaeludos.mobile.magicaeludos.framework.MotherActivity;
import com.magicaeludos.mobile.magicaeludos.framework.ObstacleProb;
import com.magicaeludos.mobile.magicaeludos.framework.ObstacleType;
import com.magicaeludos.mobile.magicaeludos.framework.Probability;
import com.magicaeludos.mobile.magicaeludos.framework.SFX;
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
    private final double hitboxWidthWater = 0.2;
    private final double hitboxHeightWater = 0.2;
    private final double hitboxWidthStone = 0.2;
    private final double hitboxHeightStone = 0.2;
    private final double hitboxWidthLog = 0.2;
    private final double hitboxHeightLog = 0.3;
    private final double hitboxWidthPuddle = 0.1;
    private final double hitboxHeightPuddle= 0.0;
    private final double hitboxWidthTree = 0.1;
    private final double hitboxHeightTree= 0.0;

    //Obstacle sounds (SFX)
    private SFX sfx_waterDrop;
    private SFX sfx_stone;
    private SFX sfx_wood;

    //Vibration
    private Vibrator vibrator;



    public ObstacleHandler(GameContent content){
        this.content = content;
        addSounds();
        vibrator =  (Vibrator) content.getActivity().getSystemService(Context.VIBRATOR_SERVICE);
    }

    private void addSounds(){
        MotherActivity activity = content.getActivity();
        sfx_waterDrop = content.getGameAudio().createSound(activity.getBaseContext(), R.raw.water_drop, activity.getVillage().getSfxLevel());
        sfx_stone = content.getGameAudio().createSound(activity.getBaseContext(), R.raw.stone, activity.getVillage().getSfxLevel());
        sfx_wood = content.getGameAudio().createSound(activity.getBaseContext(), R.raw.wood_hard, activity.getVillage().getSfxLevel());
    }

    public void addObstacle(){
        prop = content.getProp();
        ObstacleProb oProb = prop.sendObstacles();
        if (oProb != null) {
            Obstacle o;
            switch (oProb.getName()) {
                case WATER_DROP:
                    Log.w("ObstacleHandler", "Water_drop");
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
                    break;
                case PUDDLE:
                    o = createPuddle(oProb.getLane());
                    obstacles.add(o);
                    break;
                case TREE:
                    o = createTree(oProb.getLane());
                    obstacles.add(o);
                    break;
                default:
                    Log.v("Add obstacle","Try to add not existing obstacle");
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
        for (Obstacle o: obstacles) {
            Rect oBox = o.getHitBox();
            if(Rect.intersects(playerBox,oBox)){
                return o; //TODO: Sprite HitBox Fixed, but now Collision does not work.
            }
//            int oHeight = oBox.bottom;
//            int oWidth = oBox.right;
//            if (oBox.top + oHeight > playerBox.top) {
//                if (oBox.top < playerBox.top + pHeight) {
//                    if (oBox.left + oWidth > playerBox.left) {
//                        if (oBox.left < playerBox.left + pWidth) {
//                            return o;
//                        }
//                    }
//                }
//            }
        }
        return null;
    }

    public void update(){
        if (!content.getEnding()) {
            addObstacle();
        }

        moveObstacles();
        Obstacle obstacle = checkCollision();
        if (obstacle != null){
            if (obstacle.getType() == ObstacleType.VILLAGE){
                content.endGame();
            }
            if (content.getPlayer().getJumpVariable() == 0) {
                switch (obstacle.getType()) {
                    case WATER_DROP:
                        sfx_waterDrop.play();
                        obstacles.remove(obstacle);
                        CollectableHit(obstacle.getType());
                        break;
                    case PUDDLE:
                        CollectableHit(obstacle.getType());
                        break;
                    case STONE:
                        if (!obstacle.getCollition()) {
                            sfx_stone.play();
                            vibrator.vibrate(100);
                            ObstacleHit(obstacle.getType());
                            obstacle.setCollition(true);
                        }
                        break;
                    case LOG:
                        if (!obstacle.getCollition()) {
                            if (!obstacle.getCollition()) {
                                sfx_wood.play();
                                ObstacleHit(obstacle.getType());
                                obstacle.setCollition(true);
                            }
                        }
                        break;
                    case TREE:
                        if (!obstacle.getCollition()) {
                            sfx_wood.play();
                            ObstacleHit(obstacle.getType());
                            obstacle.setCollition(true);
                            break;
                        }
                }
            } else {
                switch (obstacle.getType()) {
                    case TREE:
                        if (!obstacle.getCollition()) {
                            sfx_wood.play();
                            ObstacleHit(obstacle.getType());
                            obstacle.setCollition(true);
                            break;
                        }
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
                        R.drawable.clean_water), lane, 1, 1, ObstacleType.WATER_DROP);
        o.setHitBoxDifferences(hitboxWidthWater, hitboxHeightWater);
        return o;
    }

    private Obstacle createStone(int lane){
        Obstacle o = new Obstacle(content,
                BitmapFactory.decodeResource(content.getActivity().getResources(),
                        R.drawable.stone_smal),lane, 1, 1, ObstacleType.STONE);
//        o.sprite.setWidth(o.sprite.getWidth()*2);
        o.setHitBoxDifferences(hitboxWidthStone, hitboxHeightStone);
        return o;
    }

    private Obstacle createLog(int lane){
//        TODO:: create log obstacle (Fix size ? )
        Obstacle o = new Obstacle(content,
                BitmapFactory.decodeResource(content.getActivity().getResources(),
                        R.drawable.log),lane, 2, 1, ObstacleType.LOG);
//       o.sprite.setWidth(content.getGrid().getColWidth() * 2);
//       o.sprite.setHeight(o.sprite.getHeight()*2);
        o.setHitBoxDifferences(hitboxWidthLog, hitboxHeightLog);
        return o;
    }

    private Obstacle createPuddle(int lane){
        Obstacle o = new Obstacle(content,
                BitmapFactory.decodeResource(content.getActivity().getResources(),
                        R.drawable.puddle),lane, 2, 2, ObstacleType.PUDDLE);
        o.setHitBoxDifferences(hitboxWidthPuddle, hitboxHeightPuddle);
        return o;
    }

    private Obstacle createTree(int lane){
        Obstacle o = new Obstacle(content,
                BitmapFactory.decodeResource(content.getActivity().getResources(),
                        R.drawable.tree),lane, 2, 2, ObstacleType.TREE);
        o.setHitBoxDifferences(hitboxWidthTree, hitboxHeightTree);
        return o;
    }

    private void ObstacleHit(ObstacleType oType){
        try {
            Thread.sleep(200);  //TODO: FIX               //1000 milliseconds is one second.
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        content.water.addCleanWater(-content.getWaterDropAmount() * 5); //TODO: Create a variable
        content.incrementHitCounter();
    }

    private void CollectableHit(ObstacleType oType){
        switch (oType){
            case WATER_DROP:
                content.water.addCleanWater(content.getWaterDropAmount());
                break;
            case PUDDLE:
                content.water.addDirtyWater(content.getWaterDropAmount() / 10);
                break;
        }
    }

    public void add(Obstacle o){
        obstacles.add(o);
    }
}
