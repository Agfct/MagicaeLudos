package com.magicaeludos.mobile.magicaeludos.framework;
import android.content.Context;
import android.content.SharedPreferences;

import com.magicaeludos.mobile.magicaeludos.R;

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
        //To clear all values: editor.clear(); or re-run this method to set all values to default.
        if(!sharedPref.getBoolean(activity.getString(R.string.firstTime), Boolean.FALSE)){
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean(activity.getString(R.string.firstTime), Boolean.TRUE);

            //Sets all the values that will be saved inGame
            editor.putInt(activity.getString(R.string.totalWater), 0);
            editor.putStringSet(activity.getString(R.string.upgradesAcquired),new TreeSet<String>());
            editor.putInt(activity.getString(R.string.mostWaterInOneRun),0);
            editor.putInt(activity.getString(R.string.totalAmountOfRuns),0);

        }
    }


    public void getData(){

    }



}
