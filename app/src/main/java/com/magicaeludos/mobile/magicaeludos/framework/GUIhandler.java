package com.magicaeludos.mobile.magicaeludos.framework;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.magicaeludos.mobile.magicaeludos.R;

/**
 * Created by Anders Lunde on 02.03.2016.
 */
public class GUIhandler {

    private MotherActivity activity;
    private Grid grid;
    private Paint paint = new Paint();

    //GUI Images
    private Bitmap img_waterBar;
    private Rect rectSrc_waterBar;
    private Rect rectDst_waterBar;
    private Bitmap img_water;
    private Rect rectSrc_water;
    private Rect rectDst_water;


    public GUIhandler (MotherActivity activity, Grid grid){
        this.activity = activity;
        this.grid = grid;

        fetchResources();
        initializeRects();
    }

    private void fetchResources(){
        img_waterBar = BitmapFactory.decodeResource(activity.getResources(), R.drawable.water_bar);
        img_water = BitmapFactory.decodeResource(activity.getResources(), R.drawable.blue_water);
    }

    private void initializeRects(){
        rectSrc_waterBar = new Rect(0,0,img_waterBar.getWidth(),img_waterBar.getHeight());
        rectDst_waterBar = new Rect((grid.getColWidth()*3) + (grid.getColWidth()/2),grid.getRowHeight()*2,grid.getScreenWidth(),grid.getRowHeight()*4);
        rectSrc_water = new Rect(0,0,img_waterBar.getWidth(),img_waterBar.getHeight());
        rectDst_water = new Rect((grid.getColWidth()*3) + (grid.getColWidth()/2),grid.getRowHeight()*2,grid.getScreenWidth(),grid.getRowHeight()*4);
    }


    public void update(){

    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(img_waterBar,rectSrc_waterBar,rectDst_waterBar,paint);
    }


}
