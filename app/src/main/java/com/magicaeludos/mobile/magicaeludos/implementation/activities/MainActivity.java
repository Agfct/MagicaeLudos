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
        goTo(LevelSelectActivity.class);
    }

    public void goToVillageActivity(View view){
        goTo(VillageActivity.class);
    }

    public void goToCreditsActivity(View view){
        goTo(CreditsActivity.class);
    }



}
