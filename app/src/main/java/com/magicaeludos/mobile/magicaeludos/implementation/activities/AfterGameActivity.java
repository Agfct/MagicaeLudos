package com.magicaeludos.mobile.magicaeludos.implementation.activities;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.magicaeludos.mobile.magicaeludos.R;
import com.magicaeludos.mobile.magicaeludos.framework.MotherActivity;

import java.util.Random;

public class AfterGameActivity extends MotherActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.after_game_info);

        setWaterInfo();

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

    //Randomly selects a water info from the database to display
    private void setWaterInfo(){
        Random random = new Random();
        int factNr = random.nextInt(4);
        Log.w("AfterGameActivity", "Random Int: " + factNr);

        TextView waterInfoTxT = (TextView) findViewById(R.id.infoParagraph);
        TextView waterInfoSrc = (TextView) findViewById(R.id.infoSource);
        ImageView waterInfoImage = (ImageView) findViewById(R.id.infoImage);

        switch (factNr){
            case 0: waterInfoTxT.setText(R.string.water_info_0); waterInfoImage.setImageResource(R.drawable.img_water_info_0);
                waterInfoSrc.setText(R.string.water_info_source_0);break;
            case 1: waterInfoTxT.setText(R.string.water_info_1); waterInfoImage.setImageResource(R.drawable.img_water_info_1);
                waterInfoSrc.setText(R.string.water_info_source_1); break;
            case 2: waterInfoTxT.setText(R.string.water_info_2); waterInfoImage.setImageResource(R.drawable.img_water_info_2); break;
            case 3: waterInfoTxT.setText(R.string.water_info_3); waterInfoImage.setImageResource(R.drawable.img_water_info_3); break;
            case 4: waterInfoTxT.setText(R.string.water_info_4); waterInfoImage.setImageResource(R.drawable.img_water_info_4); break;
        }

    }


    //Captures the back button press
    @Override
    public void onBackPressed(){
        this.goTo(MainActivity.class);
    }

}
