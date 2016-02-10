/*
 * Copyright (c) 2016 Anders Lunde
 */

package com.magicaeludos.mobile.magicaeludos.framework;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
/**
 * Created by Anders on 11.01.2016.
 * Extends SurfaceView and acts as a parent class for all the surfaceViews in the application.
 * All java Activities in the program uses a SurfaceView for display.
 * The layout class contains a surfaceHolder and works together with the AndroidThread class
 * starting it when the surface is ready to be drawn upon.
 */
public class Layout extends SurfaceView implements SurfaceHolder.Callback{

    private MotherActivity mainActivity;
    private SurfaceHolder holder;
    private boolean surfaceAlive = false;
    private boolean paused = false;
    private AndroidThread gameThread;
    private Content content;

    //Constructor ment to fit XML view standard
    public Layout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void initialize(MotherActivity context, Content content){
        this.mainActivity = context;
        this.holder = getHolder();
        gameThread = new AndroidThread(this);
        this.content = content;
        holder.addCallback(this);
    }


    //Update is sent straight to the content class
    public void update(){
        content.update();
    }


    //Draw is sent straight to the content class after beeing drawn by super class
    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        content.draw(canvas);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //Set loops, drawing and such
        //Starts thread if the game is not paused
        if(!isPaused()) {
            setSurfaceAlive(true);
            gameThread.setRunning(true);
            gameThread.start();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //Close loop threads and so on
        setSurfaceAlive(false);
        pauseAll();
    }

    public void pauseAll() {
        setPaused(true);
        gameThread.setRunning(false);
        while(true){
            try {
                gameThread.join();
                break;
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public void resumeAll() {
        setPaused(false);
        this.gameThread = new AndroidThread(this);
        if(isSurfaceAlive()){
            gameThread.setRunning(true);
            gameThread.start();
        }
    }

    public boolean isSurfaceAlive() {
        return surfaceAlive;
    }
    public void setSurfaceAlive(boolean surfaceAlive) {
        this.surfaceAlive = surfaceAlive;
    }
    public boolean isPaused() {
        return paused;
    }
    public void setPaused(boolean paused) {
        this.paused = paused;
    }
}