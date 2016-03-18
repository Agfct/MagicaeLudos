package com.magicaeludos.mobile.magicaeludos.framework;

/**
 * Created by Anders Lunde on 02.03.2016.
 */
public class Water {

    private int cleanWater;
    private int dirtyWater;
    private int maxAmountOfWater;

    public Water (int startingAmount, int maxAmountOfWater){
        this.cleanWater = 0;
        this.dirtyWater = 0;
        this.maxAmountOfWater = 200;
        //Initialize water here
    }


    public void addCleanWater(int cleanWaterAmount){
        this.cleanWater += cleanWaterAmount;
    }
    private void setCleanWater(int cleanWater){
        this.cleanWater = cleanWater;
    }

    public void addDirtyWater(int dirtyWaterAmount){
        this.dirtyWater += dirtyWaterAmount;
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
