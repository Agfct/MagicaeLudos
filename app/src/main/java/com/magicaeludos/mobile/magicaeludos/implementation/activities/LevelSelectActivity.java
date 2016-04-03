package com.magicaeludos.mobile.magicaeludos.implementation.activities;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;

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
        Log.w("LevelSelectActivity","Level: " + level);
        Log.w("LevelSelectActivity", "Going to the Game");

        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(this.getString(R.string.level), Integer.parseInt(level));
        goTo(intent);
    }

    public void goToMainActivity(View view){
        Log.w("LevelSelectActivity", "Going to the Mainmenu");
        goTo(MainActivity.class);
    }

    //Captures the back button press
    @Override
    public void onBackPressed(){
        goTo(MainActivity.class);
    }



}
