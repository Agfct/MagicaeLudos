package com.magicaeludos.mobile.magicaeludos.implementation.activities;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

    //Captures the back button press
    @Override
    public void onBackPressed(){
        goTo(VillageActivity.class);
    }

    public void goToVillageActivity(View view){
        Log.w("VillageActivity", "Going to the Village");
        goTo(VillageActivity.class);
    }


    private void updateUpgradesData(){
        TextView valueUpgradeNr1 = (TextView) findViewById(R.id.valueBucketUpgrade);
        TextView valueUpgradeNr2 = (TextView) findViewById(R.id.valueCleaningStation);

        valueUpgradeNr1.setText(Integer.toString(village.getUpgradeNr(1)));
        valueUpgradeNr2.setText(Integer.toString(village.getUpgradeNr(2)));

        //Getting the correct bucket upgrade to align layout to
        int nrOfVillagers = village.getNrOfVillagers();
        ImageView bucketUpgrade;
        if(nrOfVillagers == 5){
            bucketUpgrade = (ImageView) findViewById(R.id.bucketUpgrade1);
        }else if (nrOfVillagers == 10){
            bucketUpgrade = (ImageView) findViewById(R.id.bucketUpgrade2);
        }
        else if (nrOfVillagers == 20){
            bucketUpgrade = (ImageView) findViewById(R.id.bucketUpgrade3);
        }else if (nrOfVillagers >= 30){
            bucketUpgrade = (ImageView) findViewById(R.id.bucketUpgrade3);
        }else{
            bucketUpgrade = (ImageView) findViewById(R.id.defaultUpgrades);

        }



        // Moving the background bar
        RelativeLayout bar = (RelativeLayout) findViewById(R.id.upgradeWave);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_TOP, bucketUpgrade.getId());
        bar.setLayoutParams(lp);
    }

}



