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


    public void addCleanWater(int cleanWaterAmount){
        int totalWater = cleanWater + dirtyWater;
        int spaceLeft = (maxAmountOfWater - totalWater);
        if( cleanWaterAmount > spaceLeft){
            this.cleanWater += spaceLeft;
        }else{
            this.cleanWater += cleanWaterAmount;
        }
        Log.w("Water", "Added MaxWater: "+ maxAmountOfWater+ " totalWater "+ totalWater);

    }
    private void setCleanWater(int cleanWater){
        this.cleanWater = cleanWater;
    }

    public void addDirtyWater(int dirtyWaterAmount){
        int totalWater = cleanWater + dirtyWater;
        int spaceLeft = (maxAmountOfWater - totalWater);
        if( dirtyWaterAmount > spaceLeft){
            this.dirtyWater += spaceLeft;
        }else{
            this.dirtyWater += dirtyWaterAmount;
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
