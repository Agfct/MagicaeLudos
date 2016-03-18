package com.magicaeludos.mobile.magicaeludos.implementation;
import com.magicaeludos.mobile.magicaeludos.framework.FileIO;
import com.magicaeludos.mobile.magicaeludos.framework.MotherActivity;

import java.util.Set;
import java.util.TreeSet;
/**
 * Created by Anders on 10.03.2016.
 * The village is a static class that contains the information about progress, total amount of water and other important values.
 * The values are loaded at game start and updated during gameplay.
 */
public class Village {

    private MotherActivity activity;
    private FileIO fileIO;

    //Values
    private int totalWater;
    private int mostWaterInOneRun;
    private int totalAmountOfRuns;
    private int runsLeftToday = 0;
    private int currentDay = 0;
    private int nrOfVillagers = 5;

    //Uppgrades
    private int bucketUpgrade = 0;
    private int waterCleanerUpgrade = 0;

    public Village(MotherActivity activity) {
        this.activity = activity;
        this.fileIO = new FileIO(activity);
        loadVillageData();
    }

    public void loadVillageData(){
        fileIO.readFromStorageToVillage(this);
    }

    public void saveVillageData(){
        fileIO.saveVillageDataInStorage(this);
    }

    public void setTotalWater(int totalWater) {
        this.totalWater = totalWater;
    }
    public void addTotalWater(int addedWaterAmount){
        this.totalWater += addedWaterAmount;
    }

    public void setMostWaterInOneRun(int mostWaterInOneRun) {
        this.mostWaterInOneRun = mostWaterInOneRun;
    }

    public void setTotalAmountOfRuns(int totalAmountOfRuns) {
        this.totalAmountOfRuns = totalAmountOfRuns;
    }

    public int getTotalWater() {
        return totalWater;
    }

    public int getMostWaterInOneRun() {
        return mostWaterInOneRun;
    }

    public int getTotalAmountOfRuns() {
        return totalAmountOfRuns;
    }

    public int getNrOfVillagers() {
        return nrOfVillagers;
    }

    public int getRunsLeftToday() {
        return runsLeftToday;
    }

    public int getCurrentDay() {
        return currentDay;
    }

    public void setNrOfVillagers(int nrOfVillagers) {
        this.nrOfVillagers = nrOfVillagers;
    }

    public void setCurrentDay(int currentDay) {
        this.currentDay = currentDay;
    }

    public void setRunsLeftToday(int runsLeftToday) {
        this.runsLeftToday = runsLeftToday;
    }

    /* UPPGRADES */
    /* 1 Bigger Bucket */
    /* Water cleaner */
    public int getUpgradeNr(int nr){
        switch (nr){
            case 1: return bucketUpgrade;
            case 2: return waterCleanerUpgrade;
        }

        return -1;
    }

    public void setUpgradeNr(int nr, int value){
        if(nr == 1){
            bucketUpgrade = value;
        }else if(nr == 2){
            waterCleanerUpgrade = value;
        }
    }
}
