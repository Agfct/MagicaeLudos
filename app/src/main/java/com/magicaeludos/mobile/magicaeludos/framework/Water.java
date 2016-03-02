package com.magicaeludos.mobile.magicaeludos.framework;

/**
 * Created by Anders Lunde on 02.03.2016.
 */
public class Water {

    private int waterAmount;
    private int dirtyPercentage;
    private int maxAmountOfWater;

    public Water (){
        this.waterAmount = 0;
        this.dirtyPercentage = 0;
        this.maxAmountOfWater = 200;
        //Initialize water here
    }


    public void addWaterAmount(int waterAmount){
        this.waterAmount += waterAmount;
    }
    private void setWaterAmount(int waterAmount){
        this.waterAmount = waterAmount;
    }



}
