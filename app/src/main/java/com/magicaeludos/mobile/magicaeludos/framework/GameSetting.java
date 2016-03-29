package com.magicaeludos.mobile.magicaeludos.framework;
import android.util.Log;

import com.magicaeludos.mobile.magicaeludos.implementation.Village;
import com.magicaeludos.mobile.magicaeludos.implementation.activities.GameContent;
/**
 * Created by Anders on 14.03.2016.
 * Contains settings on game difficulty and length.
 */
public class GameSetting {


    private GameContent content;
    private double gameTime;
    private int gameSpeed;
    private int gameDifficulty;
    private Water water;
    private Village village;
    private int waterDropAmount;


    public GameSetting (GameContent content, int gameDifficulty){
        this.content = content;
        this.gameDifficulty = gameDifficulty;
        this.village = content.getActivity().getVillage();
        setValues();
    }

    /**
     * This method defines all the different random variables that controlls difficulty
     */
    private void setValues(){

        Log.w("GameSettings","Difficulty: " +gameDifficulty+ " bucketSize: " + village.getBucketSize());
        if(gameDifficulty == 1){
            gameTime = 60;
            gameSpeed = 10;
            waterDropAmount = 20;
            water = new Water(0,village.getBucketSize());
        }else {
            gameTime = 60;
            gameSpeed = 5;
            waterDropAmount = 30;
            water = new Water(0,village.getBucketSize());
        }


        content.setGameTime(gameTime);
        content.setGameSpeed(gameSpeed);
        content.setWaterDropAmount(waterDropAmount);
    }

    public Water getWater() {
        return water;
    }

    public int getWaterDropAmount() {return waterDropAmount;}
}
