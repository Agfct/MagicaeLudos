package com.magicaeludos.mobile.magicaeludos.implementation.activities;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import com.magicaeludos.mobile.magicaeludos.R;
import com.magicaeludos.mobile.magicaeludos.framework.Content;
import com.magicaeludos.mobile.magicaeludos.framework.GUIhandler;
import com.magicaeludos.mobile.magicaeludos.framework.Grid;
import com.magicaeludos.mobile.magicaeludos.framework.Layout;
import com.magicaeludos.mobile.magicaeludos.framework.MotherActivity;
import com.magicaeludos.mobile.magicaeludos.framework.Probability;

import com.magicaeludos.mobile.magicaeludos.framework.TouchHandler;
import com.magicaeludos.mobile.magicaeludos.framework.TouchHandler.TouchEvent;
import com.magicaeludos.mobile.magicaeludos.framework.Water;
import com.magicaeludos.mobile.magicaeludos.implementation.Background;
import com.magicaeludos.mobile.magicaeludos.implementation.Dummy;
import com.magicaeludos.mobile.magicaeludos.implementation.ObstacleHandler;
import com.magicaeludos.mobile.magicaeludos.implementation.Player;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Anders on 10.02.2016.
 */
public class GameContent implements Content{

    private MotherActivity activity;
    private Layout layout;
    private Probability prop;
    private Grid grid;
    private TouchHandler touchHandler;
    private Background background;
    private ObstacleHandler obstacles;

    //Test
    ArrayList<Dummy> dummies;
    Player player;
    Paint paint = new Paint();
    GUIhandler guIhandler;
    int dy;
    public Water water;

    public GameContent(MotherActivity activity, Layout layout) {
        this.activity = activity;
        this.layout = layout;
        this.grid = new Grid(this);
        this.touchHandler = new TouchHandler(layout, activity.getScreenWidth(), activity.getScreenHeight());
        water = new Water();
        this.guIhandler = new GUIhandler(this,grid);
        this.obstacles = new ObstacleHandler(this);
        dy = activity.getScreenHeight()/500*10;

        player = new Player(this, grid.getPlayerLane(2),grid.getInnerWidth(),grid.getInnerHeight()*2, BitmapFactory.decodeResource(activity.getResources(), R.drawable.animated_avatar));

        dummies = new ArrayList<>();
        dummies.add(new Dummy(this,new Point(grid.getLane(1).x,grid.getLane(1).y+grid.getRowHeight()*5), grid.getColWidth(), grid.getRowHeight(), Color.RED));
//        dummies.add(new Dummy(getActivity(),grid.getLane(2),grid.getColWidth(),grid.getRowHeight()));
        dummies.add(new Dummy(this, new Point(grid.getLane(3).x,grid.getLane(3).y+grid.getRowHeight()*5), grid.getColWidth(), grid.getRowHeight(), Color.RED));

        prop = new Probability();
        background = new Background(this, BitmapFactory.decodeResource(activity.getResources(), R.drawable.bck_africa));
        background.setDy(dy);
    }

    /**
     * Updates the game status. Update is ran every tick of the thread.
     * (How many ticks are based on the AndroidThread class current FPS setting)
     * Update shoud contain everything that needs to be updated in the game (movement, calculations.. everything!)
     */
    @Override
    public void update() {
        double test = prop.probExp(0.5,1.0/30.0);
//        Log.w("GameContent", "Dette er test variabelen2: "+ test);

        List<TouchEvent> touchEvents = touchHandler.getTouchEvents();
        player.update(touchEvents);

        background.update();
        obstacles.update();
        //Check here if player and Object collides: ?

        water.addWaterAmount(1);
        //Updates the GUI:
        guIhandler.update();
    }

    /**
     * The draw method draws all the sprites, items, text, images that should be drawn (shown) on screen.
     * Draw is triggered after update in the AndroidThread, but it is important that there is no "update" here as
     * drawing is suposed to be seperated from updating (to prevent logical errors),
     * but there is also an option to skip frames which causes draw not to be drawn while the game will still update.
     * @param canvas
     */
    @Override
    public void draw(Canvas canvas) {

        //BACK
        //Draws the background
//        canvas.drawBitmap(temporaryBackground,new Rect(0,0,temporaryBackground.getWidth(),temporaryBackground.getWidth()),grid.getScreenBorders(),paint);
        background.draw(canvas);

        //MIDDLE
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        canvas.drawText("Its working! :D", 100, 100, paint);

        //Test
        for (Dummy dummy: dummies
                ) {
            dummy.draw(canvas);
        }
        //Test Ends

        obstacles.draw(canvas);
        player.draw(canvas);
        grid.draw(canvas);

        //FRONT
        //Draws the GUI:
        guIhandler.draw(canvas);

    }

    public MotherActivity getActivity() {
        return activity;
    }

    public Grid getGrid() {
        return grid;
    }

    public TouchHandler getTouchHandler() {
        return touchHandler;
    }

    public int getSpeed(){return dy;}

    public int getBackgroundHeight(){return background.getBackgroundHeight();}

    public Player getPlayer() {
        return player;
    }

}
