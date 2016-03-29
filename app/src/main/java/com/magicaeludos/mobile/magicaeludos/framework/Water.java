package com.magicaeludos.mobile.magicaeludos.framework;

import android.util.Log;

/**
 * Created by Anders Lunde on 02.03.2016.
 */
public class Water {

    private int cleanWater;
    private int dirtyWater;
    private int maxAmountOfWater;

    public Water (int startingAmount, int maxAmountOfWater){
        this.cleanWater = startingAmount;
        this.dirtyWater = 0;
        this.maxAmountOfWater = maxAmountOfWater;
    }

//TODO: CHECK THIS if its correct. (Water amount was 300 on the first run. 200 on second run)
    public void addCleanWater(int cleanWaterAmount){
        int totalWater = cleanWater + dirtyWater;
        int spaceLeft = (maxAmountOfWater - totalWater);
        if(cleanWaterAmount >= 0){
            if( cleanWaterAmount > spaceLeft){
                this.cleanWater += spaceLeft;
            }else{
                this.cleanWater += cleanWaterAmount;
            }
        }else{
            if( cleanWater + cleanWaterAmount < 0){
                cleanWater = 0;
            }else {
                cleanWater += cleanWaterAmount;
            }
        }
       
        Log.w("Water", "Added MaxWater: "+ maxAmountOfWater+ " totalWater "+ totalWater);

//    public void addWaterAmount(int waterAmount){
//        this.waterAmount += waterAmount;
//        if (this.waterAmount>maxAmountOfWater){
//            this.waterAmount = maxAmountOfWater;
//        }
//        else if( this.waterAmount<0) {
//            this.waterAmount = 0;
//        }
    }
    private void setCleanWater(int cleanWater){
        this.cleanWater = cleanWater;
    }

    public void addDirtyWater(int dirtyWaterAmount){
        int totalWater = cleanWater + dirtyWater;
        int spaceLeft = (maxAmountOfWater - totalWater);
        if(dirtyWaterAmount >= 0){
            if( dirtyWaterAmount > spaceLeft){
                this.dirtyWater += spaceLeft;
            }else{
                this.dirtyWater += dirtyWaterAmount;
            } 
        }else{
            if( dirtyWater + dirtyWaterAmount < 0){
                dirtyWater = 0;
            }else {
                dirtyWater += dirtyWaterAmount;
            }
        }
    
        Log.w("Water", "Added MaxWater: "+ maxAmountOfWater+ " totalWater "+ totalWater);
    }

    private void setDirtyWater(int dirtyWater){
        this.dirtyWater = dirtyWater;
    }

    public int getCleanWater() {
        return cleanWater;
    }

    public int getDirtyWater() {
        return dirtyWater;
    }

    public int getMaxAmountOfWater() {
        return maxAmountOfWater;
    }
}
