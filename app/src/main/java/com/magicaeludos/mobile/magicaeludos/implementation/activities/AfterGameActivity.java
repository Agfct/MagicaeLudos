package com.magicaeludos.mobile.magicaeludos.implementation.activities;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.magicaeludos.mobile.magicaeludos.R;
import com.magicaeludos.mobile.magicaeludos.framework.MotherActivity;
public class AfterGameActivity extends MotherActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.after_game_info);


        int cleanWater = this.getIntent().getIntExtra("cleanWater", 0);
        int dirtyWater = this.getIntent().getIntExtra("dirtyWater", 0);
        double dirtyWaterMultiplier = this.getIntent().getDoubleExtra("dirtyWaterMultiplier", 0);
        int totalWater = cleanWater + (int)(dirtyWater * dirtyWaterMultiplier);
        TextView totalWaterAmount = (TextView) findViewById(R.id.intTotalWaterAmount);
        totalWaterAmount.setText(cleanWater + " + "+dirtyWater+"*"+dirtyWaterMultiplier+" = "+totalWater+ " l");
    }

    public void goToMainActivity(View view){
        Log.w("AfterGameActivity", "Going to the MainMenu");
        goTo(MainActivity.class);
    }

    //Captures the back button press
    @Override
    public void onBackPressed(){
        this.goTo(MainActivity.class);
    }

}
