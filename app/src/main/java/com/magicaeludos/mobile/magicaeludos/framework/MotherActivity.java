/*
 * Copyright (c) 2016 Anders Lunde
 */

package com.magicaeludos.mobile.magicaeludos.framework;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
/**
 * Created by Anders on 11.01.2016.
 * The MotherActivity is meant to gather all the Activities in the application under one roof.
 * Usually used to hide UI elements and moving between Activities. Can also be used to host static classes / variables.
 */
public abstract class MotherActivity extends FragmentActivity {

    private int screenWidth;
    private int screenHeight;
//    private static Assets assets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        Log.w("MotherActivity", "onPause()");
    }

    @Override
    public void onResume() {
        super.onResume();
        hideSystemUI();
        Log.w("MotherActivity", "onResume()");
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


    // This snippet hides the system bars.
    private void hideSystemUI() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        View decorView = getWindow().getDecorView();
//        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION //Made "one click" impossible
//                | View.SYSTEM_UI_FLAG_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }


    public int getScreenWidth(){
        return screenWidth;
    }
    public int getScreenHeight(){
        return screenHeight;
    }
}
