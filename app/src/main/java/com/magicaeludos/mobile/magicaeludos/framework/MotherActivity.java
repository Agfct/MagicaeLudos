/*
 * Copyright (c) 2016 Anders Lunde
 */

package com.magicaeludos.mobile.magicaeludos.framework;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.WindowManager;

import com.magicaeludos.mobile.magicaeludos.implementation.Village;
/**
 * Created by Anders on 11.01.2016.
 * The MotherActivity is meant to gather all the Activities in the application under one roof.
 * Usually used to hide UI elements and moving between Activities. Can also be used to host static classes / variables.
 */
public abstract class MotherActivity extends FragmentActivity {

    private int screenWidth;
    private int screenHeight;
    private static Village village;
    private static boolean developerModeOn = false;
//    private static Assets assets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(village == null){
            village = new Village(this);
        }
//        assets = new Assets(this);

        //Gets the screen size
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        hideSystemUI();
    }

    /**Used for all transitions between activities.
     * Prevents animation between activities and closes the activity after the transition is done.
     * @param javaClass
     */
    public void goTo(Class javaClass){
        Intent intent = new Intent(this, javaClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish(); //Ends the previous activity
        overridePendingTransition(0, 0);
    }

    /**Used for some transitions between activities.
     * Prevents animation between activities and closes the activity after the transition is done.
     * @param intent
     */
    public void goTo(Intent intent){
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish(); //Ends the previous activity
        overridePendingTransition(0, 0);
    }

    // This snippet hides the system bars.
    private void hideSystemUI() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }


    public int getScreenWidth(){
        return screenWidth;
    }
    public int getScreenHeight(){
        return screenHeight;
    }

    public static Village getVillage() {
        return village;
    }

    public boolean isDeveloperModeOn() {
        return developerModeOn;
    }

    public void setDeveloperModeOn(boolean developerModeOn) {
        this.developerModeOn = developerModeOn;
    }
}
