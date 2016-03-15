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

        //If this is the first time you start the app it will initialize all variables
        if(!sharedPref.getBoolean(activity.getString(R.string.firstTime), Boolean.FALSE)){
            Log.w("FileIO", "NO PROFILE, CREATING NEW PROFILE DATA");
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean(activity.getString(R.string.firstTime), Boolean.TRUE);

            //Sets all the values that will be saved inGame
            editor.putInt(activity.getString(R.string.totalWater), 0);
            editor.putStringSet(activity.getString(R.string.upgradesAcquired), new TreeSet<String>());
            editor.putInt(activity.getString(R.string.mostWaterInOneRun), 0);
            editor.putInt(activity.getString(R.string.totalAmountOfRuns),0);

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
        village.setMostWaterInOneRun(sharedPref.getInt(activity.getString(R.string.mostWaterInOneRun),0));
        village.setTotalAmountOfRuns(sharedPref.getInt(activity.getString(R.string.totalAmountOfRuns),0));
        village.setUpgradesAcquired(sharedPref.getStringSet(activity.getString(R.string.upgradesAcquired),new TreeSet<String>()));

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
        editor.putStringSet(activity.getString(R.string.upgradesAcquired), new TreeSet<String>());
        editor.putInt(activity.getString(R.string.mostWaterInOneRun), village.getMostWaterInOneRun());
        editor.putInt(activity.getString(R.string.totalAmountOfRuns),village.getTotalAmountOfRuns());

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
        editor.putStringSet(activity.getString(R.string.upgradesAcquired),new TreeSet<String>());
        editor.putInt(activity.getString(R.string.mostWaterInOneRun),0);
        editor.putInt(activity.getString(R.string.totalAmountOfRuns),0);

        editor.commit();
    }



}
