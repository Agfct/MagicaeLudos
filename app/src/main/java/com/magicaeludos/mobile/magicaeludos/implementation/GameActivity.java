package com.magicaeludos.mobile.magicaeludos.implementation;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.magicaeludos.mobile.magicaeludos.R;
import com.magicaeludos.mobile.magicaeludos.framework.Content;
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

        //TESTING: This enables you to set background in XML and make surfaceView transparent
        //If you do not have this code then surfaceview is black and nothing can be shown behind it in the XML code.
//        layout.setZOrderOnTop(true);
//        layout.getHolder().setFormat(PixelFormat.TRANSPARENT);
    }

    //When the app is closed down this is ran and it pauses the layout thread
    @Override
    public void onPause() {
        super.onPause();
        layout.pauseAll();
    }

    //When the app is opened this is ran and it starts / restarts the layout thread
    @Override
    public void onResume() {
        super.onResume();
        layout.resumeAll();
    }

    //Captures the back button press
    @Override
    public void onBackPressed(){
        this.goTo(MainActivity.class);
    }



    //Test:
//    activity.runOnUiThread(new Runnable() {
//        @Override
//        public void run() {
//            ((GameActivity) activity).runAfterGameInfo();
//        }
//    });
    public void runAfterGameInfo(){
        //Finds the layout defined in the XML activity_game
        View afterGameInfo = findViewById(R.id.afterInfo);
        afterGameInfo.setVisibility(View.VISIBLE);
    }

    public void hideAfterGameInfo(){
        //Finds the layout defined in the XML activity_game
        View afterGameInfo = findViewById(R.id.afterInfo);
        afterGameInfo.setVisibility(View.GONE);
    }
}
