package com.magicaeludos.mobile.magicaeludos.implementation.activities;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ViewFlipper;

import com.magicaeludos.mobile.magicaeludos.R;
import com.magicaeludos.mobile.magicaeludos.framework.Layout;
import com.magicaeludos.mobile.magicaeludos.framework.MotherActivity;
/**
 * Created by Anders on 10.02.2016.
 */
public class GameActivity extends MotherActivity {
    private GameContent content;
    private Layout layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Finds the layout defined in the XML activity_game
        layout = (Layout)findViewById(R.id.layout);

        //Creates a GameContent class containing the game code and classes
        content = new GameContent(this, layout);

        //Creates a Layout class that updates using the AndroidThread
        //It also adds the content to the layout (making the layout update and draw the content class)
        layout.initialize(this, content);

        //Puts the correct gameInfo based on level selected
        setCorrectGameInfo();

    }

    //When the app is closed down this is ran and it pauses the layout thread
    @Override
    public void onPause() {
        super.onPause();
        layout.pauseAll();
        content.pauseBackgroundAudio();
    }

    //When the app is opened this is ran and it starts / restarts the layout thread
    @Override
    public void onResume() {
        super.onResume();
        layout.resumeAll();
        content.startBackgroundAudio();
    }

    //Captures the back button press
    @Override
    public void onBackPressed(){
        this.goTo(MainActivity.class);
    }

    private void setCorrectGameInfo(){
        int level = getIntent().getIntExtra(getString(R.string.level), 0);
        ViewFlipper viewFlipper = (ViewFlipper) findViewById(R.id.vf);
        viewFlipper.setDisplayedChild(level-1);
    }

    public void startGame(View view){
        ViewFlipper viewFlipper = (ViewFlipper) findViewById(R.id.vf);
        viewFlipper.setVisibility(View.GONE);
        content.startGame();
    }

    public void goToAfterGame(View view){
        goTo(MainActivity.class);
    }
}
