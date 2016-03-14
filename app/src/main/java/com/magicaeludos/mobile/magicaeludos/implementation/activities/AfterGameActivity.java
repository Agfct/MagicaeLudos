package com.magicaeludos.mobile.magicaeludos.implementation.activities;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.magicaeludos.mobile.magicaeludos.R;
import com.magicaeludos.mobile.magicaeludos.framework.MotherActivity;
public class AfterGameActivity extends MotherActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.after_game_info);
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
