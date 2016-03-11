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
    private TreeSet<String> upgradesAcquired;
    private int nrOfVillagers = 5; //Calculated value ?

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

    public void setMostWaterInOneRun(int mostWaterInOneRun) {
        this.mostWaterInOneRun = mostWaterInOneRun;
    }

    public void setTotalAmountOfRuns(int totalAmountOfRuns) {
        this.totalAmountOfRuns = totalAmountOfRuns;
    }

    public void setUpgradesAcquired(Set<String> setUpgradesAcquired) {
        TreeSet<String> upgradesAcquired = new TreeSet<>(setUpgradesAcquired);
        this.upgradesAcquired = upgradesAcquired;
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

    public TreeSet<String> getUpgradesAcquired() {
        return upgradesAcquired;
    }

    public int getNrOfVillagers() {
        return nrOfVillagers;
    }
}
