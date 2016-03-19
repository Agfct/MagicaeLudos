package com.magicaeludos.mobile.magicaeludos.implementation.activities;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import com.magicaeludos.mobile.magicaeludos.R;
import com.magicaeludos.mobile.magicaeludos.framework.Content;
import com.magicaeludos.mobile.magicaeludos.framework.GUIhandler;
import com.magicaeludos.mobile.magicaeludos.framework.GameSetting;
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
import com.magicaeludos.mobile.magicaeludos.implementation.Village;

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
    private GUIhandler guIhandler;
    private Background background;
    private ObstacleHandler obstacles;
    private GameSetting gameSetting;
    private Paint paint = new Paint();


    //Time
    private double gameTime = 0;
    private double currentGameTime = 0;
    private double startTime = 0;
    private double timeElapsed = 0;

    //Pause
    private boolean running = false;

    //GameObjects
    private Player player;
    private int dy;
    public Water water;


    //Test
    ArrayList<Dummy> dummies;


    public GameContent(MotherActivity activity, Layout layout) {
        this.activity = activity;
        this.layout = layout;
        this.grid = new Grid(this);
        this.touchHandler = new TouchHandler(layout, activity.getScreenWidth(), activity.getScreenHeight());
        this.obstacles = new ObstacleHandler(this);

        initializeGameSettings();

        startGame(); //TODO: 3.. 2.. 1.. Countdown before startingGame ?
    }

    private void initializeGameSettings(){

        //Game Settings. Contains the variables for game difficulty
        gameSetting = new GameSetting(this, activity.getIntent().getIntExtra(activity.getString(R.string.level),0));

        //Player
        player = new Player(this, grid.getPlayerLane(2),grid.getColWidth()/4*3,grid.getInnerHeight()*3, BitmapFactory.decodeResource(activity.getResources(), R.drawable.animate_avatar));

        //Water
        water = gameSetting.getWater();

        //GUI handler
        this.guIhandler = new GUIhandler(this,grid);

        prop = new Probability();

        //Images
        background = new Background(this, BitmapFactory.decodeResource(activity.getResources(), R.drawable.bck_africa));

        //Testing
        dummies = new ArrayList<>();
        dummies.add(new Dummy(this, new Point(grid.getLane(1).x, grid.getLane(1).y + grid.getRowHeight() * 5), grid.getColWidth(), grid.getRowHeight(), Color.RED));
//        dummies.add(new Dummy(getActivity(),grid.getLane(2),grid.getColWidth(),grid.getRowHeight()));
        dummies.add(new Dummy(this, new Point(grid.getLane(3).x, grid.getLane(3).y + grid.getRowHeight() * 5), grid.getColWidth(), grid.getRowHeight(), Color.RED));

    }

    /**
     * Updates the game status. Update is ran every tick of the thread.
     * (How many ticks are based on the AndroidThread class current FPS setting)
     * Update shoud contain everything that needs to be updated in the game (movement, calculations.. everything!)
     */
    @Override
    public void update() {
        if(running) {
            runGameTime();
            double test = prop.probExp(0.5, 1.0 / 30.0);
//        Log.w("GameContent", "Dette er test variabelen2: "+ test);
            List<TouchEvent> touchEvents = touchHandler.getTouchEvents();
            player.update(touchEvents);
            background.update();
            obstacles.update();
            //Check here if player and Object collides: ?
//            water.addWaterAmount(1);
            //Updates the GUI:
            guIhandler.update();
        }
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
        paint.setColor(Color.WHITE);
        canvas.drawText("Game Time: " + currentGameTime, 100, 100, paint);
        canvas.drawText("Time Elapsed: " + timeElapsed, 100, 120, paint);

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

    /**
     * Counts game time in seconds
     */
    private void runGameTime(){
        timeElapsed = System.currentTimeMillis() - startTime;
        int seconds = (int) (timeElapsed / 1000);
        currentGameTime = gameTime - seconds;

        //TODO: Change this if endGame location
        if(currentGameTime <= 0) {
            endGame();
        }

    }

    private void startGame(){
        if(!running){
            startTime = System.currentTimeMillis();
            running = true;
        }
    }

    public void endGame(){
        running = false;
        updateVillage();
        activity.goTo(AfterGameActivity.class);

    }

    //Updates the village after end game
    //TODO: Need to handle dirty water, and other variables.
    private void updateVillage(){
        Village village = activity.getVillage();
        village.addTotalWater(water.getWaterAmount());
        village.saveVillageData();
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

    public void setGameSpeed(int speed){
        dy = activity.getScreenHeight()/500*speed;
    }

    public int getBackgroundHeight(){return background.getBackgroundHeight();}

    public Player getPlayer() {
        return player;
    }

    public void setGameTime(double gameTime) {
        this.gameTime = gameTime;
    }

    public Probability getProp(){return prop;}
}
