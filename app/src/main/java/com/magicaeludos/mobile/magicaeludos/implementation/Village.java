package com.magicaeludos.mobile.magicaeludos.implementation;
import android.util.Log;

import com.magicaeludos.mobile.magicaeludos.framework.FileIO;
import com.magicaeludos.mobile.magicaeludos.framework.MotherActivity;

import java.util.ArrayList;

/**
 * Created by Anders on 10.03.2016.
 * The village is a static class that contains the information about progress, total amount of water and other important values.
 * The values are loaded at game start and updated during gameplay.
 */
public class Village {

    private MotherActivity activity;
    private FileIO fileIO;

    //Audio
    private int bgmLevel = 40;
    private int sfxLevel = 50;
    private boolean bgmSound = true;
    private boolean sfxSound = true;

    //Finals
    private final int NUMBEROFRUNSPRDAY = 5;
    private final int AMOUNTOFWATERPRVILLAGER = 20;
    private ArrayList<Integer> villagerMilestones;

    //Values
    private int totalWater;
    private int mostCollectedWaterInOneRun;
    private int mostGainedWaterInOneRun;
    private int totalAmountOfRuns;
    private int runsLeftToday = 0;
    private int currentDay = 0;
    private int nrOfVillagers = 1;

    //Uppgrades
    private int bucketUpgrade = 0;
    private int waterCleanerUpgrade = 0;

    public Village(MotherActivity activity) {
        this.activity = activity;
        this.fileIO = new FileIO(activity);
        setVillagerMilestones();
        loadVillageData();
    }

    //Defines the different villager levels (5, 10, 20, 30) that increases villager size
    private void setVillagerMilestones(){
        villagerMilestones = new ArrayList<>();
        villagerMilestones.add(5);
        villagerMilestones.add(10);
        villagerMilestones.add(20);
        villagerMilestones.add(30);
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

    public void setMostCollectedWaterInOneRun(int mostCollectedWaterInOneRun) {
        this.mostCollectedWaterInOneRun = mostCollectedWaterInOneRun;
    }

    public void setMostGainedWaterInOneRun(int mostGainedWaterInOneRun) {
        this.mostGainedWaterInOneRun = mostGainedWaterInOneRun;
    }

    public void setTotalAmountOfRuns(int totalAmountOfRuns) {
        this.totalAmountOfRuns = totalAmountOfRuns;
    }

    public int getTotalWater() {
        return totalWater;
    }

    public int getMostCollectedWaterInOneRun() {
        return mostCollectedWaterInOneRun;
    }

    public int getMostGainedWaterInOneRun() {
        return mostGainedWaterInOneRun;
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
        checkUpgrades();
    }

    public void setCurrentDay(int currentDay) {
        this.currentDay = currentDay;
    }

    public void setRunsLeftToday(int runsLeftToday) {
        this.runsLeftToday = runsLeftToday;
    }

    public int getNUMBEROFRUNSPRDAY() {
        return NUMBEROFRUNSPRDAY;
    }

    public int getAMOUNTOFWATERPRVILLAGER() {
        return AMOUNTOFWATERPRVILLAGER;
    }

    public ArrayList<Integer> getVillagerMilestones() {
        return villagerMilestones;
    }

    //Upgrades based on the amout of villagers
    private void checkUpgrades(){
        Log.w("Village","Before UpppgradeNr. 1: " + bucketUpgrade + " 2: "+waterCleanerUpgrade);
        if(nrOfVillagers >= 5){
            if(bucketUpgrade == 0){
                bucketUpgrade = 1;
            }
        }
        if(nrOfVillagers >= 10){
            if(bucketUpgrade == 1){
                bucketUpgrade = 2;
            }
            if(waterCleanerUpgrade == 0){
                waterCleanerUpgrade = 1;
            }
        }
        if(nrOfVillagers >= 20){
            if(bucketUpgrade == 2){
                bucketUpgrade = 3;
            }
            if(waterCleanerUpgrade == 1){
                waterCleanerUpgrade = 2;
            }
        }
        Log.w("Village","After UpppgradeNr. 1: " + bucketUpgrade + " 2: "+waterCleanerUpgrade);
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

    //UPGRADES:
    public int getBucketSize(){
        if (bucketUpgrade == 0){
            return 100;
        }else if (bucketUpgrade == 1){
            return 150;
        }else if (bucketUpgrade == 2){
            return 200;
        }else if (bucketUpgrade == 3){
            return 400;
        }
        return 0;
    }

    public double getDirtyWaterMultiplier(){
        if (waterCleanerUpgrade == 0){
            return 0.25;
        }else if (waterCleanerUpgrade == 1){
            return 0.5;
        }else if (waterCleanerUpgrade == 2){
            return 0.75;
        }else if (waterCleanerUpgrade == 3){
            return 1;
        }
        return 0;
    }

    public int getBgmLevel() {
        return bgmLevel;
    }

    public int getSfxLevel() {
        return sfxLevel;
    }
}
