package com.magicaeludos.mobile.magicaeludos.implementation.activities;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.magicaeludos.mobile.magicaeludos.R;
import com.magicaeludos.mobile.magicaeludos.framework.MotherActivity;
public class MainActivity extends MotherActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void goToGameActivity(View view){
        Log.w("MainActivity", "Going to the Level select");
        goTo(LevelSelectActivity.class);
    }

    public void goToVillageActivity(View view){
        Log.w("MainActivity", "Going to the Village");
        goTo(VillageActivity.class);
    }



}
