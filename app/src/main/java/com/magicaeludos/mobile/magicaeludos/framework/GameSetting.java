package com.magicaeludos.mobile.magicaeludos.framework;
import com.magicaeludos.mobile.magicaeludos.implementation.activities.GameContent;
/**
 * Created by Anders on 14.03.2016.
 * Contains settings on game difficulty and length.
 */
public class GameSetting {


    private GameContent content;
    private double gameTime;
    private int gameDifficulty;


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
        }else {
            gameTime = 60;
        }


        content.setGameTime(gameTime);
    }
}
