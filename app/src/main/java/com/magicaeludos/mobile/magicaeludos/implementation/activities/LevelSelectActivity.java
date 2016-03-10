package com.magicaeludos.mobile.magicaeludos.implementation.activities;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.magicaeludos.mobile.magicaeludos.R;
import com.magicaeludos.mobile.magicaeludos.framework.MotherActivity;
public class LevelSelectActivity extends MotherActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select);
    }


    public void goToGameActivity(View view){
        String level = (String)view.getTag();
        Log.w("LevelSelectActivity","TagNr: " + level);
        Log.w("LevelSelectActivity", "Going to the Game");
        getVillage().setTotalWater(getVillage().getTotalWater()+1);//TODO: REMOVE THIS LINE
        getVillage().saveVillageData();//TODO: REMOVE THIS LINE
        goTo(GameActivity.class);
    }

    public void goToMainActivity(View view){
        Log.w("LevelSelectActivity", "Going to the Mainmenu");
        goTo(MainActivity.class);
    }



}
