package com.magicaeludos.mobile.magicaeludos.framework;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.magicaeludos.mobile.magicaeludos.R;
import com.magicaeludos.mobile.magicaeludos.implementation.Village;

import java.util.Set;
import java.util.TreeSet;
/**
 * Created by Anders on 04.03.2016.
 * Used to saved data to mobile storage
 *
 * Saves:
 * Total Water amount
 * Amount of villages ? Calculated from totalWater ?
 * Upgrades acquired
 * Most water collected in one run
 * Totalt amount of runs?
 */
public class FileIO {

    MotherActivity activity;


    public FileIO(MotherActivity activity){
        this.activity = activity;

        SharedPreferences sharedPref = activity.getSharedPreferences(activity.getString(R.string.profile_preferences), Context.MODE_PRIVATE);
//        clearSavedData(); //TODO: REMOVE
        //If this is the first time you start the app it will initialize all variables
        if(!sharedPref.getBoolean(activity.getString(R.string.firstTime), Boolean.FALSE)){
            Log.w("FileIO", "NO PROFILE, CREATING NEW PROFILE DATA");
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean(activity.getString(R.string.firstTime), Boolean.TRUE);

            //Sets all the values that will be saved inGame
            editor.putInt(activity.getString(R.string.totalWater), 0);
            editor.putInt(activity.getString(R.string.upgradeNr1), 0);
            editor.putInt(activity.getString(R.string.upgradeNr2), 0);
            editor.putInt(activity.getString(R.string.mostWaterInOneRun), 0);
            editor.putInt(activity.getString(R.string.totalAmountOfRuns), 0);
            editor.putInt(activity.getString(R.string.villages),1);
            editor.putInt(activity.getString(R.string.currentDay), 0);
            editor.putInt(activity.getString(R.string.runsLeftToday),5);

            editor.commit();

        }
    }

    /**
     * Reads the sharedPreferences values from storage to village.
     * @param village
     */
    public void readFromStorageToVillage(Village village){
        Log.w("FileIO", "UpdateVillage");
        SharedPreferences sharedPref = activity.getSharedPreferences(activity.getString(R.string.profile_preferences), Context.MODE_PRIVATE);

        village.setTotalWater(sharedPref.getInt(activity.getString(R.string.totalWater),0));
        village.setMostWaterInOneRun(sharedPref.getInt(activity.getString(R.string.mostWaterInOneRun), 0));
        village.setTotalAmountOfRuns(sharedPref.getInt(activity.getString(R.string.totalAmountOfRuns), 0));
        village.setUpgradeNr(1,sharedPref.getInt(activity.getString(R.string.upgradeNr1), 0));
        village.setUpgradeNr(2,sharedPref.getInt(activity.getString(R.string.upgradeNr2), 0));
        village.setNrOfVillagers(sharedPref.getInt(activity.getString(R.string.villages), 0));
        village.setCurrentDay(sharedPref.getInt(activity.getString(R.string.currentDay), 0));
        village.setRunsLeftToday(sharedPref.getInt(activity.getString(R.string.runsLeftToday),0));

    }

    /**
     * Saves the current village values to mobile storage
     * @param village
     */
    public void saveVillageDataInStorage(Village village){
        Log.w("FileIO", "SAVING CURRENT VILLAGE DATA");
        SharedPreferences sharedPref = activity.getSharedPreferences(activity.getString(R.string.profile_preferences), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putInt(activity.getString(R.string.totalWater), village.getTotalWater());
        editor.putInt(activity.getString(R.string.upgradeNr1), village.getUpgradeNr(1));
        editor.putInt(activity.getString(R.string.upgradeNr2), village.getUpgradeNr(2));
        editor.putInt(activity.getString(R.string.mostWaterInOneRun), village.getMostWaterInOneRun());
        editor.putInt(activity.getString(R.string.totalAmountOfRuns),village.getTotalAmountOfRuns());
        editor.putInt(activity.getString(R.string.villages), village.getNrOfVillagers());
        editor.putInt(activity.getString(R.string.currentDay), village.getCurrentDay());
        editor.putInt(activity.getString(R.string.runsLeftToday),village.getRunsLeftToday());


        editor.commit();
    }

    /**
     * Clears the mobile save data, putting all values back to default (Not setting "firstTime" boolean to false)
     */
    public void clearSavedData(){
        Log.w("FileIO", "CLEARING SAVED DATA");
        SharedPreferences sharedPref = activity.getSharedPreferences(activity.getString(R.string.profile_preferences), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        //Resets all the values to default
        editor.putInt(activity.getString(R.string.totalWater), 0);
        editor.putInt(activity.getString(R.string.upgradeNr1), 0);
        editor.putInt(activity.getString(R.string.upgradeNr2), 0);
        editor.putInt(activity.getString(R.string.mostWaterInOneRun),0);
        editor.putInt(activity.getString(R.string.totalAmountOfRuns),0);
        editor.putInt(activity.getString(R.string.villages), 1);
        editor.putInt(activity.getString(R.string.currentDay), 0);
        editor.putInt(activity.getString(R.string.runsLeftToday),5);

        editor.commit();
    }



}
