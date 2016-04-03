package com.magicaeludos.mobile.magicaeludos.framework;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.magicaeludos.mobile.magicaeludos.R;
import com.magicaeludos.mobile.magicaeludos.implementation.activities.GameContent;
/**
 * Created by Anders Lunde on 02.03.2016.
 */
public class GUIhandler {

    private MotherActivity activity;
    private GameContent content;
    private Grid grid;
    private Paint paint = new Paint();

    //Stored values
    private int barY2;
    private int barY;
    private int barX;

    //Objects for UI
    Water water;

    //GUI Images
    private Bitmap img_waterBar;
    private Rect rectSrc_waterBar;
    private Rect rectDst_waterBar;
    private Bitmap img_water;
    private Rect rectSrc_water;
    private Rect rectDst_water;


    public GUIhandler (GameContent content, Grid grid){
        this.content = content;
        this.activity = content.getActivity();
        this.grid = grid;
        this.water = content.water;

        //Setting some values
        this.barY2 = grid.getRowHeight()*4;
        this.barY = grid.getRowHeight()*2;
        this.barX = (grid.getColWidth()*3) + (grid.getColWidth()/2);

        fetchResources();
        initializeRects();
        calculateWaterAmount();
    }

    private void fetchResources(){
        img_waterBar = BitmapFactory.decodeResource(activity.getResources(), R.drawable.water_bar);
        img_water = BitmapFactory.decodeResource(activity.getResources(), R.drawable.blue_water);
    }

    private void initializeRects(){
        rectSrc_waterBar = new Rect(0,0,img_waterBar.getWidth(),img_waterBar.getHeight());
        rectDst_waterBar = new Rect(barX,barY,grid.getScreenWidth(), barY2);
        rectSrc_water = new Rect(0,0,img_waterBar.getWidth(),img_waterBar.getHeight());
        rectDst_water = new Rect((grid.getColWidth()*3) + (grid.getColWidth()/2),grid.getRowHeight()*2,grid.getScreenWidth(), barY2);
    }


    public void update(){
        calculateWaterAmount();
    }

    //Changes the size of the rectangle depending on the amount of water
    private void calculateWaterAmount(){
        double numberOfBars = 0;
        double barHeight = barY2 - barY;
        double maxWaterAmount = water.getMaxAmountOfWater();
        try {
            numberOfBars = (barHeight / maxWaterAmount);
        }catch (ArithmeticException e){
            Log.w("GUIhandler","DIVISION BY ZERO: Max Amount of water is 0");
        }
        //This line chooses the height of the rect, the Math.min is to prevent overflow.
        int height = Math.min((int)(numberOfBars * water.getCleanWater()),(int)(numberOfBars*maxWaterAmount));
        rectDst_water = new Rect(barX, barY2 - height,grid.getScreenWidth(), barY2);

    }
    public void draw(Canvas canvas){
        canvas.drawBitmap(img_water,rectSrc_water,rectDst_water,paint);
        canvas.drawBitmap(img_waterBar,rectSrc_waterBar,rectDst_waterBar,paint);
    }


}
