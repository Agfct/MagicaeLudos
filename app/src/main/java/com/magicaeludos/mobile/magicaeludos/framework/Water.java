package com.magicaeludos.mobile.magicaeludos.framework;

import android.util.Log;

/**
 * Created by Anders Lunde on 02.03.2016.
 */
public class Water {

    private double cleanWater;
    private double dirtyWater;
    private double totalCollectedWaterAmount;
    private int maxAmountOfWater;

    public Water (int startingAmount, int maxAmountOfWater){
        this.cleanWater = startingAmount;
        this.dirtyWater = 0;
        this.totalCollectedWaterAmount = 0;
        this.maxAmountOfWater = maxAmountOfWater;
    }

    // Adds clean water (negative or positive value)
    public void addCleanWater(double cleanWaterAmount){
        double totalWater = cleanWater + dirtyWater;
        double spaceLeft = (maxAmountOfWater - totalWater);
        if(cleanWaterAmount >= 0){
            totalCollectedWaterAmount += cleanWaterAmount;
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
       
        Log.w("Water", "Added clean Water: "+ cleanWater+ " totalWater "+ totalWater);

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

    public void addDirtyWater(double dirtyWaterAmount){
        Log.w("Dirty","DIRTY: "+ dirtyWaterAmount);
        double totalWater = cleanWater + dirtyWater;
        double spaceLeft = (maxAmountOfWater - totalWater);
        if(dirtyWaterAmount >= 0){
            totalCollectedWaterAmount += dirtyWaterAmount;
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


        Log.w("Water", "Added dirty water: "+ dirtyWater + " totalWater "+ totalWater);
    }

    //Removes both clean and dirty water
    public void removeWater(int removedWater){
        int cleanWaterRemoved = (int)((double)removedWater*((double)getCleanWaterPercentage()/100));
        addCleanWater(-cleanWaterRemoved);
//        int dirtyWaterRemoved = (int)((double)removedWater*((double)getDirtyWaterPercentage()/100));
        int dirtyWaterRemoved = removedWater - cleanWaterRemoved;
        addDirtyWater(-dirtyWaterRemoved);
        Log.w("Water","removedWater: "+ removedWater+ " CleanWaterRemoved: "+ cleanWaterRemoved + " dirtyWaterRemoved: "+ dirtyWaterRemoved);
    }

    private void setDirtyWater(int dirtyWater){
        this.dirtyWater = dirtyWater;
    }

    public int getCleanWater() {
        return (int)cleanWater;
    }

    public int getDirtyWater() {
        return (int)dirtyWater;
    }

    public int getTotalWater(){
        return (int)cleanWater + (int)dirtyWater;
    }

    public int getDirtyWaterPercentage(){
        if(getTotalWater() > 0) {
            return (int)(((double)getDirtyWater() / (double)getTotalWater())*100);
        }else{
            return 0;
        }
    }
    public int getCleanWaterPercentage(){
        if(getTotalWater() > 0) {
            return (int)(((double)getCleanWater() / (double)getTotalWater())*100);
        }else{
            return 0;
        }
    }

    public int getTotalCollectedWaterAmount() {
        return (int)totalCollectedWaterAmount;
    }

    public int getMaxAmountOfWater() {
        return maxAmountOfWater;
    }
}
