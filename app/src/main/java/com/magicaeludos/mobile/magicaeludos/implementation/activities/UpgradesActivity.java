package com.magicaeludos.mobile.magicaeludos.implementation.activities;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.magicaeludos.mobile.magicaeludos.R;
import com.magicaeludos.mobile.magicaeludos.framework.MotherActivity;
import com.magicaeludos.mobile.magicaeludos.implementation.Village;

public class UpgradesActivity extends MotherActivity {


    private Village village;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrades);

        village = this.getVillage();
        updateUpgradesData();
    }


    public void goToVillageActivity(View view){
        Log.w("VillageActivity", "Going to the Village");
        goTo(VillageActivity.class);
    }


    private void updateUpgradesData(){
        TextView upgradeNr1 = (TextView) findViewById(R.id.valueBucketUpgrade);
        TextView upgradeNr2 = (TextView) findViewById(R.id.valueCleaningStation);

        upgradeNr1.setText(Integer.toString(village.getUpgradeNr(1)));
        upgradeNr2.setText(Integer.toString(village.getUpgradeNr(2)));

    }
}



