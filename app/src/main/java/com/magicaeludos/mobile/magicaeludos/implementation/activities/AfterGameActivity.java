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
    private double time;
    private double delay = 2000; //A delay on 1000 = 1 sec
    private int level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.after_game_info);

        setWaterInfo();

        time = System.currentTimeMillis();
        level = this.getIntent().getIntExtra(this.getString(R.string.level), 0);

        int cleanWater = this.getIntent().getIntExtra("cleanWater", 0);
        int dirtyWater = this.getIntent().getIntExtra("dirtyWater", 0);
        double dirtyWaterMultiplier = this.getIntent().getDoubleExtra("dirtyWaterMultiplier", 0);
        int totalWater = cleanWater + (int)(dirtyWater * dirtyWaterMultiplier);
        TextView totalCleanWater = (TextView) findViewById(R.id.intCleanWaterAmount);
        TextView totalDirtyWater = (TextView) findViewById(R.id.intDirtyWaterAmount);
        TextView totalWaterAmount = (TextView) findViewById(R.id.intTotalWaterAmount);
//        totalWaterAmount.setText(cleanWater + " + " + dirtyWater + "*" + dirtyWaterMultiplier + " = "+totalWater+ " l");
        totalCleanWater.setText(cleanWater+" L");
        totalDirtyWater.setText(dirtyWater + " L * " + dirtyWaterMultiplier);
        totalWaterAmount.setText(totalWater+" L");
    }

    public void goToMainActivity(View view){
        Log.w("AfterGameActivity", "Going to the MainMenu");
        if((System.currentTimeMillis() - time) > delay)
        goTo(MainActivity.class);
    }


    public void goToGameActivity(View view){
        Log.w("LevelSelectActivity","Level: " + level);
        Log.w("LevelSelectActivity", "Going to the Game");

        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(this.getString(R.string.level), level);
        goTo(intent);
    }

    public void goToVillageActivity(View view){
        Log.w("AfterGameActivity", "Going to the Village");
        if((System.currentTimeMillis() - time) > delay)
        goTo(VillageActivity.class);
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
            case 2: waterInfoTxT.setText(R.string.water_info_2); waterInfoImage.setImageResource(R.drawable.img_water_info_2);
                waterInfoSrc.setText(R.string.water_info_source_2);break;
            case 3: waterInfoTxT.setText(R.string.water_info_3); waterInfoImage.setImageResource(R.drawable.img_water_info_3);
                waterInfoSrc.setText(R.string.water_info_source_3);break;
            case 4: waterInfoTxT.setText(R.string.water_info_4); waterInfoImage.setImageResource(R.drawable.img_water_info_4);
                waterInfoSrc.setText(R.string.water_info_source_4);break;
        }

    }


    //Captures the back button press
    @Override
    public void onBackPressed(){
        this.goTo(MainActivity.class);
    }

}
