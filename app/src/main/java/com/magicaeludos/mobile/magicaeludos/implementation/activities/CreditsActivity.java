package com.magicaeludos.mobile.magicaeludos.implementation.activities;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.magicaeludos.mobile.magicaeludos.R;
import com.magicaeludos.mobile.magicaeludos.framework.MotherActivity;
public class CreditsActivity extends MotherActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
    }


    public void goToMainActivity(View view){
        goTo(MainActivity.class);
    }



}
