package com.magicaeludos.mobile.magicaeludos.framework;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
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
    private Paint waterPaint = new Paint();

    //Stored values
    private int barY2;
    private int barY;
    private int barX;
    private int waterBarY2;
    private int waterBarHeight;
    private int barWaterBottom = 67;
    private int barWaterTop = 45;
    private int pBarY2;
    private int pBarY;
    private int pBarX;
    private int pBarX2;
    private int barHeight;
    private int pBarVillageHeight = 30;
    private int plBarY2;
    private int plBarY;
    private int plYRatio;
    private int plBarX;
    private int plBarX2;

    //Water color
    int red = 134;
    int green = 194;
    int blue = 234;

    //Objects for UI
    Water water;

    //GUI water bar
    private Bitmap img_waterBar;
    private Bitmap img_waterBar_back;
    private Rect rectSrc_waterBar;
    private Rect rectDst_waterBar;
    private Bitmap img_water;
    private Rect rectSrc_water;
    private Rect rectDst_water;

    //GUI Progress bar
    private Bitmap img_progressBar;
    private Bitmap img_player;
    private Rect rectSrc_progressBar;
    private Rect rectDst_progressBar;
    private Rect rectSrc_player;
    private Rect rectDst_player;



    public GUIhandler (GameContent content, Grid grid){
        this.content = content;
        this.activity = content.getActivity();
        this.grid = grid;
        this.water = content.water;

        //Setting some values for water bar
        this.barY2 = grid.getRowHeight()*4;
        this.barY = grid.getRowHeight()*2;
        this.barX = (grid.getColWidth()*3) + (grid.getColWidth()/2);
        this.barWaterBottom = (int)((double)barWaterBottom / (432/(double)(barY2 - barY)));
        this.barWaterTop = (int)((double)barWaterTop / (432/(double)(barY2 - barY)));
        this.waterBarHeight = (barY2 - barY) - (barWaterTop+barWaterBottom);
        this.waterBarY2= barY2-barWaterBottom;


        //Setting some values for progress bar
        this.pBarY2 = grid.getRowHeight()*4;
        this.pBarY = grid.getRowHeight()*2;
        this.barHeight = pBarY2 - plBarY;
        this.pBarX = 0;
        this.pBarX2 = grid.getColWidth()/2;
        //Given that default village icon height is pBarVillageHeight and largest image size is 144
        this.pBarVillageHeight = (int)((double)pBarVillageHeight / (144.0/(double)barHeight));

        //Setting some values for player icon
        this.plYRatio = grid.getRowHeight()*4 - ((grid.getRowHeight()*3) + grid.getInnerHeight());
        this.plBarY = grid.getRowHeight()*4 - plYRatio;
        this.plBarY2 = grid.getRowHeight()*4;
        this.plBarX = (grid.getColWidth()/8);
        this.plBarX2 = (grid.getColWidth()/8)*3;

        fetchResources();
        initializeRects();
        calculateWaterAmount();
    }

    private void fetchResources(){
        img_waterBar = BitmapFactory.decodeResource(activity.getResources(), R.drawable.water_bar);
        img_waterBar_back = BitmapFactory.decodeResource(activity.getResources(), R.drawable.water_bar);
        img_water = BitmapFactory.decodeResource(activity.getResources(), R.drawable.blue_water);

        img_progressBar = BitmapFactory.decodeResource(activity.getResources(), R.drawable.village_tracking);
        img_player = BitmapFactory.decodeResource(activity.getResources(), R.drawable.avatar);
    }

    private void initializeRects(){
        rectSrc_waterBar = new Rect(0,0,img_waterBar.getWidth(),img_waterBar.getHeight());
        rectDst_waterBar = new Rect(barX,barY,grid.getScreenWidth(), barY2);
        rectSrc_water = new Rect(0,0,img_water.getWidth(),img_water.getHeight());
        rectDst_water = new Rect((grid.getColWidth()*3) + (grid.getColWidth()/2),grid.getRowHeight()*2,grid.getScreenWidth(), waterBarY2);
        rectSrc_progressBar = new Rect(0,0,img_progressBar.getWidth(),img_progressBar.getHeight());
        rectDst_progressBar = new Rect(pBarX,pBarY,pBarX2, pBarY2);
        rectSrc_player = new Rect(0,0,img_player.getWidth(),img_player.getHeight());
        rectDst_player = new Rect(plBarX,plBarY,plBarX2, plBarY2);
    }


    public void update(){
        calculateWaterAmount();
        calculateProgress();
    }

    //Changes the size of the rectangle depending on the amount of water
    private void calculateWaterAmount(){
        double numberOfBars = 0;
        double maxWaterAmount = water.getMaxAmountOfWater();
        try {
            numberOfBars = (waterBarHeight / maxWaterAmount);
        }catch (ArithmeticException e){
            Log.w("GUIhandler","DIVISION BY ZERO: Max Amount of water is 0");
        }
        //This line chooses the height of the rect, the Math.min is to prevent overflow.
        int height = Math.min((int) (numberOfBars * water.getTotalWater()), (int) (numberOfBars * maxWaterAmount));
        rectDst_water = new Rect(barX, waterBarY2 - height,grid.getScreenWidth(), waterBarY2);

        //Set dirty amount of the image
        int dirtyPercentage = water.getDirtyWaterPercentage();
        int tmp_red;
        int tmp_green;
        int tmp_blue;
        int reduction = 0;
        if(dirtyPercentage > 0) {
            reduction = (dirtyPercentage / 10);
        }
        if(reduction > 0){
            tmp_red = red/reduction;
            tmp_green = green/reduction;
            tmp_blue = blue/reduction;
        }else{
            tmp_red = red;
            tmp_green = green;
            tmp_blue = blue;
        }
//        Log.w("GUIHandler", "WaterColor"+waterColor);
        ColorFilter filter = new LightingColorFilter(Color.rgb(tmp_red, tmp_green, tmp_blue), 0); //TODO: Fix to add correct color value
        waterPaint.setColorFilter(filter);

    }

    private void calculateProgress(){
        //This line chooses the start of the rect.
//        int progress = Math.min((int) (numberOfBars * water.getTotalWater()), (int) (numberOfBars * maxWaterAmount));
        double timeLeft =  content.getCurrentGameTime();
//        Log.w("GUIHnalder", "TimeLeft: " + timeLeft);
        double maxTime =  content.getGameSetting().getGameTime();
//        Log.w("GUIHnalder","Formel: "+ (barY2 *(1-((maxTime-timeLeft)/maxTime))));
//        plBarY2 = (int)((barY-pBarVillageHeight) * (1-((maxTime-timeLeft)/maxTime))+ (barHeight/2)-pBarVillageHeight);
//        Log.w("GUIhandler","BarHeight:" + barHeight+ "barY: " + barY);
        plBarY2 = (int)((((double)barHeight/2)-(double)pBarVillageHeight) * (1-((maxTime-timeLeft)/maxTime)) + ((double)barY + (double)pBarVillageHeight));
        plBarY = plBarY2-plYRatio;
        rectDst_player = new Rect(plBarX, plBarY,plBarX2, plBarY2);
    }
    public void draw(Canvas canvas){
        //Draws the water bar: BackGround, the actuall water, the bar itself
        canvas.drawBitmap(img_waterBar_back,rectSrc_waterBar,rectDst_waterBar,paint);
        canvas.drawBitmap(img_water, rectSrc_water, rectDst_water, waterPaint);
        canvas.drawBitmap(img_waterBar,rectSrc_waterBar,rectDst_waterBar,paint);

        //Draws the progressbar
        canvas.drawBitmap(img_progressBar,rectSrc_progressBar,rectDst_progressBar,paint);
        canvas.drawBitmap(img_player,rectSrc_player,rectDst_player,paint);
    }


}
