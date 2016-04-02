/*
 * Copyright (c) 2016 Anders Lunde
 */

package com.magicaeludos.mobile.magicaeludos.framework;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
/**
 * Created by Anders on 11.01.2016.
 * Loops the drawing and checking processes in the Match lobby and game itself
 */
public class AndroidThread extends Thread {


    private SurfaceHolder viewHolder;
    private Layout currentLayout;
    private boolean isRunning = false;
    private final int MAX_FPS = 30;
    private final int MAX_FRAME_SKIPS = 0;
    private final int FRAME_PERIOD = 1000 / MAX_FPS;

    public AndroidThread(Layout layout){
        this.currentLayout = layout;
        this.viewHolder = layout.getHolder();
    }

    @Override
    public void run(){
        long beginTime; //Time when the cycle begins
        long timeDiff;  // Time it took for the cycle to execute
        int sleepTime; // ms to sleep (<0 if we're behind)
        int framesSkipped; //Number of frames being skipped

        sleepTime = 0;

        while (isRunning){
            Canvas canvas = null;

            try {
                canvas = viewHolder.lockCanvas();
                synchronized (currentLayout.getHolder()) {

                    //FPS
                    beginTime = System.currentTimeMillis();
                    framesSkipped = 0;


                    //Updates the status
                    currentLayout.update();
                    //Draws the scene
                    currentLayout.draw(canvas);

                    //Calculates cycle time
                    timeDiff = System.currentTimeMillis() - beginTime;
                    //Calculates sleep time
                    sleepTime = (int)(FRAME_PERIOD - timeDiff);

                    if(sleepTime > 0){
                        //If sleepTime > 0 then no problem
                        try{
                            // Send the thread to sleep for a short period
                            //Good for battery
                            Thread.sleep(sleepTime);
                        }catch (InterruptedException e){}
                    }

                    while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS){
                        //catching up
                        //updates without rendering
                        this.currentLayout.update();
                        //add frame period to check if in next frame
                        sleepTime += FRAME_PERIOD;
                        framesSkipped++;
                    }
                    /*//Print out FPS
                    currentTime += System.currentTimeMillis()-beginTime;
                    timesRun++;
                    if(currentTime >= 1000) {
                        Log.w("AndroidThread", "FPS: " + timesRun);
                        currentTime = 0;
                        timesRun = 0;
                    }
                    */
                }

            }finally{
                if (canvas != null) {
                    viewHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

}
