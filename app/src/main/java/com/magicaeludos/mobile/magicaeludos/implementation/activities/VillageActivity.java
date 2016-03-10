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


    public void goToMainActivity(View view){
        Log.w("VillageActivity", "Going to the MainMenu");
        goTo(MainActivity.class);
    }

    private void updateVillageData(){
        TextView totalWater = (TextView) findViewById(R.id.valueTotalWater);
        TextView NrOfVillagers = (TextView) findViewById(R.id.valueVillagers);
        TextView bestRun = (TextView) findViewById(R.id.valueMostWaterInOneRun);
        TextView NrOfRuns = (TextView) findViewById(R.id.valueNrOfRuns);

        totalWater.setText(Integer.toString(village.getTotalWater()));
        NrOfVillagers.setText(Integer.toString(village.getNrOfVillagers()));
        bestRun.setText(Integer.toString(village.getMostWaterInOneRun()));
        NrOfRuns.setText(Integer.toString(village.getTotalAmountOfRuns()));

    }
}



