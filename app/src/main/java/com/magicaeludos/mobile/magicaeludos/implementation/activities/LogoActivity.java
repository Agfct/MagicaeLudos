package com.magicaeludos.mobile.magicaeludos.implementation.activities;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;

import com.magicaeludos.mobile.magicaeludos.R;
import com.magicaeludos.mobile.magicaeludos.framework.MotherActivity;

public class LogoActivity extends MotherActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);


        new CountDownTimer(2000, 1000) { // adjust the milli seconds here

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                goToMainActivity();
            }
        }.start();

    }

    public void goToMainActivity(){
        Log.w("LogoActivity", "Going to the MainMenu");
        goTo(MainActivity.class);
    }





}
