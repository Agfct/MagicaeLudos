package com.magicaeludos.mobile.magicaeludos.framework;
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
    private int waterDropAmount;


    public GameSetting (GameContent content, int gameDifficulty){
        this.content = content;
        this.gameDifficulty = gameDifficulty;
        setValues();
    }

    /**
     * This method defines all the different random variables that controlls difficulty
     */
    private void setValues(){

        if(gameDifficulty == 1){
            gameTime = 60;
            gameSpeed = 10;
            water = new Water(0,200); //TODO: Add upgrades that increases water amount
            waterDropAmount = 20;
        }else {
            gameTime = 60;
            gameSpeed = 5;
            water = new Water(0,200);
            waterDropAmount = 30;
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
