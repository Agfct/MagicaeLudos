package com.magicaeludos.mobile.magicaeludos.framework;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import com.magicaeludos.mobile.magicaeludos.implementation.GameContent;
/**
 * Created by Anders on 24.02.2016.
 * This class cointains a grid that defines the lanes and other size properties of the GameContent
 */
public class Grid {

    //Screen sizes based on a set amount of COLS
    //ScreenCol is the size of one Column, and screen row is the size of oneRow
    private int screenWidth;
    private int screenHeight;
    private int ROWS;
    private final int COLS = 4;
    private int colWidth;
    private int rowHeight;

    //Rect that covers the entire screen
    private Rect screenBorders;

    //Fixed points for spawning in lanes
    private Point lane1;
    private Point lane2;
    private Point lane3;

    //Activity
    private MotherActivity activity;


    public Grid (GameContent gameContent){
        this.activity = gameContent.getActivity();
        setScreenValues();
        defineLanes();
    }

    private void setScreenValues(){
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
        ROWS = (COLS * screenHeight) / screenWidth;

        screenBorders = new Rect(0, 0, screenWidth, screenHeight);
        //Rounds up the values for the colWidth and Row to ensure it covers the screen correctly.
        colWidth = (int) Math.ceil((double) screenWidth / COLS);
        rowHeight = (int) Math.ceil((double) screenHeight / ROWS);
    }

    private void defineLanes(){
        //rowHeight*-2
        lane1 = new Point(colWidth /2,0);
        lane2 = new Point(colWidth +(colWidth /2),0);
        lane3 = new Point((colWidth *2)+ colWidth /2,0);
        Log.w("Grid","Lane1: "+ lane1+ " Lane2: "+ lane2 + " Lane3: "+ lane3);
    }


    //Test
    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        canvas.drawText("COLS: " + COLS + " ROWS: " + ROWS,50,50,paint);
        canvas.drawText("colWidth: " + colWidth + " rowHeight: " + rowHeight, 50, 70, paint);
        canvas.drawText("screenWidth: " + screenWidth + " screenHeight: " + screenHeight, 50, 150, paint);
    }

    /**
     * Takes inn a laneNumber 1-3 and returns a Point containin the start position for that lane.
     * The default position is the top left corner of the lane (top y value is negative so that objects spawns outside screen)
     * @param laneNr
     * @return
     */
    public Point getLane(int laneNr) {

        switch (laneNr){
            case 1:return lane1;
            case 2:return lane2;
            case 3:return lane3;
        }
        //If a laneNr is not between 1-3 it returns the point of lane 1
        return lane1;

    }

    public int getColWidth() {
        return colWidth;
    }

    public int getRowHeight() {
        return rowHeight;
    }

    public Rect getScreenBorders() {
        return screenBorders;
    }
}
