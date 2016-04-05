package com.magicaeludos.mobile.magicaeludos.implementation.activities;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.magicaeludos.mobile.magicaeludos.R;
import com.magicaeludos.mobile.magicaeludos.framework.MotherActivity;
import com.magicaeludos.mobile.magicaeludos.implementation.Village;
public class VillageActivity extends MotherActivity {


    private Village village;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_village);

        village = this.getVillage();
        updateVillageData();
    }

    //Captures the back button press
    @Override
    public void onBackPressed(){
        this.goTo(MainActivity.class);
    }

    public void goToMainActivity(View view){
        Log.w("VillageActivity", "Going to the MainMenu");
        goTo(MainActivity.class);
    }

    public void goToUpgradesActivity(View view){
        Log.w("VillageActivity", "Going to the Upgrade Activity");
        goTo(UpgradesActivity.class);
    }

    private void updateVillageData(){
        TextView totalWater = (TextView) findViewById(R.id.valueTotalWater);
        TextView nrOfVillagers = (TextView) findViewById(R.id.valueVillagers);
        TextView bestRun = (TextView) findViewById(R.id.valueMostWaterInOneRun);
        TextView nrOfRuns = (TextView) findViewById(R.id.valueNrOfRuns);
        TextView currentDay = (TextView) findViewById(R.id.valueCurrentDay);
        TextView runsLeftToday = (TextView) findViewById(R.id.valueRunsLeftToday);

        totalWater.setText(Integer.toString(village.getTotalWater()));
        nrOfVillagers.setText(Integer.toString(village.getNrOfVillagers()));
        bestRun.setText(Integer.toString(village.getMostWaterInOneRun()));
        nrOfRuns.setText(Integer.toString(village.getTotalAmountOfRuns()));
        currentDay.setText(Integer.toString(village.getCurrentDay()));
        runsLeftToday.setText(Integer.toString(village.getRunsLeftToday()));

    }
}



